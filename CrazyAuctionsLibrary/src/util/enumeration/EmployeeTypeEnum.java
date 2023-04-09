/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author hewtu
 */
public enum EmployeeTypeEnum {
    EMPLOYEE("EMPLOYEE"),
    ADMIN("ADMIN"),
    FINANCE("FINANCE"),
    SALES("SALES");
    private final String state;

    private EmployeeTypeEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
