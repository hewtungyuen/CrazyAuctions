/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import ejb.session.stateless.AddressEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author hewtu
 */
public class Main {

    @EJB
    private static AddressEntitySessionBeanRemote addressEntitySessionBeanRemote;

    @EJB
    private static CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        MainApp mainApp = new MainApp(customerEntitySessionBeanRemote, addressEntitySessionBeanRemote);
        mainApp.runApp(); // inject EJB here
    }
    
}
