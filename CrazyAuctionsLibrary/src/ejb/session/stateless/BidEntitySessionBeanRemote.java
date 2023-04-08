/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BidEntity;
import javax.ejb.Remote;
import javax.persistence.NoResultException;
import util.exception.InsufficientBalanceException;

/**
 *
 * @author hewtu
 */
@Remote
public interface BidEntitySessionBeanRemote {

    BidEntity getHighestBidForAuctionListing(Long auctionListingId) throws NoResultException;

    void markWinningBid(Long auctionListingId);

    BidEntity createNewBid(Long customerId, Long auctionListingId) throws InsufficientBalanceException;

}
