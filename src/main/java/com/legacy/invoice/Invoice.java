package com.legacy.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO representing an invoice.
 * Annotated with Jakarta XML Binding for XML serialization/deserialization.
 */
@XmlRootElement(name = "invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {

    @XmlElement
    private String id;

    @XmlElement
    private String date;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<InvoiceItem> items = new ArrayList<InvoiceItem>();

    @XmlElement
    private BigDecimal totalAmount;

    // Default constructor required by JAXB
    public Invoice() {
    }

    public Invoice(String id, String date, List<InvoiceItem> items, BigDecimal totalAmount) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", items=" + (items != null ? items.size() : 0) +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
