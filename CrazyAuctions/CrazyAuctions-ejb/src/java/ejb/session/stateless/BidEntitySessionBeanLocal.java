/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BidEntity;
import javax.ejb.Local;
import util.exception.InsufficientBalanceException;
import util.exception.NoAuctionListingBidsException;

/**
 *
 * @author hewtu
 */
@Local
public interface BidEntitySessionBeanLocal {

    void markWinningBid(Long auctionListingId);

    BidEntity createNewBid(Long customerId, Long auctionListingId) throws InsufficientBalanceException;

    BidEntity getHighestBidForAuctionListing(Long auctionListingId) throws NoAuctionListingBidsException;

    BidEntity updateBid(BidEntity updatedBid);

}
