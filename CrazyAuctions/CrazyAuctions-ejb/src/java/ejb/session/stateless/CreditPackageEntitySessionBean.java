/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditPackageEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hewtu
 */
@Stateless
public class CreditPackageEntitySessionBean implements CreditPackageEntitySessionBeanRemote, CreditPackageEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public CreditPackageEntity createCreditPackage(BigDecimal credits) {
        CreditPackageEntity c = new CreditPackageEntity(credits);
        em.persist(c);
        em.flush();
        return c;
    }

    @Override
    public List<CreditPackageEntity> viewAllCreditPackages() {
        Query q = em.createQuery("SELECT c FROM CreditPackageEntity c");
        return q.getResultList();
    }

    @Override
    public CreditPackageEntity getCreditPackage(Long creditPackageId) {
        CreditPackageEntity c = em.find(CreditPackageEntity.class, creditPackageId);
        return c;
    }

    @Override
    public CreditPackageEntity deleteCreditPackage(Long creditPackageId) {
        CreditPackageEntity c = em.find(CreditPackageEntity.class, creditPackageId);
        if (c.getPurchasedBefore().equals(true)) {
            c.setIsEnabled(Boolean.FALSE);
        } else {
            em.remove(c);
        }
        return c;
    }

    @Override
    public CreditPackageEntity updateCreditPackage(CreditPackageEntity updatedCreditPackage) {
        em.merge(updatedCreditPackage);
        return updatedCreditPackage;
    }

    @Override
    public List<CreditPackageEntity> viewAllOpenCreditPackages() {
        Query q = em.createQuery("SELECT c FROM CreditPackageEntity c WHERE c.isEnabled = TRUE");
        return q.getResultList();
    }
}
