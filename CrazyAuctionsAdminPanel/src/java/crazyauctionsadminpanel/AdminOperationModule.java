/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import java.util.Scanner;
import util.enumeration.EmployeeTypeEnum;

/**
 *
 * @author hewtu
 */
public class AdminOperationModule {

    private AdminOperationModuleHelper adminOperationModuleHelper;

    public AdminOperationModule(Long employeeId, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote) {
        this.adminOperationModuleHelper = new AdminOperationModuleHelper(employeeId, employeeEntitySessionBeanRemote); // pass in id here 
    }

    public void employeeMenu() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Admin Panel: Employee ***\n");
            System.out.println("1: Logout");
            System.out.println("2: Change password");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    adminOperationModuleHelper.logout();
                    break;
                } else if (response == 2) {
                    adminOperationModuleHelper.changePassword();

                } else if (response == 3) {
                    break;

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3 || response == 1) {
                break;
            }
        }
    }

    public void systemAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Admin Panel: System Admin ***\n");
            System.out.println("1: Logout");
            System.out.println("2: Change password");
            System.out.println("3: Create New Employee");
            System.out.println("4: View Employee Details");
            System.out.println("5: View All Employees");
            System.out.println("6: Exit\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    adminOperationModuleHelper.logout();
                    break;
                } else if (response == 2) {
                    adminOperationModuleHelper.changePassword();

                } else if (response == 3) {
                    adminOperationModuleHelper.createNewEmployee();

                } else if (response == 4) {
                    Long employeeId = adminOperationModuleHelper.viewEmployeeDetails();
                    viewEmployeeDetailsMenu(employeeId);

                } else if (response == 5) {
                    adminOperationModuleHelper.viewAllEmployees();

                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6 || response == 1) {
                break;
            }
        }
    }

    public void financeStaffMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Admin Panel: Finance Staff ***\n");
            System.out.println("1: Logout");
            System.out.println("2: Change password");
            System.out.println("3: Create Credit Package");
            System.out.println("4: View Credit Package Details");
            System.out.println("5: View All Credit Packages");
            System.out.println("6: Exit\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    adminOperationModuleHelper.logout();
                    break;
                } else if (response == 2) {
                    adminOperationModuleHelper.changePassword();

                } else if (response == 3) {
                    adminOperationModuleHelper.createCreditPackage();

                } else if (response == 4) {
                    adminOperationModuleHelper.viewCreditPackageDetails();
                    viewCreditPackageDetailsMenu();

                } else if (response == 5) {
                    adminOperationModuleHelper.viewAllCreditPackages();

                } else if (response == 6) {
                    break;

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6 || response == 1) {
                break;
            }
        }
    }

    public void salesStaffMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Admin Panel: Sales Staff ***\n");
            System.out.println("1: Logout");
            System.out.println("2: Change password");
            System.out.println("3: Create Auction Listing");
            System.out.println("4: View Auction Listing Details");
            System.out.println("5: View All Auction Listings");
            System.out.println("6: View All Auction Listings With Bids Below Reserve Price");
            System.out.println("7: Exit\n");
            response = 0;

            while (response < 1 || response > 7) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    adminOperationModuleHelper.logout();
                    break;
                } else if (response == 2) {
                    adminOperationModuleHelper.changePassword();

                } else if (response == 3) {
                    adminOperationModuleHelper.createAuctionListing();

                } else if (response == 4) {
                    adminOperationModuleHelper.viewAuctionListingDetails();
                    viewAuctionListingDetailsMenu();

                } else if (response == 5) {
                    adminOperationModuleHelper.viewAllAuctionListings();

                } else if (response == 6) {
                    adminOperationModuleHelper.viewAllAuctionListingsWithBidBelowReserve();
                    assignWinningBidMenu();

                } else if (response == 7) {
                    break;

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 7 || response == 1) {
                break;
            }
        }
    }

    public void viewEmployeeDetailsMenu(Long employeeId) {

        System.out.println("1: Update Employee");
        System.out.println("2: Delete Employee");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        if (response == 1) {
            adminOperationModuleHelper.updateEmployee(employeeId);
        } else if (response == 2) {
            adminOperationModuleHelper.deleteEmployee(employeeId);
        }
    }

    public void viewCreditPackageDetailsMenu() {

        System.out.println("1: Update Credit Package");
        System.out.println("2: Delete Credit Package");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        if (response == 1) {
            adminOperationModuleHelper.updateCreditPackage();
        } else if (response == 2) {
            adminOperationModuleHelper.deleteCreditPackage();
        }
    }

    public void viewAuctionListingDetailsMenu() {

        System.out.println("1: Update Auction Listing");
        System.out.println("2: Delete Auction Listing");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        response = scanner.nextInt();

        if (response == 1) {
            adminOperationModuleHelper.updateAuctionListing();
        } else if (response == 2) {
            adminOperationModuleHelper.deleteAuctionListing();
        }

    }

    public void assignWinningBidMenu() {

        System.out.println("1: Assign Winning Bid");
        System.out.println("2: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            adminOperationModuleHelper.assignWinningBid();
        }
    }
}
