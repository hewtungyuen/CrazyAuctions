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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CustomerTypeEnum;
import util.enumeration.TransactionTypeEnum;
import util.exception.InvalidLoginException;

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
    public CustomerEntity createCustomer(String username, String password) {
        CustomerEntity c = new CustomerEntity(CustomerTypeEnum.BASIC, username, password);
        em.persist(c);
        em.flush();
        return c;
    }

    @Override
    public CustomerEntity login(String username, String password) throws InvalidLoginException {
        Query q = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.username = :username");
        q.setParameter("username", username);

        try {
            CustomerEntity c = (CustomerEntity) q.getSingleResult();
            if (c.getPassword().equals(password)) {
                c.setIsLoggedIn(Boolean.TRUE);
                return c;
            } else {
                throw new InvalidLoginException("Incorrect password");
            }
        } catch (InvalidLoginException ex) {
            throw new InvalidLoginException("Incorrect password");
        } catch (Exception ex) {
            throw new InvalidLoginException("Incorrect username");
        }
    }

    @Override
    public void logout(Long customerId) {
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        c.setIsLoggedIn(Boolean.FALSE);
    }

    @Override
    public CustomerEntity getCustomer(Long customerId) {
        return em.find(CustomerEntity.class, customerId);
    }

    @Override
    public CreditPackageEntity purchaseCreditPackage(Long customerId, Long creditPackageId, Integer quantity) {
        CreditPackageEntity creditPackage = em.find(CreditPackageEntity.class, creditPackageId);
        creditPackage.setPurchasedBefore(Boolean.TRUE);

        CustomerEntity customer = em.find(CustomerEntity.class, customerId);
        BigDecimal numberOfCreditsPurchased = creditPackage.getCredits().multiply(new BigDecimal(quantity));
        
        credit(customerId, numberOfCreditsPurchased, "Purchase credit package");

        return creditPackage;
    }

    @Override
    public void credit(Long customerId, BigDecimal amount, String transactionDescription) {
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
    public void debit(Long customerId, BigDecimal amount, String transactionDescription) {
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        c.setCreditBalance(c.getCreditBalance().subtract(amount));
        transactionEntitySessionBeanLocal.createNewTransaction(
                amount,
                TransactionTypeEnum.DEBIT,
                transactionDescription,
                c
        );
    }

}
