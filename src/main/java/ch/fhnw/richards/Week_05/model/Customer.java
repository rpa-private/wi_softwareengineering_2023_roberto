package ch.fhnw.richards.Week_05.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer {
    private final Integer ID;
    private String name;
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

    /**
     * Create a customer from a row in a ResultSet
     */
    public Customer(ResultSet rs) throws SQLException {
        this.ID = rs.getInt("ID");
        this.name = rs.getString("name");
        this.email = rs.getString("email");
    }

    /**
     * Method to get all customers from the database
     */
    public static List<Customer> getCustomers(Database db) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer ORDER BY ID";
        try (
                PreparedStatement stmt = db.getConnection().prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                customers.add(new Customer(rs));
            }

        } catch (SQLException e) {
            System.out.println("Exception " + e);
        }
        return customers;
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
