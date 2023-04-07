/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import ejb.session.stateless.AddressEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import entity.AddressEntity;
import entity.CustomerEntity;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class CustomerOperationModuleHelper {

    private Long customerId;
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    private AddressEntitySessionBeanRemote addressEntitySessionBeanRemote;

    public CustomerOperationModuleHelper(Long customerId,
            CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote,
            AddressEntitySessionBeanRemote addressEntitySessionBeanRemote
    ) {
        this.customerId = customerId;
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.addressEntitySessionBeanRemote = addressEntitySessionBeanRemote;
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

        customerEntitySessionBeanRemote.createAddress(customerId, address);
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
        List<AddressEntity> addresses = customerEntitySessionBeanRemote.viewAllAddresses(customerId);
        for (AddressEntity a : addresses) {
            System.out.println(a.toString());
        }
    }

    public void viewCreditBalance() {

    }

    public void viewCreditTransactionHistory() {

    }

    public void purchaseCreditPackage() {

    }

    public void browseAllAuctionListings() {

    }

    public void viewAuctionListingDetails() {

    }

    public void browseWonAuctionListings() {

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

    public void placeNewBid() {

    }

    public void refreshAuctionListingBids() {

    }

    public void selectDeliveryAddressForWonAuctionListing() {

    }
}
