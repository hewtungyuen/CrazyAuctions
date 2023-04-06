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
import javax.ejb.Local;

/**
 *
 * @author hewtu
 */
@Local
public interface AuctionListingEntitySessionBeanLocal {

    AuctionListingEntity createNewAuctionListing(BigDecimal startingBidPrice, BigDecimal reservePrice, String productName, Date startDate, Date endDate);

    AuctionListingEntity getAuctionListingByProductName(String productName);

    List<AuctionListingEntity> viewAllAuctionListings();

    List<AuctionListingEntity> viewAllListingsWithBidsBelowReserve();

    AuctionListingEntity updateAuctionListing(AuctionListingEntity winningBid);

    AuctionListingEntity deleteAuctionListing(Long auctionListingId);

}
