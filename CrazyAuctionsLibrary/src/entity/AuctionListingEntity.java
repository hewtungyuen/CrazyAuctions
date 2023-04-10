/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.AuctionListingStateEnum;

/**
 *
 * @author hewtu
 */
@Entity
public class AuctionListingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = true)
    @JoinColumn(unique = true)
    private BidEntity winningBid;

    @OneToOne(optional = true)
    private AddressEntity winnerDeliveryAddress;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal currentBidPrice;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal reservePrice;

    @Column(nullable = false, unique = true, length = 30)
    private String productName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionListingStateEnum auctionListingState;

    public AuctionListingEntity() {
        this.auctionListingState = AuctionListingStateEnum.CLOSED;
    }

    public AuctionListingEntity(BigDecimal currentBidPrice, BigDecimal reservePrice, String productName, Date startDate, Date endDate) {
        this();
        this.currentBidPrice = currentBidPrice;
        this.reservePrice = reservePrice;
        this.productName = productName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionListingEntity)) {
            return false;
        }
        AuctionListingEntity other = (AuctionListingEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AuctionListing Entity: id=" + getId() + ", \n winningBid=" + getWinningBid() + ", \n winnerDeliveryAddress=" + getWinnerDeliveryAddress()
                + ", \n currentBidPrice=" + getCurrentBidPrice() + ", \n reservePrice=" + getReservePrice() + ", \n productName=" + getProductName() 
            + ", \n startDate=" + getStartDate() + ", \n endDate=" + getEndDate() + ", \n auctionListingState=" + getAuctionListingState();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the winningBid
     */
    public BidEntity getWinningBid() {
        return winningBid;
    }

    /**
     * @param winningBid the winningBid to set
     */
    public void setWinningBid(BidEntity winningBid) {
        this.winningBid = winningBid;
    }

    /**
     * @return the winnerDeliveryAddress
     */
    public AddressEntity getWinnerDeliveryAddress() {
        return winnerDeliveryAddress;
    }

    /**
     * @param winnerDeliveryAddress the winnerDeliveryAddress to set
     */
    public void setWinnerDeliveryAddress(AddressEntity winnerDeliveryAddress) {
        this.winnerDeliveryAddress = winnerDeliveryAddress;
    }

    /**
     * @return the currentBidPrice
     */
    public BigDecimal getCurrentBidPrice() {
        return currentBidPrice;
    }

    /**
     * @param currentBidPrice the currentBidPrice to set
     */
    public void setCurrentBidPrice(BigDecimal currentBidPrice) {
        this.currentBidPrice = currentBidPrice;
    }

    /**
     * @return the reservePrice
     */
    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    /**
     * @param reservePrice the reservePrice to set
     */
    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the auctionListingState
     */
    public AuctionListingStateEnum getAuctionListingState() {
        return auctionListingState;
    }

    /**
     * @param auctionListingState the auctionListingState to set
     */
    public void setAuctionListingState(AuctionListingStateEnum auctionListingState) {
        this.auctionListingState = auctionListingState;
    }

}
