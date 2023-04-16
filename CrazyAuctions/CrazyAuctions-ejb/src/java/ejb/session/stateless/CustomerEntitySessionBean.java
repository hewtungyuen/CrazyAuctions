/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditPackageEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.CustomerTypeEnum;
import util.enumeration.TransactionTypeEnum;
import util.exception.AuthenticationException;
import util.exception.DuplicateUsernameException;

/**
 *
 * @author hewtu
 */
@Stateless
public class CustomerEntitySessionBean implements CustomerEntitySessionBeanRemote, CustomerEntitySessionBeanLocal {

    @EJB
    private TransactionEntitySessionBeanLocal transactionEntitySessionBeanLocal;

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public CustomerEntity createCustomer(String username, String password) throws DuplicateUsernameException { // duplicate username 
        try {
            CustomerEntity c = new CustomerEntity(CustomerTypeEnum.BASIC, username, password);
            em.persist(c);
            em.flush();
            return c;
        } catch (PersistenceException ex) {
            throw new DuplicateUsernameException("Username: " + username + " is invalid. Please use another username.");
        }
    }

    @Override
    public CustomerEntity login(String username, String password) throws AuthenticationException {
        Query q = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.username = :username");
        q.setParameter("username", username);

        try {
            CustomerEntity c = (CustomerEntity) q.getSingleResult();
            if (Objects.equals(c.getIsLoggedIn(), Boolean.TRUE)) {
                throw new AuthenticationException(c.getUsername() + " is already logged in");
            }
            if (c.getPassword().equals(password)) {
                c.setIsLoggedIn(Boolean.TRUE);
                return c;
            } else {
                throw new AuthenticationException("Incorrect password");
            }
        } catch (NoResultException ex) {
            throw new AuthenticationException("Incorrect username");
        }
    }

    @Override
    public void logout(Long customerId) throws AuthenticationException {
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        if (Objects.equals(c.getIsLoggedIn(), Boolean.FALSE)) {
            throw new AuthenticationException(c.getUsername() + " is already logged out");
        }
        c.setIsLoggedIn(Boolean.FALSE);
    }

    @Override
    public CustomerEntity getCustomer(Long customerId) { // no such customer 
        return em.find(CustomerEntity.class, customerId);
    }

    @Override
    public CreditPackageEntity purchaseCreditPackage(Long customerId, Long creditPackageId, Integer quantity) { // no such credit package
        CreditPackageEntity creditPackage = em.find(CreditPackageEntity.class, creditPackageId);
        creditPackage.setPurchasedBefore(Boolean.TRUE);

        BigDecimal numberOfCreditsPurchased = creditPackage.getCredits().multiply(new BigDecimal(quantity));
        credit(customerId, numberOfCreditsPurchased, "Purchase credit package");

        return creditPackage;
    }

    @Override
    public void credit(Long customerId, BigDecimal amount, String transactionDescription) { // no such customers
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        c.setCreditBalance(c.getCreditBalance().add(amount));
        transactionEntitySessionBeanLocal.createNewTransaction(
                amount,
                TransactionTypeEnum.CREDIT,
                transactionDescription,
                c
        );
    }

    @Override
    public void debit(Long customerId, BigDecimal amount, String transactionDescription) { // no such customer, insufficient balance
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        BigDecimal newCreditBalance = c.getCreditBalance().subtract(amount);
        c.setCreditBalance(newCreditBalance);
        transactionEntitySessionBeanLocal.createNewTransaction(
                amount,
                TransactionTypeEnum.DEBIT,
                transactionDescription,
                c
        );
    }

    @Override
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        em.merge(customer);
        return customer;
    }

    @Override
    public CustomerEntity getCustomerByUsername(String username) {
        Query q = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.username = :username");
        q.setParameter("username", username);

        return (CustomerEntity) q.getSingleResult();
    }

}
