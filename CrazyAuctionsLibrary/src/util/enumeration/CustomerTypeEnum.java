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
public enum CustomerTypeEnum {
    BASIC("BASIC"),
    PREMIUM("PREMIUM");

    private final String state;

    private CustomerTypeEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
