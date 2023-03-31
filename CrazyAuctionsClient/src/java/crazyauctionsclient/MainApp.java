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

    private AdminClient adminClient;
    
    private AuctionClient auctionClient;

    public MainApp() {

    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to CrazyAuctions client ***\n");
            System.out.println("1: OAS Admin Panel");
            System.out.println("2: OAS Auction Client");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    adminClient = new AdminClient();
                    adminClient.login();
                } else if (response == 2) {
                    auctionClient = new AuctionClient();
                    auctionClient.login();
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
}
