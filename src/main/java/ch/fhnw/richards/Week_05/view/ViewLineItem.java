package ch.fhnw.richards.Week_05.view;

import ch.fhnw.richards.Week_05.model.LineItem;
import ch.fhnw.richards.Week_05.model.Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewLineItem {
    final SimpleStringProperty name = new SimpleStringProperty();
    final SimpleIntegerProperty quantity = new SimpleIntegerProperty();
    final SimpleIntegerProperty salesPrice = new SimpleIntegerProperty();
    final SimpleIntegerProperty subtotal = new SimpleIntegerProperty();

    public ViewLineItem(Model model, LineItem li) {
        name.setValue(model.getProductNameByID(li.getProductID()));
        quantity.setValue(li.getQuantity());
        salesPrice.setValue(li.getSalesPrice());
        subtotal.setValue(li.getQuantity() * li.getSalesPrice());
    }
}
