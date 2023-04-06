/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListingEntity;
import entity.BidEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AuctionListingStateEnum;

/**
 *
 * @author hewtu
 */
@Stateless
public class AuctionListingEntitySessionBean implements AuctionListingEntitySessionBeanRemote, AuctionListingEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public AuctionListingEntity createNewAuctionListing(BigDecimal startingBidPrice, BigDecimal reservePrice, String productName, Date startDate, Date endDate) {
        AuctionListingEntity a = new AuctionListingEntity(startingBidPrice, reservePrice, productName, startDate, endDate);
        em.persist(a);
        em.flush();
        return a;
    }

    @Override
    public AuctionListingEntity getAuctionListingByProductName(String productName) {
        Query q = em.createQuery("SELECT a FROM AuctionListingEntity a WHERE a.productName = :productName");
        q.setParameter("productName", productName);
        return (AuctionListingEntity) q.getSingleResult();
    }

    @Override
    public List<AuctionListingEntity> viewAllAuctionListings() {
        Query q = em.createQuery("SELECT a FROM AuctionListingEntity a");
        return q.getResultList();
    }

    @Override
    public List<AuctionListingEntity> viewAllListingsWithBidsBelowReserve() {
        Query q = em.createQuery("SELECT a FROM AuctionListingEntity a WHERE a.currentBidPrice < a.reservePrice AND a.auctionListingState != :openState");
        q.setParameter("openState", AuctionListingStateEnum.OPEN);
        return q.getResultList();
    }

    @Override
    public AuctionListingEntity updateAuctionListing(AuctionListingEntity updatedAuctionListing) {
        em.merge(updatedAuctionListing);
        return updatedAuctionListing;
    }

    @Override
    public AuctionListingEntity deleteAuctionListing(Long auctionListingId) {
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);
        em.remove(a);
        return a;
    }
    
    
    
    

}