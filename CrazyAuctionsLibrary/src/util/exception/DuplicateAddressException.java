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
public class DuplicateAddressException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateAddressException</code> without
     * detail message.
     */
    public DuplicateAddressException() {
    }

    /**
     * Constructs an instance of <code>DuplicateAddressException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateAddressException(String msg) {
        super(msg);
    }
}
