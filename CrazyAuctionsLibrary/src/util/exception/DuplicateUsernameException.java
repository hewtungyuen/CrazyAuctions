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
public class DuplicateUsernameException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateUsernameException</code> without
     * detail message.
     */
    public DuplicateUsernameException() {
    }

    /**
     * Constructs an instance of <code>DuplicateUsernameException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateUsernameException(String msg) {
        super(msg);
    }
}
