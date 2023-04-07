/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CustomerTypeEnum;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
@Stateless
public class CustomerEntitySessionBean implements CustomerEntitySessionBeanRemote, CustomerEntitySessionBeanLocal {

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
    public void logout(CustomerEntity customer) {
        CustomerEntity c = em.find(CustomerEntity.class, customer.getId());
        c.setIsLoggedIn(Boolean.FALSE);
    }
}
