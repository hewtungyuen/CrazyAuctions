/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.session.singleton.BidIncrementSessionBeanLocal;
import entity.AuctionListingEntity;
import entity.BidEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AuctionListingStateEnum;
import util.exception.InsufficientBalanceException;
import util.exception.NoAuctionListingBidsException;

/**
 *
 * @author hewtu
 */
@Stateless
public class BidEntitySessionBean implements BidEntitySessionBeanRemote, BidEntitySessionBeanLocal {

    @EJB
    private AuctionListingEntitySessionBeanLocal auctionListingEntitySessionBeanLocal;

    @EJB
    private BidIncrementSessionBeanLocal bidIncrementSessionBeanLocal;

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public void markWinningBid(Long auctionListingId) {  // no such auction listing 

        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);
        System.out.println("run mark winning bid");

        try {
            BidEntity b = getHighestBidForAuctionListing(auctionListingId);
            System.out.println("current bid price" + a.getCurrentBidPrice());
            System.out.println("reserve price" + a.getReservePrice());

            if (a.getCurrentBidPrice().compareTo(a.getReservePrice()) >= 0) {
                System.out.println("bid price > reserve price");
                b.setIsWinningBid(Boolean.TRUE);
                a.setWinningBid(b);
                a.setAuctionListingState(AuctionListingStateEnum.CLOSED);
            } else {
                System.out.println("bid price < reserve price");
                a.setAuctionListingState(AuctionListingStateEnum.REQUIRE_INTERVENTION);
            }

        } catch (NoAuctionListingBidsException e) {
            a.setAuctionListingState(AuctionListingStateEnum.REQUIRE_INTERVENTION);
        }
    }

    @Override
    public BidEntity createNewBid(Long customerId, Long auctionListingId, BigDecimal bidPrice) throws InsufficientBalanceException {
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);

        BigDecimal currentBidPrice = a.getCurrentBidPrice();
        BigDecimal newBidPrice = bidIncrementSessionBeanLocal.incrementPrice(currentBidPrice);
        
        if (bidPrice != null) {
            newBidPrice = bidPrice;
        }
        
        if (c.getCreditBalance().compareTo(newBidPrice) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to bid for " + a.getProductName());
        }

        try {
            BidEntity previousBid = getHighestBidForAuctionListing(auctionListingId);
            CustomerEntity previousBidder = previousBid.getCustomer();
            System.out.println("refunding: " + previousBidder.getUsername());
            customerEntitySessionBeanLocal.credit(previousBidder.getId(), currentBidPrice, "Outbidded for " + a.getProductName());
        } catch (NoAuctionListingBidsException ex) {
            System.out.println("No previous highest bidder");
        }

        customerEntitySessionBeanLocal.debit(c.getId(), newBidPrice, "Placed bid for " + a.getProductName());
        a.setCurrentBidPrice(newBidPrice);
        BidEntity normalBid = new BidEntity(c, a, newBidPrice);
        em.persist(normalBid);
        em.flush();

        return normalBid;
    }

    @Override
    public void handleProxyBid(BidEntity proxyBid, BidEntity normalBid) {
        Long auctionListingId = proxyBid.getId();

        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);
        BigDecimal newBidPrice = bidIncrementSessionBeanLocal.incrementPrice(a.getCurrentBidPrice());

        if (proxyBid.getBidPrice().compareTo(newBidPrice) >= 0) {
            CustomerEntity c = normalBid.getCustomer();
            customerEntitySessionBeanLocal.credit(c.getId(), a.getCurrentBidPrice(), "Outbidded for " + a.getProductName());
            a.setCurrentBidPrice(newBidPrice);
            auctionListingEntitySessionBeanLocal.updateAuctionListing(a);
        } else {
            CustomerEntity c = proxyBid.getCustomer();
            customerEntitySessionBeanLocal.credit(c.getId(), proxyBid.getBidPrice(), "Proxy bid expired for " + a.getProductName());
        }

    }

    @Override
    public BidEntity getHighestBidForAuctionListing(Long auctionListingId) throws NoAuctionListingBidsException {

        Query q = em.createQuery("SELECT b FROM BidEntity b WHERE b.auctionListing.id = :auctionListingId "
                + "AND b.bidPrice = (SELECT MAX(b2.bidPrice) FROM BidEntity b2 WHERE b2.auctionListing.id = :auctionListingId)");
        q.setParameter("auctionListingId", auctionListingId);

        try {
            BidEntity highest = (BidEntity) q.getSingleResult();
            return highest;
        } catch (NoResultException ex) {
            throw new NoAuctionListingBidsException("Auction has no bids");
        }
    }

    @Override
    public BidEntity updateBid(BidEntity updatedBid) {
        em.merge(updatedBid);
        return updatedBid;
    }
}
