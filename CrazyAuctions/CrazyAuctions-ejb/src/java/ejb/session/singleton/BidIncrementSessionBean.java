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
        if (currentPrice >= 0.01 && currentPrice <= 0.99) {
            return 0.05;
        } elif (currentPrice >= 1.00 && currentPrice <= 4.99) {
            return 0.25;
        } elif (currentPrice >= 5.00 && currentPrice <= 24.99) {
            return 0.50;
        } elif (currentPrice >= 25.00 && currentPrice <= 99.99) {
            return 1.00;
        } elif (currentPrice >= 100.00 && currentPrice <= 249.99) {
            return 2.50;
        } elif (currentPrice >= 250.00 && currentPrice <= 499.99) {
            return 5.00;
        } elif (currentPrice >= 500.00 && currentPrice <= 999.99) {
            return 10.00;
        } elif (currentPrice >= 1000 && currentPrice <= 2499.99) {
            return 25.00;
        } elif (currentPrice >= 2500 && currentPrice <= 4999.99) {
            return 50.00;
        } else {
            return 100.00;
        }
    }


    
}
