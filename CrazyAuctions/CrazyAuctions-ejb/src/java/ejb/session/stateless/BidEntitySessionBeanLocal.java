/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BidEntity;
import javax.ejb.Local;
import javax.persistence.NoResultException;
import util.exception.InsufficientBalanceException;

/**
 *
 * @author hewtu
 */
@Local
public interface BidEntitySessionBeanLocal {

    void markWinningBid(Long auctionListingId);

    BidEntity createNewBid(Long customerId, Long auctionListingId) throws InsufficientBalanceException;

    BidEntity getHighestBidForAuctionListing(Long auctionListingId) throws NoResultException;

}
