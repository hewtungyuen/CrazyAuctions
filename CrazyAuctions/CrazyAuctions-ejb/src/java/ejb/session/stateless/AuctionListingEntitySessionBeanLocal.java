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
import util.exception.DuplicateProductNameException;

/**
 *
 * @author hewtu
 */
@Local
public interface AuctionListingEntitySessionBeanLocal {

    AuctionListingEntity createNewAuctionListing(BigDecimal startingBidPrice, BigDecimal reservePrice, String productName, Date startDate, Date endDate) throws DuplicateProductNameException;

    AuctionListingEntity getAuctionListingByProductName(String productName);

    List<AuctionListingEntity> viewAllAuctionListings();

    List<AuctionListingEntity> viewAllListingsWithBidsBelowReserve();

    AuctionListingEntity updateAuctionListing(AuctionListingEntity winningBid);

    AuctionListingEntity deleteAuctionListing(Long auctionListingId);

    List<AuctionListingEntity> viewAllOpenAuctionListings();

    List<AuctionListingEntity> browseWonAuctionListings(Long customerId);

}
