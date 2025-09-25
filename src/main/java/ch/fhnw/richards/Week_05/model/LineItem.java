package ch.fhnw.richards.Week_05.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItem {
    private final Integer invoiceID;
    private final Integer productID;
    private Integer quantity;
    private Integer salesPrice;

    public LineItem(Integer invoiceID, Integer productID) {
        this.invoiceID = invoiceID;
        this.productID = productID;
    }

    public LineItem(Integer invoiceID, Integer productID, Integer quantity, Integer salesPrice) {
        this(invoiceID, productID);
        this.quantity = quantity;
        this.salesPrice = salesPrice;
    }

    /**
     * Create a line-item from a row in a ResultSet
     */
    public LineItem(ResultSet rs) throws SQLException {
        this.invoiceID = rs.getInt("invoiceID");
        this.productID = rs.getInt("productID");
        this.quantity = rs.getInt("quantity");
        this.salesPrice = rs.getInt("salesPrice");
    }

    /**
     * Method to get all line-items from the database
     */
    public static List<LineItem> getLineItems(Database db, final Integer customerID) {
        List<LineItem> lineItems = new ArrayList<>();
        String query = "SELECT * FROM LineItem WHERE invoiceID = ? ORDER BY productID";
        try (
                PreparedStatement stmt = db.getConnection().prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setInt(1, customerID); // Must be outside of try(...) - hence the nested try-with-resources
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lineItems.add(new LineItem(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception " + e);
        }
        return lineItems;
    }

    public Integer getInvoiceID() {
        return invoiceID;
    }

    public Integer getProductID() {
        return productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Integer salesPrice) {
        this.salesPrice = salesPrice;
    }
}
