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
import javax.persistence.NoResultException;

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
        scanner.nextLine();
        Date startDate;
        System.out.println("Enter auction start date (yyyy/mm/dd HH:mm)");
        String startDateString = scanner.nextLine();

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

        AuctionListingEntity a = auctionListingEntitySessionBeanRemote.createNewAuctionListing(startingBidPrice, reservePrice, productName, startDate, endDate);
        System.out.println("Created: " + a.toString());
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
        System.out.println("Enter new product name or leave empty if unchanged: ");
        String productName = scanner.nextLine().trim();
        if (productName.length() > 0) {
            a.setProductName(productName);
        }

        System.out.println("Enter new reserve price or 0 if unchanged: ");
        BigDecimal reservePrice = scanner.nextBigDecimal();

        if (reservePrice.compareTo(new BigDecimal(0)) > 0) {
            a.setReservePrice(reservePrice);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        scanner.nextLine();
        System.out.println("Enter new auction start date (yyyy/mm/dd HH:mm) or leave empty if unchanged");
        String startDateString = scanner.nextLine().trim();

        if (startDateString.length() > 0) {
            try {
                Date startDate = dateFormat.parse(startDateString);
                a.setStartDate(startDate);
            } catch (Exception e) {
                System.out.println("Invalid date format");
                return;
            }
        }

        System.out.println("Enter new auction end date (yyyy/mm/dd HH:mm) or leave empty if unchanged");
        String endDateString = scanner.nextLine().trim();

        if (endDateString.length() > 0) {
            try {
                Date endDate = dateFormat.parse(endDateString);
                a.setEndDate(endDate);
            } catch (Exception e) {
                System.out.println("Invalid date format");
                return;
            }
        }

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
        BidEntity winningBid;
        try {
            winningBid = bidEntitySessionBeanRemote.getHighestBidForAuctionListing(a.getId());
        } catch (NoResultException ex) {
            System.out.println("No bids for this auction listing");
            return;
        }

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
