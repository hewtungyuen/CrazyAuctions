/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.EmployeeTypeEnum;
import util.exception.AuthenticationException;
import util.exception.DuplicateUsernameException;

/**
 *
 * @author hewtu
 */
@Stateless
public class EmployeeEntitySessionBean implements EmployeeEntitySessionBeanRemote, EmployeeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public EmployeeEntity login(String username, String password) throws AuthenticationException {
        Query q = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :username");
        q.setParameter("username", username);

        try {
            EmployeeEntity e = (EmployeeEntity) q.getSingleResult();
            if (Objects.equals(e.getIsLoggedIn(), Boolean.TRUE)) {
                throw new AuthenticationException(e.getUsername() + " is already logged in");
            }
            if (e.getPassword().equals(password)) {
                e.setIsLoggedIn(Boolean.TRUE);
                return e;
            } else {
                throw new AuthenticationException("Incorrect password");
            }
        } catch (NoResultException ex) {
            throw new AuthenticationException("Incorrect username");
        }
    }

    @Override
    public Long logout(Long employeeId) {
        EmployeeEntity e = em.find(EmployeeEntity.class, employeeId);
        e.setIsLoggedIn(Boolean.FALSE);
        return e.getId();
    }

    @Override
    public Boolean checkCorrectPassword(java.lang.Long employeeId, String password) {
        EmployeeEntity e = em.find(EmployeeEntity.class, employeeId);
        return e.getPassword().equals(password);
    }

    @Override
    public void changePassword(Long employeeId, String newPassword) {
        EmployeeEntity e = em.find(EmployeeEntity.class, employeeId);
        e.setPassword(newPassword);
    }

    @Override
    public Long createNewEmployee(String username, String password, EmployeeTypeEnum employeeType) throws DuplicateUsernameException { // duplicate username
        try {
            EmployeeEntity e = new EmployeeEntity(username, password, employeeType);
            em.persist(e);
            em.flush();
            return e.getId();
        } catch (PersistenceException ex) {
            throw new DuplicateUsernameException("Username: " + username + " is invalid. Please use another username.");
        }
    }

    @Override
    public List<EmployeeEntity> viewAllEmployees() {
        Query q = em.createQuery("SELECT e FROM EmployeeEntity e");
        return q.getResultList();
    }

    @Override
    public EmployeeEntity getEmployeeByUsername(String username) { // no such employee
        Query q = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :username");
        q.setParameter("username", username);
        return (EmployeeEntity) q.getSingleResult();
    }

    @Override
    public EmployeeEntity deleteEmployee(Long employeeId) { // no such employee 
        EmployeeEntity e = em.find(EmployeeEntity.class, employeeId);
        em.remove(e);
        return e;
    }

    @Override
    public EmployeeEntity getEmployeeById(Long employeeId) { // no such employee 
        EmployeeEntity e = em.find(EmployeeEntity.class, employeeId);
        return e;
    }

    @Override
    public EmployeeEntity updateEmployee(EmployeeEntity updatedEmployee) {
        em.merge(updatedEmployee);
        return updatedEmployee;
    }

}
