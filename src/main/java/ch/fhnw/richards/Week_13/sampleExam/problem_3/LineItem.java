package ch.fhnw.richards.Week_13.sampleExam.problem_3;

public class LineItem {
    private final Integer InvoiceID;
    private final Integer ProductID;
    private Integer Quantity;
    private Integer Price;

    public LineItem(Integer InvoiceID, Integer ProductID, Integer Quantity, Integer Price) {
        this.InvoiceID = InvoiceID;
        this.ProductID = ProductID;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public Integer getInvoiceID() {
        return InvoiceID;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }
}
