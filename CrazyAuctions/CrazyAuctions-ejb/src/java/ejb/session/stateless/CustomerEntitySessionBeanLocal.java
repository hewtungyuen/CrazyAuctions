/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CreditPackageEntity;
import entity.CustomerEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
@Local
public interface CustomerEntitySessionBeanLocal {

    CustomerEntity createCustomer(String username, String password);

    CustomerEntity login(String username, String password) throws InvalidLoginException;

    void logout(Long customerId);

    CustomerEntity getCustomer(Long customerId);

    AddressEntity createAddress(Long customer, String address);

    List<AddressEntity> viewAllAddresses(Long customerId);

    CreditPackageEntity purchaseCreditPackage(Long customerId, Long creditPackageId);
    
}
