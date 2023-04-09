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
public class NoSuchCreditPackageException extends Exception {

    /**
     * Creates a new instance of <code>NoSuchCreditPackageException</code>
     * without detail message.
     */
    public NoSuchCreditPackageException() {
    }

    /**
     * Constructs an instance of <code>NoSuchCreditPackageException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoSuchCreditPackageException(String msg) {
        super(msg);
    }
}
