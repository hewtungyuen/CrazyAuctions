/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hewtu
 */
@Stateless
public class AddressEntitySessionBean implements AddressEntitySessionBeanRemote, AddressEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Override
    public AddressEntity getAddress(Long addressId) {
        AddressEntity a = em.find(AddressEntity.class, addressId);
        return a;
    }

    @Override
    public AddressEntity deleteAddress(Long addressId) {
        AddressEntity a = em.find(AddressEntity.class, addressId);
        em.remove(a);
        a.getCustomer().getAddresses().remove(a);
        return a;
    }

    @Override
    public AddressEntity updateAddress(AddressEntity newAddress) {
        em.merge(newAddress);
        return newAddress;
    }

}
