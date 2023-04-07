/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditPackageEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hewtu
 */
@Local
public interface CreditPackageEntitySessionBeanLocal {

    CreditPackageEntity createCreditPackage(BigDecimal credits);

    List<CreditPackageEntity> viewAllCreditPackages();

    CreditPackageEntity getCreditPackage(Long creditPackageId);

    CreditPackageEntity deleteCreditPackage(Long creditPackageId);

    CreditPackageEntity updateCreditPackage(CreditPackageEntity updatedCreditPackage);

    List<CreditPackageEntity> viewAllOpenCreditPackages();
    
}
