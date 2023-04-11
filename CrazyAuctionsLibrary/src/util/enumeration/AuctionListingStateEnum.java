/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author hewtu
 */
public enum AuctionListingStateEnum {
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    DISABLED("DISABLED"),
    REQUIRE_INTERVENTION("REQUIRE_INTERVENTION");
    
    private final String state;

    private AuctionListingStateEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
