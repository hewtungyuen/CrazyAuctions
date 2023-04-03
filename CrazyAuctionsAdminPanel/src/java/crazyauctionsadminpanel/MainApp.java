/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

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

    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    public MainApp(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote) {
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
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
        AdminOperationModule adminOperationModule = new AdminOperationModule(e.getId(), employeeEntitySessionBeanRemote); // pass in user id here? 

        // render menu according to employee type 
        if (employeeType == EmployeeTypeEnum.EMPLOYEE) {
            adminOperationModule.employeeMenu();
        } else if (employeeType == EmployeeTypeEnum.ADMIN) {
            adminOperationModule.systemAdminMenu();
        } else if (employeeType == EmployeeTypeEnum.FINANCE) {
            adminOperationModule.financeStaffMenu();
        } else if (employeeType == EmployeeTypeEnum.SALES) {
            adminOperationModule.salesStaffMenu();
        }
    }

}
