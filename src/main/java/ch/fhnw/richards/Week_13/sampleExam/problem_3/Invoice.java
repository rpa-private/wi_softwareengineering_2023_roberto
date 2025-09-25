package ch.fhnw.richards.Week_13.sampleExam.problem_3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private static int nextID = 0;
    private final Integer ID = nextID++;
    private LocalDate Date;
    private Integer CustomerID;
    private final ArrayList<LineItem> LineItems = new ArrayList<>();
    private Integer total = 0;

    public Invoice(LocalDate Date, Integer CustomerID) {
        this.Date = Date;
        this.CustomerID = CustomerID;
    }

    public void addLineItem(LineItem item) {
        LineItems.add(item);
        total += item.getQuantity() * item.getPrice();
    }

    public Integer getID() {
        return ID;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    public ArrayList<LineItem> getLineItems() {
        return LineItems;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
