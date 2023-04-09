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
public enum TransactionTypeEnum {
    CREDIT("CREDIT"),
    DEBIT("DEBIT");
    
    private final String state;

    private TransactionTypeEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
