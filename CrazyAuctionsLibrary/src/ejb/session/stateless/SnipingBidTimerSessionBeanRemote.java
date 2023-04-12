/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BidEntity;
import java.util.Date;
import javax.ejb.Remote;
import util.helperclass.SnipingBidDetails;

/**
 *
 * @author hewtu
 */
@Remote
public interface SnipingBidTimerSessionBeanRemote {

    void createTimer(SnipingBidDetails snipingBidDetails);
    
}
