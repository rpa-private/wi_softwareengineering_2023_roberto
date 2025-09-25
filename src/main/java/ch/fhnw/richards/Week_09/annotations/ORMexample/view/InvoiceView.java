package ch.fhnw.richards.Week_09.annotations.ORMexample.view;

import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Invoice;
import ch.fhnw.richards.Week_09.annotations.ORMexample.model.LineItem;
import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class InvoiceView extends VBox {
    private final Model model;

    private final Label lblID = new Label("ID");
    private final Label lblDate = new Label("Date");
    private final Label lblCustomer = new Label("Customer");
    private final Label lblTotal = new Label("Total");

    private final TextField txtID = new TextField();
    private final TextField txtDate = new TextField();
    private final TextField txtCustomer = new TextField();
    private final TextField txtTotal = new TextField();

    private final TableView<ViewLineItem> tblLineItems = new TableView<>();
    private final TableColumn<ViewLineItem, String> colProduct = new TableColumn<>("Product");
    private final TableColumn<ViewLineItem, Integer> colQuantity = new TableColumn<>("Quantity");
    private final TableColumn<ViewLineItem, Integer> colSalesPrice = new TableColumn<>("Sales price");
    private final TableColumn<ViewLineItem, Integer> colSubtotal = new TableColumn<>("Subtotal");

    // Our local data cache for the current invoice. LineItems are converted into ViewLineItems.
    private Invoice currentInvoice;
    private final ObservableList<ViewLineItem> currentLineItems = FXCollections.observableArrayList();

    public InvoiceView(Model model) {
        super();
        this.model = model;

        // General formatting options
        this.setPadding(new Insets(10));
        txtID.setDisable(true);
        txtDate.setDisable(true);
        txtCustomer.setDisable(true);
        txtTotal.setDisable(true);

        // Set up the TableView for LineItems
        tblLineItems.setEditable(false);
        colProduct.setCellValueFactory(c -> c.getValue().name);
        colQuantity.setCellValueFactory(c -> c.getValue().quantity.asObject());
        colSalesPrice.setCellValueFactory(c -> c.getValue().salesPrice.asObject());
        colSubtotal.setCellValueFactory(c -> c.getValue().subtotal.asObject());
        tblLineItems.getColumns().add(colProduct);
        tblLineItems.getColumns().add(colQuantity);
        tblLineItems.getColumns().add(colSalesPrice);
        tblLineItems.getColumns().add(colSubtotal);
        tblLineItems.setItems(currentLineItems);

        // Add components to view
        HBox idRow = new HBox(lblID, txtID);
        idRow.setAlignment(Pos.CENTER_RIGHT);
        HBox dateRow = new HBox(lblDate, txtDate);
        dateRow.setAlignment(Pos.CENTER_RIGHT);
        HBox customerRow = new HBox(lblCustomer, txtCustomer);
        customerRow.setAlignment(Pos.CENTER_RIGHT);
        HBox totalRow = new HBox(lblTotal, txtTotal);
        totalRow.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(idRow, dateRow, customerRow, tblLineItems, totalRow);
    }

    public void showInvoice(Integer invoiceNr) {
        // Update our local data cache for the current invoice
        currentInvoice = model.getInvoices().get(invoiceNr);
        currentLineItems.remove(0, currentLineItems.size()); // Remove previous entries
        for (LineItem li : currentInvoice.getLineItems()) currentLineItems.add(new ViewLineItem(model, li));

        // Update the TextFields
        txtID.setText(currentInvoice.getID().toString());
        txtDate.setText(currentInvoice.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        txtCustomer.setText(currentInvoice.getCustomerID().toString());
        txtTotal.setText(currentInvoice.getTotal().toString());
    }
}
