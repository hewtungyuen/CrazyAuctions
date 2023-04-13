/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BidEntity;
import java.util.Date;
import javax.ejb.Local;
import util.exception.InsufficientBalanceException;
import util.helperclass.SnipingBidDetails;

/**
 *
 * @author hewtu
 */
@Local
public interface SnipingBidTimerSessionBeanLocal {

    void createTimer(SnipingBidDetails snipingBidDetails) throws InsufficientBalanceException;
    
}
