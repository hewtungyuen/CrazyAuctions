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
public class NoSuchEmployeeException extends Exception {

    /**
     * Creates a new instance of <code>NoSuchEmployeeException</code> without
     * detail message.
     */
    public NoSuchEmployeeException() {
    }

    /**
     * Constructs an instance of <code>NoSuchEmployeeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoSuchEmployeeException(String msg) {
        super(msg);
    }
}
