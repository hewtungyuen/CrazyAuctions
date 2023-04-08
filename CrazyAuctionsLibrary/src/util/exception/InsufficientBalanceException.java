/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author hewtu
 */
public class InsufficientBalanceException extends Exception {

    /**
     * Creates a new instance of <code>InsufficientBalanceException</code>
     * without detail message.
     */
    public InsufficientBalanceException() {
    }

    /**
     * Constructs an instance of <code>InsufficientBalanceException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}
