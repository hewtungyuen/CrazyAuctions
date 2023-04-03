/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class AdminOperationModuleHelper {

    private Long employeeId;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    public AdminOperationModuleHelper(Long employeeId, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote) {
        this.employeeId = employeeId;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
    }

    // all employees 
    public void logout() {
        employeeEntitySessionBeanRemote.logout(employeeId);
    }

    public void changePassword() {
        // prompt current password, check if it is correct
        // enter new password twice, both times it should match
        // change password attribute 

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter current password:");
        String currentPassword = scanner.nextLine();

        Boolean correct = employeeEntitySessionBeanRemote.checkCorrectPassword(employeeId, currentPassword);

        if (!correct) {
            System.out.println("Incorrect password");
            return;
        }

        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        System.out.println("Enter new password again:");
        String newPasswordConfirmation = scanner.nextLine();

        if (newPassword.equals(newPasswordConfirmation)) {
            employeeEntitySessionBeanRemote.changePassword(employeeId, newPassword);
            System.out.println("Changed password successfully");

            return;
        }
        System.out.println("Passwords dont match");
    }

    // system admin
    public void createNewEmployee() {
        // enter employee details
    }

    public void viewEmployeeDetails() {
        // call toString method of employee 
    }

    public void viewAllEmployees() {
        // view all employee IDs
    }

    public void updateEmployee() {
        // 
    }

    public void deleteEmployee() {
    }

    // finance staff
    public void createCreditPackage() {
    }

    public void viewCreditPackageDetails() {
    }

    public void viewAllCreditPackages() {
    }

    public void updateCreditPackage() {

    }

    public void deleteCreditPackage() {

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
