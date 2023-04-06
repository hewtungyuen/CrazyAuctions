/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

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
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    public SalesOperations(Long employeeId, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote) {
        this.employeeId = employeeId;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
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
