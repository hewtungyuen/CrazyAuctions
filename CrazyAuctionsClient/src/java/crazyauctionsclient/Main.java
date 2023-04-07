/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import ejb.session.stateless.AddressEntitySessionBeanRemote;
import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author hewtu
 */
public class Main {

    @EJB
    private static CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote;

    @EJB
    private static AddressEntitySessionBeanRemote addressEntitySessionBeanRemote;

    @EJB
    private static CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;

    @EJB
    private static AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;

    @EJB
    private static TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;
    
    public static void main(String[] args) {
        // TODO code application logic here

        MainApp mainApp = new MainApp(
                customerEntitySessionBeanRemote,
                addressEntitySessionBeanRemote,
                creditPackageEntitySessionBeanRemote,
                auctionListingEntitySessionBeanRemote,
                transactionEntitySessionBeanRemote
        );
        mainApp.runApp(); // inject EJB here
    }

}
