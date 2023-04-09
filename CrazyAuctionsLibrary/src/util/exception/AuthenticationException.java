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
public class AuthenticationException extends Exception {

    /**
     * Creates a new instance of <code>InvalidLoginException</code> without
     * detail message.
     */
    public AuthenticationException() {
    }

    /**
     * Constructs an instance of <code>InvalidLoginException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AuthenticationException(String msg) {
        super(msg);
    }
}
