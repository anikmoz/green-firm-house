package com.green.firm.domain;

import com.green.firm.domain.enumeration.paymentStatus;
import com.green.firm.domain.enumeration.weightTypes;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustomerBought.
 */
@Entity
@Table(name = "customer_bought")
public class CustomerBought implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "weight_type", nullable = false)
    private weightTypes weightType;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private ZonedDateTime deliveryDate;

    @Column(name = "remarks")
    private String remarks;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private paymentStatus status;

    @NotNull
    @Column(name = "total_weight", nullable = false)
    private Integer totalWeight;

    @ManyToOne(optional = false)
    @NotNull
    private ProductType productType;

    @ManyToOne(optional = false)
    @NotNull
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerBought id(Long id) {
        this.id = id;
        return this;
    }

    public weightTypes getWeightType() {
        return this.weightType;
    }

    public CustomerBought weightType(weightTypes weightType) {
        this.weightType = weightType;
        return this;
    }

    public void setWeightType(weightTypes weightType) {
        this.weightType = weightType;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public CustomerBought unitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public CustomerBought totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTime getDeliveryDate() {
        return this.deliveryDate;
    }

    public CustomerBought deliveryDate(ZonedDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(ZonedDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public CustomerBought remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public paymentStatus getStatus() {
        return this.status;
    }

    public CustomerBought status(paymentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(paymentStatus status) {
        this.status = status;
    }

    public Integer getTotalWeight() {
        return this.totalWeight;
    }

    public CustomerBought totalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public CustomerBought productType(ProductType productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustomerBought customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerBought)) {
            return false;
        }
        return id != null && id.equals(((CustomerBought) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerBought{" +
            "id=" + getId() +
            ", weightType='" + getWeightType() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", totalPrice=" + getTotalPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalWeight=" + getTotalWeight() +
            "}";
    }
}
