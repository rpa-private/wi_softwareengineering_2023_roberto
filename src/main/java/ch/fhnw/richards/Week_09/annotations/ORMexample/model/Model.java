package ch.fhnw.richards.Week_09.annotations.ORMexample.model;

import ch.fhnw.richards.Week_09.annotations.ORM.Repository;

import java.util.List;

/**
 * The model maintains the list of products, information on one customer and that customers invoice-IDs,
 * and the detailed information on one invoice (including all line items).
 */
public class Model {
    private final Repository<Product, Integer> productRep = new Repository<>();
    private final Repository<Customer, Integer> customerRep = new Repository<>();
    private final Repository<Invoice, Integer> invoiceRep = new Repository<>();
    private final List<Product> products;
    private final List<Customer> customers;
    private final List<Invoice> invoices;

    public Model() {
        products = productRep.findAll(new Product(0));
        customers = customerRep.findAll(new Customer(0));
        invoices = invoiceRep.findAll(new Invoice(0));
    }

    public void stop() {
        Repository.close();
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
}
