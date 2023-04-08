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
import entity.AddressEntity;
import entity.AuctionListingEntity;
import entity.BidEntity;
import entity.CreditPackageEntity;
import entity.CustomerEntity;
import entity.TransactionEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.InsufficientBalanceException;

/**
 *
 * @author hewtu
 */
public class CustomerOperationModuleHelper {

    private Long customerId;
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    private AddressEntitySessionBeanRemote addressEntitySessionBeanRemote;
    private CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote;
    private AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;
    private TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;
    private BidEntitySessionBeanRemote bidEntitySessionBeanRemote;

    public CustomerOperationModuleHelper(Long customerId,
            CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote,
            AddressEntitySessionBeanRemote addressEntitySessionBeanRemote,
            CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote,
            AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote,
            TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote,
            BidEntitySessionBeanRemote bidEntitySessionBeanRemote
    ) {
        this.customerId = customerId;
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.addressEntitySessionBeanRemote = addressEntitySessionBeanRemote;
        this.creditPackageEntitySessionBeanRemote = creditPackageEntitySessionBeanRemote;
        this.auctionListingEntitySessionBeanRemote = auctionListingEntitySessionBeanRemote;
        this.transactionEntitySessionBeanRemote = transactionEntitySessionBeanRemote;
        this.bidEntitySessionBeanRemote = bidEntitySessionBeanRemote;
    }

    public void logout() {
        customerEntitySessionBeanRemote.logout(customerId);
    }

    public void viewCustomerProfile() {
        CustomerEntity c = customerEntitySessionBeanRemote.getCustomer(customerId);
        System.out.println("Username: " + c.getUsername());
        System.out.println("Credit balance: " + c.getCreditBalance());
        System.out.println("Customer membership type: " + c.getCustomerType());

    }

    public void updateCustomerProfile() {

    }

    public void createAddress() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter address:");
        String address = scanner.nextLine();

        addressEntitySessionBeanRemote.createAddress(address, customerId);
    }

    public Long viewAddressDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter address id:");
        Long addressId = scanner.nextLong();
        AddressEntity a = addressEntitySessionBeanRemote.getAddress(addressId);
        System.out.println(a.toString());
        return addressId;
    }

    public void viewAllAddresses() {
        List<AddressEntity> addresses = addressEntitySessionBeanRemote.viewAllAddresses(customerId);
        for (AddressEntity a : addresses) {
            System.out.println(a.toString());
        }
    }

    public void viewCreditBalance() {
        CustomerEntity c = customerEntitySessionBeanRemote.getCustomer(customerId);
        System.out.println("Credit balance: " + c.getCreditBalance());
    }

    public void viewCreditTransactionHistory() {
        List<TransactionEntity> transactions = transactionEntitySessionBeanRemote.viewCustomerTransactions(customerId);
        for (TransactionEntity t : transactions) {
            System.out.println(t.toString());
        }
    }

    public void purchaseCreditPackage() {
        List<CreditPackageEntity> creditPackages = creditPackageEntitySessionBeanRemote.viewAllOpenCreditPackages();
        for (CreditPackageEntity c : creditPackages) {
            System.out.println(c.toString());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter credit package id to purchase:");
        Long creditPackageId = scanner.nextLong();

        System.out.println("Enter quantity:");
        Integer quantity = scanner.nextInt();

        CreditPackageEntity c = customerEntitySessionBeanRemote.purchaseCreditPackage(customerId, creditPackageId, quantity);
        System.out.println("Purchased credit package: " + c.toString());
    }

    public void browseAllAuctionListings() {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanRemote.viewAllOpenAuctionListings();
        for (AuctionListingEntity a : listings) {
            System.out.println(a.toString());
        }
    }

    public String viewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product name to view:");
        String auctionListingName = scanner.nextLine();
        AuctionListingEntity a = auctionListingEntitySessionBeanRemote.getAuctionListingByProductName(auctionListingName);
        return a.getProductName();
    }

    public void browseWonAuctionListings() {
        List<AuctionListingEntity> won = auctionListingEntitySessionBeanRemote.browseWonAuctionListings(customerId);
        for (AuctionListingEntity a : won) {
            System.out.println(a.toString());
        }
    }

    public void updateAddress(Long addressId) {
        Scanner scanner = new Scanner(System.in);
        AddressEntity a = addressEntitySessionBeanRemote.getAddress(addressId);

        System.out.println("Enter new address (or blank if unchanged):");
        String newAddressLine = scanner.nextLine().trim();

        if (newAddressLine.length() > 0) {
            a.setAddress(newAddressLine);
            AddressEntity newAddress = addressEntitySessionBeanRemote.updateAddress(a);
            System.out.println("Successfully updated");
        }

        System.out.println("No change");
    }

    public void deleteAddress(Long addressId) {
        AddressEntity a = addressEntitySessionBeanRemote.deleteAddress(addressId);
        System.out.println("Deleted address: " + a.getAddress());
    }

    public void placeNewBid(Long auctionListingId) {
        try {
            BidEntity b = bidEntitySessionBeanRemote.createNewBid(customerId, auctionListingId);
            System.out.println("Placed new bid at " + b.getBidPrice());
        } catch (InsufficientBalanceException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void selectDeliveryAddressForWonAuctionListing() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select delivery address for which auction listing? (input product name)");
        String productName = scanner.nextLine().trim();
        AuctionListingEntity auctionListing = auctionListingEntitySessionBeanRemote.getAuctionListingByProductName(productName);

        if (auctionListing.getWinnerDeliveryAddress() != null) {
            System.out.println("Already selected delivery address for this auction listing");
            return;
        }
        
        List<AddressEntity> addresses = addressEntitySessionBeanRemote.viewAllAvailableAddressesForCustomer(customerId);
        
        for (AddressEntity a : addresses) {
            a.toString();
        }
        
        System.out.println("Select delivery address: (input address id)");
        Long addressId = scanner.nextLong();

        AddressEntity address = addressEntitySessionBeanRemote.getAddress(addressId);

        auctionListing.setWinnerDeliveryAddress(address);
        AuctionListingEntity updated = auctionListingEntitySessionBeanRemote.updateAuctionListing(auctionListing);
        System.out.println("Selected delivery address: " + address.getAddress() + "for: " + auctionListing.getProductName());
    }
}
