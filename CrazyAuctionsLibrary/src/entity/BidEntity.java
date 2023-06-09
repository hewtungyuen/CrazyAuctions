/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hewtu
 */
@Entity
public class BidEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CustomerEntity customer;

//    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AuctionListingEntity auctionListing;

//    @NotNull
//    @DecimalMin("0.06") // min starting bid price is 0.01, so min bid price = 0.01 + 0.05 = 0.06
    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal bidPrice;

//    @NotNull
    @Column(nullable = false)
    private Boolean isWinningBid; // only if it actually won

    public BidEntity() {
        this.isWinningBid = false;
    }

    public BidEntity(CustomerEntity customer, AuctionListingEntity auctionListing, BigDecimal bidPrice) {
        this();
        this.customer = customer;
        this.auctionListing = auctionListing;
        this.bidPrice = bidPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof BidEntity)) {
            return false;
        }
        BidEntity other = (BidEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bid: id = " + getId() + ", \n    bidPrice = " + getBidPrice() + ", \n    isWinningBid = " + getIsWinningBid() + ",\n    customer = "
                + getCustomer().getUsername() + ", \n    auctionListing = " + getAuctionListing().getProductName() + "\n";
    }

    /**
     * @return the customer
     */
    public CustomerEntity getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    /**
     * @return the auctionListing
     */
    public AuctionListingEntity getAuctionListing() {
        return auctionListing;
    }

    /**
     * @param auctionListing the auctionListing to set
     */
    public void setAuctionListing(AuctionListingEntity auctionListing) {
        this.auctionListing = auctionListing;
    }

    /**
     * @return the bidPrice
     */
    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    /**
     * @param bidPrice the bidPrice to set
     */
    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    /**
     * @return the isWinningBid
     */
    public Boolean getIsWinningBid() {
        return isWinningBid;
    }

    /**
     * @param isWinningBid the isWinningBid to set
     */
    public void setIsWinningBid(Boolean isWinningBid) {
        this.isWinningBid = isWinningBid;
    }

}
