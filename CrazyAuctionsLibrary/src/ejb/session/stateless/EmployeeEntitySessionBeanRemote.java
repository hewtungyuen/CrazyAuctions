/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.EmployeeTypeEnum;
import util.exception.AuthenticationException;
import util.exception.DuplicateUsernameException;

/**
 *
 * @author hewtu
 */
@Remote
public interface EmployeeEntitySessionBeanRemote {

    EmployeeEntity login(String username, String password) throws AuthenticationException;

    Long logout(Long employeeId);

    Boolean checkCorrectPassword(java.lang.Long employeeId, String password);

    void changePassword(Long employeeId, String newPassword);

    Long createNewEmployee(String username, String password, EmployeeTypeEnum employeeType) throws DuplicateUsernameException;

    List<EmployeeEntity> viewAllEmployees();

    EmployeeEntity getEmployeeByUsername(String username);

    EmployeeEntity deleteEmployee(Long employeeId);

    EmployeeEntity getEmployeeById(Long employeeId);

    EmployeeEntity updateEmployee(EmployeeEntity updatedEmployee);

}
