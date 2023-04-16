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
import util.exception.DuplicateUsernameException;

/**
 *
 * @author hewtu
 */
public class AdminOperations {

    private Long employeeId;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    public AdminOperations(Long employeeId, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote) {
        this.employeeId = employeeId;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
    }

    // all employees 
    public void logout() {
        EmployeeEntity e = employeeEntitySessionBeanRemote.getEmployeeById(employeeId);
        if (e.getIsLoggedIn()) {
            employeeEntitySessionBeanRemote.logout(employeeId);
        } else {
            System.out.println(e.getUsername() + " is already logged out");
        }
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

        try {
            Long newEmployeeId = employeeEntitySessionBeanRemote.createNewEmployee(username, password, employeeType);
            System.out.println("Created employee: " + newEmployeeId);
        } catch (DuplicateUsernameException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Long viewEmployeeDetails() {
        // call toString method of employee 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee username");
        String username = scanner.nextLine();

        EmployeeEntity e = employeeEntitySessionBeanRemote.getEmployeeByUsername(username);
        System.out.println(e.toString());
        return e.getId();
    }

    public void viewAllEmployees() {
        List<EmployeeEntity> employees = employeeEntitySessionBeanRemote.viewAllEmployees();
        for (EmployeeEntity e : employees) {
            System.out.println(e.toString());
        }

        if (employees.isEmpty()) {
            System.out.println("No employees");
        }
    }

    public void updateEmployee(Long employeeId) {
        EmployeeEntity e = employeeEntitySessionBeanRemote.getEmployeeById(employeeId);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new employee type: 0: no change, 1: Employee, 2: Finance, 3: Sales, 4: Admin");
        Integer response = scanner.nextInt();

        if (response == 1) {
            e.setEmployeeType(EmployeeTypeEnum.EMPLOYEE);
        } else if (response == 2) {
            e.setEmployeeType(EmployeeTypeEnum.FINANCE);
        } else if (response == 3) {
            e.setEmployeeType(EmployeeTypeEnum.SALES);
        } else if (response == 4) {
            e.setEmployeeType(EmployeeTypeEnum.ADMIN);
        } else if (response == 0) {
        } else {
            System.out.println("Invalid input, try again");
        }

        EmployeeEntity updatedEmployee = employeeEntitySessionBeanRemote.updateEmployee(e);
        System.out.println("Updated: " + updatedEmployee.toString());
    }

    public void deleteEmployee(Long employeeId) {
        EmployeeEntity e = employeeEntitySessionBeanRemote.deleteEmployee(employeeId);
        System.out.println("Deleted employee: " + e.toString());
    }
}
