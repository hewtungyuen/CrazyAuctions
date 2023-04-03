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
import util.exception.InvalidLoginException;

/**
 *
 * @author hewtu
 */
@Remote
public interface EmployeeEntitySessionBeanRemote {

    EmployeeEntity login(String username, String password) throws InvalidLoginException;

    Long logout(Long employeeId);

    Boolean checkCorrectPassword(java.lang.Long employeeId, String password);

    void changePassword(Long employeeId, String newPassword);

    Long createNewEmployee(String username, String password, EmployeeTypeEnum employeeType);

    List<EmployeeEntity> viewAllEmployees();

    EmployeeEntity getEmployee(String username);

    EmployeeEntity deleteEmployee(Long employeeId);

}
