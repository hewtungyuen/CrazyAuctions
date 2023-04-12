/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionspremiumclient;

import ws.soap.premiumcustomer.AuctionListingEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import ws.soap.premiumcustomer.AuthenticationException_Exception;
import ws.soap.premiumcustomer.BidEntity;
import ws.soap.premiumcustomer.InsufficientBalanceException_Exception;
import ws.soap.premiumcustomer.InvalidDateInputException_Exception;
import ws.soap.premiumcustomer.PremiumCustomerWebService;

/**
 *
 * @author hewtu
 */
public class PremiumCustomerMenu {

    private Long customerId;
    private PremiumCustomerWebService port;

    public PremiumCustomerMenu(Long customerId, PremiumCustomerWebService port) {
        this.customerId = customerId;
        this.port = port;
    }

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
                    try {
                        logout(customerId);
                    } catch (AuthenticationException_Exception ex) {
                        System.out.println("Already logged out");
                    }
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

    public void logout(Long customerId) throws AuthenticationException_Exception {
        port.remoteLogout(customerId);
    }

    public void viewCreditBalance(Long customerId) {
        BigDecimal balance = port.remoteViewCreditBalance(customerId);
        System.out.println("Credit balance: " + balance);
    }

    public void viewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter auction listing product name: ");

        String productName = scanner.nextLine();

        try {
            AuctionListingEntity a = port.remoteViewAuctionListingDetails(productName);
            System.out.println(a.toString());

            System.out.println("1: Configure Proxy Bid");
            System.out.println("2: Configure Sniping Bid");
            System.out.println("3: Exit\n");

            Integer response = scanner.nextInt();

            if (response == 1) {
                configureProxyBid(a.getId(), customerId);
            } else if (response == 2) {
                configureSnipinigBid(a.getId(), customerId);
            }
        } catch (Exception ex) {
            System.out.println("No such auction listing");
        }

    }

    public void configureProxyBid(Long auctionListingId, Long customerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter proxy bid price: ");

    }

    public void configureSnipinigBid(Long auctionListingId, Long customerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sniping bid price: ");
        BigDecimal snipingPrice = scanner.nextBigDecimal();

        System.out.println("Enter time duration (in minutes) before auction expiry to trigger sniping bid: ");
        Long minutes = scanner.nextLong();

        try {
            port.configureSnipingBid(customerId, auctionListingId, snipingPrice, minutes);
            System.out.println("Successfully configured sniping bid");
        } catch (InsufficientBalanceException_Exception | InvalidDateInputException_Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void browseAllAuctionListings() {
        List<AuctionListingEntity> listings = port.remoteBrowseAllAuctionListings();
        for (AuctionListingEntity a : listings) {
            System.out.println(a.getProductName());
        }

        if (listings.isEmpty()) {
            System.out.println("No open auction listings. ");
        }
    }

    public void viewWonAuctionListings() {
        List<AuctionListingEntity> listings = port.remoteViewWonAuctionListings(customerId);
        for (AuctionListingEntity a : listings) {
            System.out.println(a.getProductName());
        }
        if (listings.isEmpty()) {
            System.out.println("No won auction listings. ");
        }
    }

}
