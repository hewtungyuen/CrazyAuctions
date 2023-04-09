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
import util.exception.InsufficientBalanceException;

/**
 *
 * @author hewtu
 */
@Stateless
public class BidEntitySessionBean implements BidEntitySessionBeanRemote, BidEntitySessionBeanLocal {

    @EJB
    private BidIncrementSessionBeanLocal bidIncrementSessionBeanLocal;

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public void markWinningBid(Long auctionListingId) {  // no such auction listing 

        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);

        try {
            BidEntity b = getHighestBidForAuctionListing(auctionListingId);
            if (b.getBidPrice().compareTo(a.getReservePrice()) >= 0) {
                b.setIsWinningBid(Boolean.TRUE);
                a.setWinningBid(b);
            }
        } catch (NoResultException e) {
            return;
        }
    }

    @Override
    public BidEntity createNewBid(Long customerId, Long auctionListingId) throws InsufficientBalanceException {
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);
        BigDecimal currentBidPrice = a.getCurrentBidPrice();
        BigDecimal newBidPrice = bidIncrementSessionBeanLocal.incrementPrice(currentBidPrice);

        if (c.getCreditBalance().compareTo(newBidPrice) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to bid for " + a.getProductName());
        }

        try {
            BidEntity previousBid = getHighestBidForAuctionListing(auctionListingId);
            CustomerEntity previousBidder = previousBid.getCustomer();
            customerEntitySessionBeanLocal.credit(previousBidder.getId(), currentBidPrice, "Outbidded for " + a.getProductName());
        } catch (NoResultException ex) {
            
        }

        customerEntitySessionBeanLocal.debit(customerId, newBidPrice, "Placed bid for " + a.getProductName());

        BidEntity b = new BidEntity(c, a, newBidPrice);
        em.persist(b);
        em.flush();
        return b;
    }

    @Override
    public BidEntity getHighestBidForAuctionListing(Long auctionListingId) throws NoResultException {

        Query q = em.createQuery("SELECT b FROM BidEntity b WHERE b.auctionListing.id = :auctionListingId ORDER BY b.bidPrice DESC");
        q.setParameter("auctionListingId", auctionListingId);
        q.setMaxResults(1);
        try {
            BidEntity highest = (BidEntity) q.getSingleResult();
            return highest;
        } catch (NoResultException ex) {
            throw new NoResultException("asdfasdf");
        }
    }

}
