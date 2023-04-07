/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import entity.TransactionEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.TransactionTypeEnum;

/**
 *
 * @author hewtu
 */
@Remote
public interface TransactionEntitySessionBeanRemote {

    TransactionEntity createNewTransaction(BigDecimal transactionAmount, TransactionTypeEnum transactionType, String transactionDescription, CustomerEntity customer);

    List<TransactionEntity> viewCustomerTransactions(Long customerId);
    
}
