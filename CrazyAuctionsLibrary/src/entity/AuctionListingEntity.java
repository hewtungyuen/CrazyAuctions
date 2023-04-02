/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    @OneToOne
    private BidEntity winningBid = null;
    private AddressEntity winnerDeliveryAddress = null;
    private BigDecimal currentBidPrice = 0;
    private BigDecimal reservePrice;
    private string productName;
    private Date startTime;
    private Date endTime;
    private Enum State;
    @OneToMany
    private List<BidEntity> bids;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public BidEntity getWinningBid() {
        return winningBid;
    }
    
    public void setWinningBid(BidEntity winningBid) {
        this.winningBid = winningBid;
    }
    
    public AddressEntity getWinnerDeliveryAddress() {
        return winnerDeliveryAddress;
    }
    
    public void setWinnerDeliveryAddress(AddressEntity winnerDeliveryAddress) {
        this.winnerDeliveryAddress = winnerDeliveryAddress;
    }
    
    public BigDecimal getCurrentBidPrice() {
        return currentBidPrice;
    }
    
    public void setCurrentBidPrice(BigDecimal currentBidPrice) {
        this.currentBidPrice = currentBidPrice;
    }
    
    public BigDecimal getReservePrice() {
        return reservePrice;
    }
    
    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Enum getState() {
        return state;
    }
    
    public void setState(Enum state) {
        this.state = state;
    }
    
    public List<BidEntity> getBids() {
        return bids;
    }
    
    public void setBids(List<BidEntity> bids) {
        this.bids = bids;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionListingEntity)) {
            return false;
        }
        AuctionListingEntity other = (AuctionListingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AuctionListingEntity[ id=" + id + " ]";
    }
    
}
