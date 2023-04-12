/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionspremiumclient;

import ejb.session.stateless.BidEntitySessionBeanRemote;
import entity.AuctionListingEntity;
import entity.BidEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class PremiumCustomerMenu {

    private Long customerId;

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to OAS Auction Client ***\n");
            System.out.println("1: Logout");
            System.out.println("2: View Credit Balance");
            System.out.println("3: View Auction Listing Details");
            System.out.println("4: Browse All Auction Listings");
            System.out.println("5: View Won Auction Listings");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    logout(customerId);
                    break;
                } else if (response == 2) {
                    viewCreditBalance(customerId);
                } else if (response == 3) {
                    viewAuctionListingDetails();
                } else if (response == 4) {
                    browseAllAuctionListings();
                } else if (response == 5) {
                    viewWonAuctionListings();
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 1) {
                break;
            }
        }
    }

    public void logout(Long customerId) {

    }

    public void viewCreditBalance(Long customerId) {

    }

    public void viewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter auction listing product name: ");

        String productName = scanner.nextLine();
        AuctionListingEntity a;
//        System.out.println(a.toString());

        System.out.println("1: Configure Proxy Bid");
        System.out.println("2: Configure Sniping Bid");
        System.out.println("3: Exit\n");

        Integer response = scanner.nextInt();

        if (response == 1) {
            configureProxyBid();
        } else if (response == 2) {
            configureSnipinigBid();
        }
    }

    public void configureProxyBid() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter proxy bid price: ");

    }

    public void configureSnipinigBid() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sniping bid price: ");
        BigDecimal snipingPrice = scanner.nextBigDecimal();
        
        System.out.println("Enter time duration (in minutes) before auction expiry to trigger sniping bid: ");
        Integer minutes = scanner.nextInt();
    }

    public void browseAllAuctionListings() {

    }

    public void viewWonAuctionListings() {

    }

}
