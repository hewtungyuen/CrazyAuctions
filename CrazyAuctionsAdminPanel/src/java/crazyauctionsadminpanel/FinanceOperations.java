/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsadminpanel;

import ejb.session.stateless.CreditPackageEntitySessionBeanRemote;
import entity.CreditPackageEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hewtu
 */
public class FinanceOperations {

    private CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote;

    public FinanceOperations(CreditPackageEntitySessionBeanRemote creditPackageEntitySessionBeanRemote) {
        this.creditPackageEntitySessionBeanRemote = creditPackageEntitySessionBeanRemote;
    }

    // finance staff
    public void createCreditPackage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of credits: ");
        BigDecimal credits = scanner.nextBigDecimal();
        CreditPackageEntity c = creditPackageEntitySessionBeanRemote.createCreditPackage(credits);
        System.out.println("Created: " + c.toString());
    }

    public Long viewCreditPackageDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter credit package id: ");
        Long id = scanner.nextLong();

        CreditPackageEntity c = creditPackageEntitySessionBeanRemote.getCreditPackage(id);
        System.out.println(c.toString());
        return c.getId();
    }

    public void viewAllCreditPackages() {
        List<CreditPackageEntity> packages = creditPackageEntitySessionBeanRemote.viewAllCreditPackages();
        for (CreditPackageEntity p : packages) {
            System.out.println(p.toString());
        }
        if (packages.isEmpty()) {
            System.out.println("No credit packages");
        }
    }

    public void updateCreditPackage(Long creditPackageId) {
        CreditPackageEntity c = creditPackageEntitySessionBeanRemote.getCreditPackage(creditPackageId);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new number of credits: (0 if no change)");
        BigDecimal credits = scanner.nextBigDecimal();

        if (credits.compareTo(BigDecimal.ZERO) > 0) {
            c.setCredits(credits);
        }

        CreditPackageEntity updatedCreditPackage = creditPackageEntitySessionBeanRemote.updateCreditPackage(c);
        System.out.println("Updated: " + updatedCreditPackage.toString());

    }

    public void deleteCreditPackage(Long creditPackageId) {
        CreditPackageEntity c = creditPackageEntitySessionBeanRemote.deleteCreditPackage(creditPackageId);
        System.out.println("Deleted: " + c.toString());
    }
}
