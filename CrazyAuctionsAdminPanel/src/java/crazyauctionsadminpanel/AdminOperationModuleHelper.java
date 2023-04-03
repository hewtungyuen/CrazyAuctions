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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee username");
        String username = scanner.nextLine();

        System.out.println("Enter employee password");
        String password = scanner.nextLine();

        System.out.println("Enter employee type:");
        System.out.println("1: Employee");
        System.out.println("2: Finance");
        System.out.println("3: Sales");
        System.out.println("4: Admin");
        EmployeeTypeEnum employeeType;

        Integer response = scanner.nextInt();

        if (response == 1) {
            employeeType = EmployeeTypeEnum.EMPLOYEE;
        } else if (response == 2) {
            employeeType = EmployeeTypeEnum.FINANCE;
        } else if (response == 3) {
            employeeType = EmployeeTypeEnum.SALES;
        } else if (response == 4) {
            employeeType = EmployeeTypeEnum.ADMIN;
        } else {
            System.out.println("Invalid input, try again");
            return;
        }

        Long employeeId = employeeEntitySessionBeanRemote.createNewEmployee(username, password, employeeType);

        System.out.println("Created employee: " + employeeId);

    }

    public Long viewEmployeeDetails() {
        // call toString method of employee 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee username");
        String username = scanner.nextLine();

        EmployeeEntity e = employeeEntitySessionBeanRemote.getEmployee(username);
        System.out.println(e.toString());
        return e.getId();
    }

    public void viewAllEmployees() {
        List<EmployeeEntity> employees = employeeEntitySessionBeanRemote.viewAllEmployees();
        for (EmployeeEntity e : employees) {
            System.out.println(e.toString());
        }
    }

    public void updateEmployee(Long employeeId) {
        // 
    }

    public void deleteEmployee(Long employeeId) {
        EmployeeEntity e = employeeEntitySessionBeanRemote.deleteEmployee(employeeId);
        System.out.println("Deleted employee: " + e.toString());
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
