/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import entity.EmployeeEntity;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeTypeEnum;

/**
 *
 * @author hewtu
 */
public class SalesOperations {

    private Long employeeId;
    private AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;

    public SalesOperations(AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote) {
        this.auctionListingEntitySessionBeanRemote = auctionListingEntitySessionBeanRemote;
    }

    // sales staff
    public void createAuctionListing() {
    }

    public void viewAuctionListingDetails() {
    }

    public void viewAllAuctionListings() {
    }

    public void viewAllAuctionListingsWithBidBelowReserve() {
    }

    public void updateAuctionListing() {
    }

    public void deleteAuctionListing() {
    }

    public void assignWinningBid() {
    }
}
