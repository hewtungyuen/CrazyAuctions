/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditPackageEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author hewtu
 */
@Remote
public interface CreditPackageEntitySessionBeanRemote {

    CreditPackageEntity createCreditPackage(BigDecimal credits);

    List<CreditPackageEntity> viewAllCreditPackages();

    CreditPackageEntity getCreditPackage(Long creditPackageId);

    CreditPackageEntity deleteCreditPackage(Long creditPackageId);

}
