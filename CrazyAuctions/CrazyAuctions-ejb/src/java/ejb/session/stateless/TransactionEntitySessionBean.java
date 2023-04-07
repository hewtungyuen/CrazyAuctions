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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.TransactionTypeEnum;

/**
 *
 * @author hewtu
 */
@Stateless
public class TransactionEntitySessionBean implements TransactionEntitySessionBeanRemote, TransactionEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public TransactionEntity createNewTransaction(BigDecimal transactionAmount, TransactionTypeEnum transactionType, String transactionDescription, CustomerEntity customer) {
        TransactionEntity t = new TransactionEntity(customer, transactionAmount, transactionType, transactionDescription);
        em.persist(t);
        em.flush();
        return t;
    }

    @Override
    public List<TransactionEntity> viewCustomerTransactions(Long customerId) {
        Query q = em.createQuery("SELECT t FROM TransactionEntity t WHERE t.customer.id = :customerId");
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

    
}
