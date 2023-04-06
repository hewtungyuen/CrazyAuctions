/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.AuctionListingEntitySessionBeanRemote;
import ejb.session.stateless.BidEntitySessionBeanRemote;
import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import entity.EmployeeEntity;
import java.util.Scanner;
import util.enumeration.EmployeeTypeEnum;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
public class MainApp {

    private AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;
    private CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote;
    private BidEntitySessionBeanRemote bidEntitySessionBeanRemote;

    public MainApp(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote,
            CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote,
            AuctionListingEntitySessionBeanRemote auctionListingEntitySessionBeanRemote,
            BidEntitySessionBeanRemote bidEntitySessionBeanRemote
    ) {
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.creditPackageEntitySessionBeanRemote = creditPackageEntitySessionBeanRemote;
        this.auctionListingEntitySessionBeanRemote = auctionListingEntitySessionBeanRemote;
        this.bidEntitySessionBeanRemote = bidEntitySessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to OAS Admin Panel ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doLogin();
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

    public void doLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();
        EmployeeEntity e;
        try {
            e = employeeEntitySessionBeanRemote.login(username, password);

        } catch (InvalidLoginException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        // retrieve employee type 
        EmployeeTypeEnum employeeType = e.getEmployeeType();
        EmployeeMenus employeeMenu = new EmployeeMenus(e.getId(),
                employeeEntitySessionBeanRemote,
                creditPackageEntitySessionBeanRemote,
                auctionListingEntitySessionBeanRemote,
                bidEntitySessionBeanRemote
        );

        // render menu according to employee type 
        if (employeeType == EmployeeTypeEnum.EMPLOYEE) {
            employeeMenu.employeeMenu();
        } else if (employeeType == EmployeeTypeEnum.ADMIN) {
            employeeMenu.systemAdminMenu();
        } else if (employeeType == EmployeeTypeEnum.FINANCE) {
            employeeMenu.financeStaffMenu();
        } else if (employeeType == EmployeeTypeEnum.SALES) {
            employeeMenu.salesStaffMenu();
        }
    }

}
