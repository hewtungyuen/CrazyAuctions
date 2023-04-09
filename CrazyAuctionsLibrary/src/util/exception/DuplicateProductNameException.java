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
public class DuplicateProductNameException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateProductNameException</code>
     * without detail message.
     */
    public DuplicateProductNameException() {
    }

    /**
     * Constructs an instance of <code>DuplicateProductNameException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateProductNameException(String msg) {
        super(msg);
    }
}
