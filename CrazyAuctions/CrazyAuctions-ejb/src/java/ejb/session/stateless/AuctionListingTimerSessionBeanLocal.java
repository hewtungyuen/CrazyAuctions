/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Local;

/**
 *
 * @author hewtu
 */
@Local
public interface AuctionListingTimerSessionBeanLocal {

    void createAuctionTimers(Long auctionListingId);

    void cancelTimers(Long auctionListingId);
}
