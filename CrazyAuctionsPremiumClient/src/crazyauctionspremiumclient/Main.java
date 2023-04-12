/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionspremiumclient;

import ws.soap.premiumcustomer.PremiumCustomerWebService;
import ws.soap.premiumcustomer.PremiumCustomerWebService_Service;

/**
 *
 * @author hewtu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PremiumCustomerWebService_Service premiumCustomerWebService_Service = new PremiumCustomerWebService_Service();
        PremiumCustomerWebService premiumCustomerWebServicePort = premiumCustomerWebService_Service.getPremiumCustomerWebServicePort();
        
        MainApp mainApp = new MainApp(premiumCustomerWebServicePort);
        mainApp.runApp();
    }
}
