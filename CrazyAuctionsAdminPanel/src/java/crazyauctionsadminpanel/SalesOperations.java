/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import entity.AuctionListingEntity;
import entity.EmployeeEntity;
import java.math.BigDecimal;
import java.util.Date;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.println("Enter reserve price: ");
        BigDecimal reservePrice = scanner.nextBigDecimal();

        System.out.println("Enter starting bid price (must be lower than reserve price): ");
        BigDecimal startingBidPrice = scanner.nextBigDecimal();

        System.out.println("Enter auction start date: ");
        String startDateString = scanner.nextLine();
        Date startDate = new Date(startDateString);

        System.out.println("Enter auction end date: ");
        String endDateString = scanner.nextLine();
        Date endDate = new Date(endDateString);

        AuctionListingEntity a = new AuctionListingEntity(startingBidPrice, reservePrice, productName, startDate, endDate);

    }

    public AuctionListingEntity viewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();

        AuctionListingEntity a = auctionListingEntitySessionBeanRemote.getAuctionListingByProductName(productName);
        System.out.println(a.toString());
        return a;
    }

    public void viewAllAuctionListings() {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanRemote.viewAllAuctionListings();
        for (AuctionListingEntity a : listings) {
            System.out.println(a.toString());
        }
    }

    public void viewAllAuctionListingsWithBidBelowReserve() {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanRemote.viewAllListingsWithBidsBelowReserve();
        for (AuctionListingEntity a : listings) {
            System.out.println(a.toString());
        }
    }

    public void updateAuctionListing(AuctionListingEntity a) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new product name: ");
        String productName = scanner.nextLine();
        a.setProductName(productName);

        System.out.println("Enter new reserve price: ");
        BigDecimal reservePrice = scanner.nextBigDecimal();
        a.setReservePrice(reservePrice);

        System.out.println("Enter new auction start date: ");
        String startDateString = scanner.nextLine();
        Date startDate = new Date(startDateString);
        a.setStartDate(startDate);

        System.out.println("Enter new auction end date: ");
        String endDateString = scanner.nextLine();
        Date endDate = new Date(endDateString);
        a.setEndDate(endDate);

        AuctionListingEntity updated = auctionListingEntitySessionBeanRemote.updateAuctionListing(a);
        System.out.println("Updated: " + updated.toString());
    }

    public void deleteAuctionListing(AuctionListingEntity a) {
        AuctionListingEntity deleted = auctionListingEntitySessionBeanRemote.deleteAuctionListing(a.getId());
        System.out.println("Deleted :" + a.toString());
    }

    public void assignWinningBid() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();
        AuctionListingEntity a = auctionListingEntitySessionBeanRemote.getAuctionListingByProductName(productName);

        // display all winning bids for that auction listing 
        System.out.println("Enter winning bid id (0 if no winning bid): ");
        Long winningBidId = scanner.nextLong();
        // get the winning bid 

        if (winningBidId != new Long(0)) {
            auctionListingEntitySessionBeanRemote.updateAuctionListing(a);
            System.out.println("Assigned winning bid for: " + a.toString());
        } else {
            a.setWinningBid(null);  // might have an issue here 
        }

    }
}
