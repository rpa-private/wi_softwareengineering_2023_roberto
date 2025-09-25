package ch.fhnw.richards.Week_05.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private final Integer ID;
    private LocalDate date;
    private Integer customerID;
    private List<LineItem> lineItems = new ArrayList<>();

    public Invoice(Integer ID) {
        this.ID = ID;
    }

    public Invoice(Integer ID, LocalDate date, Integer customerID) {
        this(ID);
        this.date = date;
        this.customerID = customerID;
    }

    /**
     * Create an invoice from a row in a ResultSet
     */
    public Invoice(ResultSet rs) throws SQLException {
        this.ID = rs.getInt("ID");
        this.date = rs.getDate("date").toLocalDate();
        this.customerID = rs.getInt("customerID");
    }

    /**
     * Method to get all invoices from the database
     */
    public static List<Invoice> getInvoices(Database db) {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoice ORDER BY ID";
        try (
                PreparedStatement stmt = db.getConnection().prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Invoice invoice = new Invoice(rs);
                invoice.lineItems = LineItem.getLineItems(db, invoice.ID);
                invoices.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println("Exception " + e);
        }
        return invoices;
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


