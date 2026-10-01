package com.legacy.invoice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Represents a line item in an invoice.
 * Uses legacy JAXB annotations for XML binding.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceItem {

    @XmlElement
    private String description;

    @XmlElement
    private BigDecimal unitPrice;

    @XmlElement
    private Integer quantity;

    @XmlElement
    private BigDecimal lineTotal;

    // Default constructor required by JAXB
    public InvoiceItem() {
    }

    public InvoiceItem(String description, BigDecimal unitPrice, Integer quantity, BigDecimal lineTotal) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", lineTotal=" + lineTotal +
                '}';
    }
}
