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
public class AuctionOperationModule {

    private AuctionOperationModuleHelper auctionOperationModuleHelper;

    public AuctionOperationModule() {
        this.auctionOperationModuleHelper = new AuctionOperationModuleHelper();
    }

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Admin Panel: Employee ***\n");
            System.out.println("1: Logout");
            System.out.println("2: View Customer Profile");
            System.out.println("3: Update Customer Profile");
            System.out.println("4: Create Address");
            System.out.println("5: View Address Details"); // includes update and delete 
            System.out.println("6: View All Addresses");
            System.out.println("7: View Credit Balance");
            System.out.println("8: View Credit Transaction History");
            System.out.println("9: Purchase Credit Package");
            System.out.println("10: Browse All Auction Listings");
            System.out.println("11: View Auction Listing Details");  // includes place new bids and refresh
            System.out.println("12: Browse Won Auction Listings");  // includes select delivery address 
            System.out.println("13: Exit\n");
            response = 0;

            while (response < 1 || response > 13) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    auctionOperationModuleHelper.logout();

                } else if (response == 2) {
                    auctionOperationModuleHelper.viewCustomerProfile();

                } else if (response == 3) {
                    auctionOperationModuleHelper.updateCustomerProfile();

                } else if (response == 4) {
                    auctionOperationModuleHelper.createAddress();

                } else if (response == 5) {
                    auctionOperationModuleHelper.viewAddressDetails();
                    viewAddressDetailsMenu();

                } else if (response == 6) {
                    auctionOperationModuleHelper.viewAllAddresses();

                } else if (response == 7) {
                    auctionOperationModuleHelper.viewCreditBalance();

                } else if (response == 8) {
                    auctionOperationModuleHelper.viewCreditTransactionHistory();

                } else if (response == 9) {
                    auctionOperationModuleHelper.purchaseCreditPackage();

                } else if (response == 10) {
                    auctionOperationModuleHelper.browseAllAuctionListings();

                } else if (response == 11) {
                    auctionOperationModuleHelper.viewAuctionListingDetails();
                    viewAuctionListingDetailsMenu();

                } else if (response == 12) {
                    auctionOperationModuleHelper.browseWonAuctionListings();
                    browseWonAuctionListingsMenu();

                } else if (response == 13) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 13) {
                break;
            }
        }
    }

    public void viewAddressDetailsMenu() {

        System.out.println("1: Update Address");
        System.out.println("2: Delete Address");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            auctionOperationModuleHelper.updateAddress();
        } else if (response == 2) {
            auctionOperationModuleHelper.deleteAddress();
        }
    }

    public void viewAuctionListingDetailsMenu() {

        System.out.println("1: Place New Bid");
        System.out.println("2: Refresh Auction Listing Bids");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            auctionOperationModuleHelper.placeNewBid();
        } else if (response == 2) {
            auctionOperationModuleHelper.refreshAuctionListingBids();
        }
    }

    public void browseWonAuctionListingsMenu() {

        System.out.println("1: Select Delivery Address for Won Auction Listing");
        System.out.println("2: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            auctionOperationModuleHelper.selectDeliveryAddressForWonAuctionListing();
        }
    }
}
