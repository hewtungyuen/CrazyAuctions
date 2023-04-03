/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import javax.ejb.Remote;
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
@Remote
public interface EmployeeEntitySessionBeanRemote {

    EmployeeEntity login(String username, String password) throws InvalidLoginException;

    Long logout(Long employeeId);

}
