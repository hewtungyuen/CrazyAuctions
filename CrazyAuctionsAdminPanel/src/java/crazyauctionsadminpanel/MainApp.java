/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import java.util.Scanner;
import util.enumeration.EmployeeTypeEnum;

/**
 *
 * @author hewtu
 */
public class MainApp {

    private AdminOperationModule adminOperationModule;

    public MainApp() {

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
        System.out.println("Enter username:");
        // check if user is currently logged in 

        System.out.println("Enter password:");
        // check if password is correct 
        
        // retrieve employee type 
        EmployeeTypeEnum employeeType = EmployeeTypeEnum.FINANCE;
        adminOperationModule = new AdminOperationModule(); // pass in user id here? 

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
