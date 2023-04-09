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
public class NoSuchAddressException extends Exception{

    /**
     * Creates a new instance of <code>NoSuchAddressException</code> without
     * detail message.
     */
    public NoSuchAddressException() {
    }

    /**
     * Constructs an instance of <code>NoSuchAddressException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoSuchAddressException(String msg) {
        super(msg);
    }
}
