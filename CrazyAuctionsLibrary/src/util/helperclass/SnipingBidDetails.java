/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.helperclass;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author hewtu
 */
public class SnipingBidDetails implements Serializable {

    private Long customerId;
    private Long auctionListingId;
    private BigDecimal snipingBidPrice;
    private Date triggerDate;

    public SnipingBidDetails(Long customerId, Long auctionListingId, BigDecimal snipingBidPrice, Date triggerDate) {
        this.customerId = customerId;
        this.auctionListingId = auctionListingId;
        this.snipingBidPrice = snipingBidPrice;
        this.triggerDate = triggerDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAuctionListingId() {
        return auctionListingId;
    }

    public void setAuctionListingId(Long auctionListingId) {
        this.auctionListingId = auctionListingId;
    }

    public BigDecimal getSnipingBidPrice() {
        return snipingBidPrice;
    }

    public void setSnipingBidPrice(BigDecimal snipingBidPrice) {
        this.snipingBidPrice = snipingBidPrice;
    }

    public Date getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(Date triggerDate) {
        this.triggerDate = triggerDate;
    }

}
