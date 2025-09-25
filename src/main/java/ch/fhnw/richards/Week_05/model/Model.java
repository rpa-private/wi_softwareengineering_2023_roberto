package ch.fhnw.richards.Week_05.model;

import java.sql.ResultSet;
import java.util.List;

/**
 * The model maintains the list of products, information on one customer and that customers invoice-IDs,
 * and the detailed information on one invoice (including all line items).
 */
public class Model {
    private final Database db = new Database();
    private final List<Product> products;
    private final List<Customer> customers;
    private final List<Invoice> invoices;

    public Model() {
        db.createDatabase(); // Always recreate fresh data
        products = Product.getProducts(db);
        customers = Customer.getCustomers(db);
        invoices = Invoice.getInvoices(db);
    }

    public void stop() {
        db.closeConnection();
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public String getProductNameByID(Integer ID) {
        for (Product p : products) {
            if (p.getID().equals(ID)) {
                return p.getName();
            }
        }
        return null;
    }

    public Database getDb() {
        return db;
    }
}
