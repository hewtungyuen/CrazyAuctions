/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.BidEntitySessionBeanRemote;
import entity.AuctionListingEntity;
import entity.BidEntity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class SalesOperations {

    private Long employeeId;
    private AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;
    private BidEntitySessionBeanRemote bidEntitySessionBeanRemote;

    public SalesOperations(AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote,
            BidEntitySessionBeanRemote bidEntitySessionBeanRemote
    ) {
        this.auctionListingEntitySessionBeanRemote = auctionListingEntitySessionBeanRemote;
        this.bidEntitySessionBeanRemote = bidEntitySessionBeanRemote;
    }

    // sales staff
    public void createAuctionListing() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.println("Enter reserve price: ");
        BigDecimal reservePrice = scanner.nextBigDecimal();

        System.out.println("Enter starting bid price (must be lower than reserve price): ");
        BigDecimal startingBidPrice = scanner.nextBigDecimal();

        System.out.println("Enter auction start date (yyyy/mm/dd HH:mm)");
        String startDateString = scanner.nextLine();
        Date startDate;
        try {
            startDate = dateFormat.parse(startDateString);
        } catch (Exception e) {
            System.out.println("Invalid date format");
            return;
        }

        System.out.println("Enter auction end date: (yyyy/mm/dd HH:mm)");
        String endDateString = scanner.nextLine();

        Date endDate;
        try {
            endDate = dateFormat.parse(endDateString);
        } catch (Exception e) {
            System.out.println("Invalid date format");
            return;
        }

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

        BidEntity winningBid = bidEntitySessionBeanRemote.getHighestBidForAuctionListing(a.getId());

        System.out.println("Assign this bid (" + winningBid.toString() + ") as winner?: ");
        System.out.println("1: Yes");
        System.out.println("2: No");

        Integer response = scanner.nextInt();

        if (response == 1) {
            a.setWinningBid(winningBid);
            winningBid.setIsWinningBid(Boolean.TRUE);
            auctionListingEntitySessionBeanRemote.updateAuctionListing(a);
            System.out.println("Assigned winning bid for: " + a.toString());
        } else {
            a.setWinningBid(null);  // might have an issue here 
        }

    }
}
