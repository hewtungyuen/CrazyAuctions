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
    public BigDecimal incrementPrice(BigDecimal currentPrice) {
        if (currentPrice.compareTo(new BigDecimal("0.01")) >= 0 && currentPrice.compareTo(new BigDecimal("0.99")) <= 0) {
            return new BigDecimal("0.05").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("1.00")) >= 0 && currentPrice.compareTo(new BigDecimal("4.99")) <= 0) {
            return new BigDecimal("0.25").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("5.00")) >= 0 && currentPrice.compareTo(new BigDecimal("24.99")) <= 0) {
            return new BigDecimal("0.50").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("25.00")) >= 0 && currentPrice.compareTo(new BigDecimal("99.99")) <= 0) {
            return new BigDecimal("1.00").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("100.00")) >= 0 && currentPrice.compareTo(new BigDecimal("249.99")) <= 0) {
            return new BigDecimal("2.50").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("250.00")) >= 0 && currentPrice.compareTo(new BigDecimal("499.99")) <= 0) {
            return new BigDecimal("5.00").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("500.00")) >= 0 && currentPrice.compareTo(new BigDecimal("999.99")) <= 0) {
            return new BigDecimal("10.00").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("1000.00")) >= 0 && currentPrice.compareTo(new BigDecimal("2499.99")) <= 0) {
            return new BigDecimal("25.00").add(currentPrice);
        } else if (currentPrice.compareTo(new BigDecimal("2500.00")) >= 0 && currentPrice.compareTo(new BigDecimal("4999.99")) <= 0) {
            return new BigDecimal("50.00").add(currentPrice);
        } else {
            return new BigDecimal("100.00").add(currentPrice);
        }
    }

}
