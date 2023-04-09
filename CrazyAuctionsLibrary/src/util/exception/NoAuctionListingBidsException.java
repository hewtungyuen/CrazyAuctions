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
public class NoAuctionListingBidsException extends Exception {

    /**
     * Creates a new instance of <code>NoAuctionListingBidsException</code>
     * without detail message.
     */
    public NoAuctionListingBidsException() {
    }

    /**
     * Constructs an instance of <code>NoAuctionListingBidsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAuctionListingBidsException(String msg) {
        super(msg);
    }
}
