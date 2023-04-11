/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.BidEntitySessionBeanRemote;
import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import entity.AuctionListingEntity;
import java.util.Scanner;
import util.exception.NoAuctionListingBidsException;

/**
 *
 * @author hewtu
 */
public class EmployeeMenus {

    private EmployeeOperations employeeOperations;
    private AdminOperations adminOperations;
    private SalesOperations salesOperations;
    private FinanceOperations financeOperations;
    private AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;
    private BidEntitySessionBeanRemote bidEntitySessionBeanRemote;
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;

    public EmployeeMenus(Long employeeId,
            EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote,
            CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote,
            AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote,
            BidEntitySessionBeanRemote bidEntitySessionBeanRemote,
            CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote
    ) {
        this.employeeOperations = new EmployeeOperations(employeeId, employeeEntitySessionBeanRemote);
        this.adminOperations = new AdminOperations(employeeId, employeeEntitySessionBeanRemote);
        this.salesOperations = new SalesOperations(auctionListingEntitySessionBeanRemote, bidEntitySessionBeanRemote, customerEntitySessionBeanRemote);
        this.financeOperations = new FinanceOperations(creditPackageEntitySessionBeanRemote);
    }

    public void employeeMenu() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Admin Panel: Employee ***\n");
            System.out.println("1: Logout");
            System.out.println("2: Change password");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    employeeOperations.logout();
                    break;
                } else if (response == 2) {
                    employeeOperations.changePassword();

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 1) {
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
            response = 0;

            while (response < 1 || response > 5) {
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

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 1) {
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
            response = 0;

            while (response < 1 || response > 5) {
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

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 1) {
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
                    salesOperations.createAuctionListing();

                } else if (response == 4) {
                    AuctionListingEntity a = salesOperations.viewAuctionListingDetails();
                    viewAuctionListingDetailsMenu(a);

                } else if (response == 5) {
                    salesOperations.viewAllAuctionListings();

                } else if (response == 6) {
                    try {
                        salesOperations.viewAllAuctionListingsWithBidBelowReserve();
                        assignWinningBidMenu();
                    } catch (NoAuctionListingBidsException ex) {
                        System.out.println(ex.getMessage());
                    }

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 1) {
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

    public void viewAuctionListingDetailsMenu(AuctionListingEntity a) {

        System.out.println("1: Update Auction Listing");
        System.out.println("2: Delete Auction Listing");
        System.out.println("3: Exit\n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");

        Integer response = scanner.nextInt();

        if (response == 1) {
            salesOperations.updateAuctionListing(a);
        } else if (response == 2) {
            salesOperations.deleteAuctionListing(a);
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
