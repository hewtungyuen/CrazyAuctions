/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import java.util.Scanner;
import util.enumeration.EmployeeTypeEnum;

/**
 *
 * @author hewtu
 */
public class EmployeeMenus {

    private EmployeeOperations employeeOperations;
    private AdminOperations adminOperations;
    private SalesOperations salesOperations;
    private FinanceOperations financeOperations;

    public EmployeeMenus(Long employeeId, 
            EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote,
            CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote
    ) {
        this.employeeOperations = new EmployeeOperations(employeeId, employeeEntitySessionBeanRemote);
        this.adminOperations = new AdminOperations(employeeId, employeeEntitySessionBeanRemote);
        this.salesOperations = new SalesOperations(employeeId, employeeEntitySessionBeanRemote);
        this.financeOperations = new FinanceOperations(creditPackageEntitySessionBeanRemote);
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
                    employeeOperations.logout();
                    break;
                } else if (response == 2) {
                    employeeOperations.changePassword();

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
                    employeeOperations.logout();
                    break;
                } else if (response == 2) {
                    employeeOperations.changePassword();

                } else if (response == 3) {
                    adminOperations.createNewEmployee();

                } else if (response == 4) {
                    Long employeeId = adminOperations.viewEmployeeDetails();
                    viewEmployeeDetailsMenu(employeeId);

                } else if (response == 5) {
                    adminOperations.viewAllEmployees();

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
                    employeeOperations.logout();
                    break;
                } else if (response == 2) {
                    employeeOperations.changePassword();

                } else if (response == 3) {
                    financeOperations.createCreditPackage();

                } else if (response == 4) {
                    Long creditPackageId = financeOperations.viewCreditPackageDetails();
                    viewCreditPackageDetailsMenu(creditPackageId);

                } else if (response == 5) {
                    financeOperations.viewAllCreditPackages();

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
                    employeeOperations.logout();
                    break;
                } else if (response == 2) {
                    employeeOperations.changePassword();

                } else if (response == 3) {
                    salesOperations.createAuctionListing();

                } else if (response == 4) {
                    salesOperations.viewAuctionListingDetails();
                    viewAuctionListingDetailsMenu();

                } else if (response == 5) {
                    salesOperations.viewAllAuctionListings();

                } else if (response == 6) {
                    salesOperations.viewAllAuctionListingsWithBidBelowReserve();
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
            adminOperations.updateEmployee(employeeId);
        } else if (response == 2) {
            adminOperations.deleteEmployee(employeeId);
        }
    }

    public void viewCreditPackageDetailsMenu(Long creditPackageId) {

        System.out.println("1: Update Credit Package");
        System.out.println("2: Delete Credit Package");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        if (response == 1) {
            financeOperations.updateCreditPackage(creditPackageId);
        } else if (response == 2) {
            financeOperations.deleteCreditPackage(creditPackageId);
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
            salesOperations.updateAuctionListing();
        } else if (response == 2) {
            salesOperations.deleteAuctionListing();
        }

    }

    public void assignWinningBidMenu() {

        System.out.println("1: Assign Winning Bid");
        System.out.println("2: Exit\n");

        Scanner scanner = new Scanner(System.in);
        Integer response = scanner.nextInt();

        System.out.print("> ");

        if (response == 1) {
            salesOperations.assignWinningBid();
        }
    }
}
