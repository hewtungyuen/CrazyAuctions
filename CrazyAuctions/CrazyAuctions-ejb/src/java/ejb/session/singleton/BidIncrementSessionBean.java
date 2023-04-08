/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import java.math.BigDecimal;
import javax.ejb.Singleton;

/**
 *
 * @author hewtu
 */
@Singleton
public class BidIncrementSessionBean implements BidIncrementSessionBeanRemote, BidIncrementSessionBeanLocal {

    @Override
    public void incrementPrice(BigDecimal currentPrice) {
    }


    
}
