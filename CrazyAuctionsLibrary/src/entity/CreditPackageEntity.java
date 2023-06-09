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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hewtu
 */
@Entity
public class CreditPackageEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
    @Column(nullable = false)
    private Boolean isEnabled;

//    @NotNull
    @Column(nullable = false)
    private Boolean purchasedBefore;

//    @NotNull
//    @DecimalMin("0.01")
    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal credits;

    public CreditPackageEntity() {
        this.isEnabled = true;
        this.purchasedBefore = false;
    }

    public CreditPackageEntity(BigDecimal credits) {
        this();
        this.credits = credits;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditPackageEntity)) {
            return false;
        }
        CreditPackageEntity other = (CreditPackageEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Credit Package: id = " + getId() + ", \n    isEnabled = " + getIsEnabled() + ", \n    purchasedBefore = " + getPurchasedBefore()
                + ", \n    credits = " + getCredits() + "\n";
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getPurchasedBefore() {
        return purchasedBefore;
    }

    public void setPurchasedBefore(Boolean purchasedBefore) {
        this.purchasedBefore = purchasedBefore;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

}
