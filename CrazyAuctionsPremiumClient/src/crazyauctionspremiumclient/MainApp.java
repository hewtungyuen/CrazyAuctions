/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionspremiumclient;

import java.util.Scanner;
import ws.soap.premiumcustomer.AuthenticationException_Exception;
import ws.soap.premiumcustomer.CustomerEntity;
import ws.soap.premiumcustomer.PremiumCustomerWebService;

/**
 *
 * @author hewtu
 */
public class MainApp {

    PremiumCustomerWebService port;

    public MainApp(PremiumCustomerWebService premiumCustomerWebServicePort) {
        this.port = premiumCustomerWebServicePort;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** Welcome to OAS Auction Remote Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doLogin();
                } else if (response == 2) {
                    doRegister();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    public void doLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            CustomerEntity c = port.remoteLogin(username, password);
            PremiumCustomerMenu premiumCustomerMenu = new PremiumCustomerMenu(c.getId(), port);
            premiumCustomerMenu.menu();
        } catch (AuthenticationException_Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void doRegister() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            CustomerEntity c = port.premiumRegistration(username, password);
            System.out.println("Successfully registered: " + c.toString());
        } catch (AuthenticationException_Exception ex) {
            System.out.println("Your CrazyBids.com credentials are incorrect: " + ex.getMessage());
            System.out.println("Please ensure that you have regisered for a CrazyBids account first.");
        }
    }
}
