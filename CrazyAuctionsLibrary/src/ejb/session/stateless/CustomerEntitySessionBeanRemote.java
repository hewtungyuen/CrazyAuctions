/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Remote;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
@Remote
public interface CustomerEntitySessionBeanRemote {

    CustomerEntity createCustomer(String username, String password);

    CustomerEntity login(String username, String password) throws InvalidLoginException;

    void logout(CustomerEntity customer);
    
}
