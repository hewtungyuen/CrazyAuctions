/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
@Stateless
public class EmployeeEntitySessionBean implements EmployeeEntitySessionBeanRemote, EmployeeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public EmployeeEntity login(String username, String password) throws InvalidLoginException {
        Query q = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :username");
        q.setParameter("username", username);

        try {
            EmployeeEntity e = (EmployeeEntity) q.getSingleResult();
            if (e.getPassword().equals(password)) {
                e.setIsLoggedIn(Boolean.TRUE);
                return e;
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
    public Long logout(Long employeeId) {
        EmployeeEntity e = em.find(EmployeeEntity.class, employeeId);
        e.setIsLoggedIn(Boolean.FALSE);
        return e.getId();
    }
}
