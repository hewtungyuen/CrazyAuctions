/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hewtu
 */
@Local
public interface AddressEntitySessionBeanLocal {

    AddressEntity getAddress(Long addressId);

    AddressEntity deleteAddress(Long addressId);

    AddressEntity updateAddress(AddressEntity newAddress);

    List<AddressEntity> viewAllAvailableAddressesForCustomer(Long customerId);
    
}
