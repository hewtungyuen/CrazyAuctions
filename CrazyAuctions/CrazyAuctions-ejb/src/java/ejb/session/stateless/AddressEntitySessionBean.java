/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.AuctionListingEntity;
import entity.CustomerEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hewtu
 */
@Stateless
public class AddressEntitySessionBean implements AddressEntitySessionBeanRemote, AddressEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public AddressEntity getAddress(Long addressId) { // no such address
        AddressEntity a = em.find(AddressEntity.class, addressId);
        return a;
    }

    @Override
    public AddressEntity deleteAddress(Long addressId) {
        AddressEntity a = em.find(AddressEntity.class, addressId);
        Query q = em.createQuery("SELECT a FROM AuctionListingEntity a WHERE a.winnerDeliveryAddress.id = :addressId");
        q.setParameter("addressId", addressId);
        try {
            AuctionListingEntity auctionListing = (AuctionListingEntity) q.getSingleResult();
            a.setIsDisabled(Boolean.TRUE);
        } catch (NoResultException ex) {
            em.remove(a);
        }
        return a;
    }

    @Override
    public AddressEntity updateAddress(AddressEntity newAddress) {
        em.merge(newAddress);
        return newAddress;
    }

    @Override
    public List<AddressEntity> viewAllAvailableAddressesForCustomer(Long customerId) {
        Query q = em.createQuery("SELECT a FROM AddressEntity a WHERE a.customer.id = :customerId AND a.isDisabled = FALSE");
        return q.getResultList();
    }

    @Override
    public AddressEntity createAddress(String addressLine, Long customerId) {  // duplicate address
        CustomerEntity c = em.find(CustomerEntity.class, customerId);
        AddressEntity a = new AddressEntity(c, addressLine);
        em.persist(a);
        return a;
    }

    @Override
    public List<AddressEntity> viewAllAddresses(Long customerId) {
        Query q = em.createQuery("SELECT a FROM AddressEntity a WHERE a.customer.id = :customerId");
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

}
