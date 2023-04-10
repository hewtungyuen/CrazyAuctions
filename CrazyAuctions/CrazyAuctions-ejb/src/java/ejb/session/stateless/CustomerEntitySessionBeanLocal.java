/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CreditPackageEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.exception.AuthenticationException;

/**
 *
 * @author hewtu
 */
@Local
public interface CustomerEntitySessionBeanLocal {

    CustomerEntity createCustomer(String username, String password);

    CustomerEntity login(String username, String password) throws AuthenticationException;

    void logout(Long customerId) throws AuthenticationException;

    CustomerEntity getCustomer(Long customerId);

    CreditPackageEntity purchaseCreditPackage(Long customerId, Long creditPackageId, Integer quantity);

    void credit(Long customerId, BigDecimal amount, String transactionDescription);

    void debit(Long customerId, BigDecimal amount, String transactionDescription);

    CustomerEntity updateCustomer(CustomerEntity customer);
    
}
