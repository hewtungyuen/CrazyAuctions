/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import ejb.session.stateless.AddressEntitySessionBeanRemote;
import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.BidEntitySessionBeanRemote;
import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import entity.AuctionListingEntity;
import entity.CustomerEntity;
import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class CustomerOperationModule {

    private Long customerId;
    private CustomerOperationModuleHelper customerOperationModuleHelper;
    private AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;

    public CustomerOperationModule(Long customerId,
            CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote,
            AddressEntitySessionBeanRemote addressEntitySessionBeanRemote,
            CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote,
            AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote,
            TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote,
            BidEntitySessionBeanRemote bidEntitySessionBeanRemote
    ) {
        this.customerId = customerId;
        this.customerOperationModuleHelper = new CustomerOperationModuleHelper(
                customerId,
                customerEntitySessionBeanRemote,
                addressEntitySessionBeanRemote,
                creditPackageEntitySessionBeanRemote,
                auctionListingEntitySessionBeanRemote,
                transactionEntitySessionBeanRemote,
                bidEntitySessionBeanRemote
        );
        this.auctionListingEntitySessionBeanRemote = auctionListingEntitySessionBeanRemote;
    }

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Auction Client ***\n");
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
                    customerOperationModuleHelper.logout();
                    break;

                } else if (response == 2) {
                    customerOperationModuleHelper.viewCustomerProfile();

                } else if (response == 3) {
                    customerOperationModuleHelper.updateCustomerProfile();

                } else if (response == 4) {
                    customerOperationModuleHelper.createAddress();

                } else if (response == 5) {
                    Long addressId = customerOperationModuleHelper.viewAddressDetails();
                    viewAddressDetailsMenu(addressId);

                } else if (response == 6) {
                    customerOperationModuleHelper.viewAllAddresses();

                } else if (response == 7) {
                    customerOperationModuleHelper.viewCreditBalance();

                } else if (response == 8) {
                    customerOperationModuleHelper.viewCreditTransactionHistory();

                } else if (response == 9) {
                    customerOperationModuleHelper.purchaseCreditPackage();

                } else if (response == 10) {
                    customerOperationModuleHelper.browseAllAuctionListings();

                } else if (response == 11) {
                    String productName = customerOperationModuleHelper.viewAuctionListingDetails();
                    viewAuctionListingDetailsMenu(productName);

                } else if (response == 12) {
                    customerOperationModuleHelper.browseWonAuctionListings();
                    browseWonAuctionListingsMenu();

                } else if (response == 13 || response == 1) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 13 || response == 1) {
                break;
            }
        }
    }

    public void viewAddressDetailsMenu(Long addressId) {

        System.out.println("1: Update Address");
        System.out.println("2: Delete Address");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            customerOperationModuleHelper.updateAddress(addressId);
        } else if (response == 2) {
            customerOperationModuleHelper.deleteAddress(addressId);
        }
    }

    public void viewAuctionListingDetailsMenu(String productName) {

        AuctionListingEntity a = auctionListingEntitySessionBeanRemote.getAuctionListingByProductName(productName);
        System.out.println(a.toString());
        System.out.println("1: Place New Bid");
        System.out.println("2: Refresh Auction Listing Bids");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            customerOperationModuleHelper.placeNewBid(a.getId());
        } else if (response == 2) {
            customerOperationModuleHelper.refreshAuctionListingBids();
            viewAuctionListingDetailsMenu(productName);
        }
    }

    public void browseWonAuctionListingsMenu() {

        System.out.println("1: Select Delivery Address for Won Auction Listing");
        System.out.println("2: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            customerOperationModuleHelper.selectDeliveryAddressForWonAuctionListing();
        }
    }
}
