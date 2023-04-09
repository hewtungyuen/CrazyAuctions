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
public class DuplicateCreditPackageException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateCreditPackageException</code>
     * without detail message.
     */
    public DuplicateCreditPackageException() {
    }

    /**
     * Constructs an instance of <code>DuplicateCreditPackageException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateCreditPackageException(String msg) {
        super(msg);
    }
}
