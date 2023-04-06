/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import entity.EmployeeEntity;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.EmployeeTypeEnum;

/**
 *
 * @author hewtu
 */
@Singleton
@LocalBean
@Startup
public class SystemAdminInitSessionBean {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");
        query.setParameter("inUsername", "admin");

        try {
            query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            EmployeeEntity employee = new EmployeeEntity("admin", "password", EmployeeTypeEnum.ADMIN);
            em.persist(employee);
        }
    }
}
