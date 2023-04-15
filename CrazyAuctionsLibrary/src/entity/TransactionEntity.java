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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.TransactionTypeEnum;

/**
 *
 * @author hewtu
 */
@Entity
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CustomerEntity customer;

//    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transactionDate;

//    @NotNull
//    @DecimalMin("0.01")
    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal transactionAmount;

//    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionTypeEnum transactionType;

//    @NotNull
//    @Size(min = 8, max = 30)
    @Column(nullable = false, length = 30)
    private String transactionDescription;

    public TransactionEntity() {
        this.transactionDate = new Date();
    }

    public TransactionEntity(CustomerEntity customer, BigDecimal transactionAmount, TransactionTypeEnum transactionType, String transactionDescription) {
        this();
        this.customer = customer;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionDescription = transactionDescription;
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
        if (!(object instanceof TransactionEntity)) {
            return false;
        }
        TransactionEntity other = (TransactionEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction: " + getId() + ",\n    transactionDate = " + getTransactionDate() + ",\n    transactionAmount = " + getTransactionAmount()
                + ",\n    transactionType = " + getTransactionType() + ",\n    transactionDescription = " + getTransactionDescription()
                + ",\n    customer = " + getCustomer().getUsername() + "\n";
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
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return the transactionAmount
     */
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount the transactionAmount to set
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return the transactionType
     */
    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the transactionDescription
     */
    public String getTransactionDescription() {
        return transactionDescription;
    }

    /**
     * @param transactionDescription the transactionDescription to set
     */
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

}
