/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class MainApp {

    private AuctionOperationModule auctionOperationModule;

    // 
    public MainApp() {

    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to OAS Auction Client ***\n");
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
        // check if credentials are correct
        // check customer type and render correct menu accordingly 
        System.out.println("Enter username:");
        System.out.println("Enter password:");

        auctionOperationModule = new AuctionOperationModule();
        auctionOperationModule.menu();
    }

    public void doRegister() {
        System.out.println("Enter username:");
        System.out.println("Enter password:");
        System.out.println("Successfully registered.");

    }
}
