/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsclient;

import ejb.session.stateless.AddressEntitySessionBeanRemote;
import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import entity.CustomerEntity;
import java.util.Scanner;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
public class MainApp {

    private CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote;
    private CustomerOperationModule customerOperationModule;
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    private AddressEntitySessionBeanRemote addressEntitySessionBeanRemote;

    // 
    public MainApp(CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote,
            AddressEntitySessionBeanRemote addressEntitySessionBeanRemote,
            CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote
    ) {
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.addressEntitySessionBeanRemote = addressEntitySessionBeanRemote;
        this.creditPackageEntitySessionBeanRemote = creditPackageEntitySessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to OAS Auction Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doLogin();
                } else if (response == 2) {
                    doRegister();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    public void doLogin() {
        // check if credentials are correct
        // check customer type and render correct menu accordingly 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            CustomerEntity c = customerEntitySessionBeanRemote.login(username, password);
            customerOperationModule = new CustomerOperationModule(c.getId(),
                    customerEntitySessionBeanRemote,
                    addressEntitySessionBeanRemote,
                    creditPackageEntitySessionBeanRemote
            );
            customerOperationModule.menu();
        } catch (InvalidLoginException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void doRegister() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        CustomerEntity c = customerEntitySessionBeanRemote.createCustomer(username, password);
        System.out.println("Successfully registered: " + c.toString());

    }
}
