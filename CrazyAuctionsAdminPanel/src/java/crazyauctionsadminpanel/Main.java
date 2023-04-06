/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.BidEntitySessionBeanRemote;
import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author hewtu
 */
public class Main {

    @EJB
    private static BidEntitySessionBeanRemote bidEntitySessionBeanRemote;

    @EJB
    private static AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;

    @EJB
    private static CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote;

    @EJB
    private static EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(employeeEntitySessionBeanRemote,
                creditPackageEntitySessionBeanRemote,
                auctionListingEntitySessionBeanRemote,
                bidEntitySessionBeanRemote
        );
        mainApp.runApp();
    }

}
