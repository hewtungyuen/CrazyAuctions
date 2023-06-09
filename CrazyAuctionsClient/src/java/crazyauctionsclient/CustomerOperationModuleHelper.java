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
import util.exception.AuthenticationException;
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
        try {
            customerEntitySessionBeanRemote.logout(customerId);
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void viewCustomerProfile() {
        CustomerEntity c = customerEntitySessionBeanRemote.getCustomer(customerId);
        System.out.println("Username: " + c.getUsername());
        System.out.println("Credit balance: " + c.getCreditBalance());
        System.out.println("Customer membership type: " + c.getCustomerType());

    }

    public void updateCustomerProfile(Long customerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: Update username");
        System.out.println("2: Change password");
        System.out.println("3: Exit\n");

        Integer response = scanner.nextInt();
        CustomerEntity c = customerEntitySessionBeanRemote.getCustomer(customerId);

        if (response == 1) {
            changeUsername(c);
        } else if (response == 2) {
            try {
                changePassword(c);
            } catch (AuthenticationException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public CustomerEntity changeUsername(CustomerEntity c) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new username");
        String username = scanner.nextLine();
        c.setUsername(username);
        return customerEntitySessionBeanRemote.updateCustomer(c);
    }

    public CustomerEntity changePassword(CustomerEntity c) throws AuthenticationException {
        CustomerEntity customer = customerEntitySessionBeanRemote.getCustomer(c.getId());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter old password");
        String oldPassword = scanner.nextLine();

        if (!customer.getPassword().equals(oldPassword)) {
            throw new AuthenticationException("Passwords dont match");
        }
        System.out.println("Enter new password");
        String password = scanner.nextLine();

        System.out.println("Confirm new password");
        String confirmPassword = scanner.nextLine();

        if (password.equals(confirmPassword)) {
            customer.setPassword(password);
            return customerEntitySessionBeanRemote.updateCustomer(customer);
        } else {
            throw new AuthenticationException("Passwords dont match");
        }
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

        if (addresses.isEmpty()) {
            System.out.println("No addresses");
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

        if (transactions.isEmpty()) {
            System.out.println("No transactions");
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

        if (quantity == 0) {
            System.out.println("Quantity cannot be zero");
            return;
        }

        CreditPackageEntity c = customerEntitySessionBeanRemote.purchaseCreditPackage(customerId, creditPackageId, quantity);
        System.out.println("Purchased credit package: " + c.toString());
    }

    public void browseAllAuctionListings() {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanRemote.viewAllOpenAuctionListings();
        for (AuctionListingEntity a : listings) {
            System.out.println(a.toString());
        }

        if (listings.isEmpty()) {
            System.out.println("No active auction listings");
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

        if (won.isEmpty()) {
            System.out.println("No won auction listings");
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
            BidEntity b = bidEntitySessionBeanRemote.createNewBid(customerId, auctionListingId, null);
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

        System.out.println("Number of addresses: " + addresses.size());
        for (AddressEntity a : addresses) {
            System.out.println(a.toString());
        }

        System.out.println("Select delivery address: (input address id)");
        Long addressId = scanner.nextLong();

        AddressEntity address = addressEntitySessionBeanRemote.getAddress(addressId);

        auctionListing.setWinnerDeliveryAddress(address);
        AuctionListingEntity updated = auctionListingEntitySessionBeanRemote.updateAuctionListing(auctionListing);
        System.out.println("Selected delivery address: " + address.getAddress() + "for: " + auctionListing.getProductName());
    }
}
