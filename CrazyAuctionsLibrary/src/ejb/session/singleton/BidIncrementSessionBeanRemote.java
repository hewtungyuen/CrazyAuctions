/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author hewtu
 */
@Remote
public interface BidIncrementSessionBeanRemote {

    BigDecimal incrementPrice(BigDecimal currentPrice);
    
}
