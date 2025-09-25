package ch.fhnw.richards.Week_09.annotations.ORMexample.model;

import ch.fhnw.richards.Week_09.annotations.ORM.Column;
import ch.fhnw.richards.Week_09.annotations.ORM.Entity;
import ch.fhnw.richards.Week_09.annotations.ORM.ID;
import ch.fhnw.richards.Week_09.annotations.ORM.Table;

import java.util.ArrayList;

@Entity
@Table(name="Customer")
public class Customer {
    @ch.fhnw.richards.Week_09.annotations.ORM.ID
    @Column(name="ID")
    private final Integer ID;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    private final ArrayList<Integer> invoices = new ArrayList<>();

    public Customer(Integer ID) {
        this.ID = ID;
    }

    public Customer(Integer ID, String name, String email) {
        this(ID);
        this.name = name;
        this.email = email;
    }

    public void addInvoice(Integer invoiceID) {
        invoices.add(invoiceID);
    }

    public void removeInvoice(Integer invoiceID) {
        invoices.removeIf(integer -> integer.equals(invoiceID));
    }

    public ArrayList<Integer> getInvoices() {
        return new ArrayList<>(invoices); // Copy, so no one else can change our list
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
