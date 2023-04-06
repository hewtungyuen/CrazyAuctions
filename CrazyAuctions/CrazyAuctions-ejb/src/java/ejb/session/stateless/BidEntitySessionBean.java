/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListingEntity;
import entity.BidEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hewtu
 */
@Stateless
public class BidEntitySessionBean implements BidEntitySessionBeanRemote, BidEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public BidEntity getHighestBidForAuctionListing(Long auctionListingId) {
        Query q = em.createQuery("SELECT b FROM BidEntity b WHERE b.auctionListing.id = :auctionListingId ORDER BY b.bidPrice DESC LIMIT 1");
        q.setParameter("auctionListingId", auctionListingId);
        return (BidEntity) q.getSingleResult();
    }

    @Override
    public void markWinningBid(Long auctionListingId) {  // next time account for proxy bid 
        BidEntity b = getHighestBidForAuctionListing(auctionListingId);
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);

        if (b.getBidPrice().compareTo(a.getReservePrice()) >= 0) {
            b.setIsWinningBid(Boolean.TRUE);
            a.setWinningBid(b);
        }
    }

}
