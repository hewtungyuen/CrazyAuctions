/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BidEntity;
import java.math.BigDecimal;
import javax.ejb.Remote;
import util.exception.InsufficientBalanceException;
import util.exception.NoAuctionListingBidsException;

/**
 *
 * @author hewtu
 */
@Remote
public interface BidEntitySessionBeanRemote {

    void markWinningBid(Long auctionListingId);

    BidEntity createNewBid(Long customerId, Long auctionListingId, BigDecimal bidPrice) throws InsufficientBalanceException;

    BidEntity getHighestBidForAuctionListing(Long auctionListingId) throws NoAuctionListingBidsException;

    BidEntity updateBid(BidEntity updatedBid);

    void handleProxyBid(BidEntity proxyBid, BidEntity normalBid);

}
