/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import entity.CustomerEntity;

/**
 *
 * @author hewtu
 */
public class CustomerOperationModuleHelper {

    private CustomerEntity customer;
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;

    public CustomerOperationModuleHelper(CustomerEntity customer, CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote) {
        this.customer = customer;
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
    }

    public void logout() {
        customerEntitySessionBeanRemote.logout(customer);
    }

    public void viewCustomerProfile() {

    }

    public void updateCustomerProfile() {

    }

    public void createAddress() {

    }

    public void viewAddressDetails() {

    }

    public void viewAllAddresses() {

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

    public void updateAddress() {

    }

    public void deleteAddress() {

    }

    public void placeNewBid() {

    }

    public void refreshAuctionListingBids() {

    }

    public void selectDeliveryAddressForWonAuctionListing() {

    }
}
