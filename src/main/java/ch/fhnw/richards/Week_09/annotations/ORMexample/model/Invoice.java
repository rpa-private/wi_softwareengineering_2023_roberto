package ch.fhnw.richards.Week_09.annotations.ORMexample.model;

import ch.fhnw.richards.Week_09.annotations.ORM.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// LineItems are in a many-to-one relationship with invoices. JPA does not allow
// us to represent this with just an ID. We must have an attribute that contains
// an invoice. The reason is probably that there is no other way to know what
// class LineItem is related to - by declaring an attribute, we can access the
// type of that attribute at runtime.
//
// Therefore, we use an alternate method here: We declare lineItems to be an
// ElementCollection. The usage in full JPA is more complex that shown here,
// but the idea is the same: load items from the named table, selecting on
// the JoinColumn we name.
@Entity
@Table(name="Invoice")
public class Invoice {
    @ID
    @Column(name="ID")
    private final Integer ID;
    @Column(name="date")
    private LocalDate date;
    @Column(name="customerID")
    private Integer customerID;
    @ElementCollection
    @CollectionTable(name="LineItem", joinColumn="invoiceID")
    private List<LineItem> lineItems = new ArrayList<>();

    public Invoice(Integer ID) {
        this.ID = ID;
    }

    public Invoice(Integer ID, LocalDate date, Integer customerID) {
        this(ID);
        this.date = date;
        this.customerID = customerID;
    }

    public void addLineItem(Integer productID, Integer quantity, Integer salesPrice) {
        lineItems.add(new LineItem(this.ID, productID, quantity, salesPrice));
    }

    public void addLineItemFromProduct(Product product, Integer quantity) {
        lineItems.add(new LineItem(this.ID, product.getID(), quantity, product.getPrice()));
    }

    public void removeLineItem(LineItem li) {
        lineItems.removeIf(lineItem -> lineItem.equals(li));
    }

    public ArrayList<LineItem> getLineItems() {
        return new ArrayList<>(lineItems); // Copy, so no one else can change our list
    }

    public Integer getID() {
        return ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
     * We do not store the total; instead, we calculate it on demand.
     */
    public Integer getTotal() {
        int total = 0;
        for (LineItem li : lineItems) {
            int subtotal = li.getQuantity() * li.getSalesPrice();
            total += subtotal;
        }
        return total;
    }
}


