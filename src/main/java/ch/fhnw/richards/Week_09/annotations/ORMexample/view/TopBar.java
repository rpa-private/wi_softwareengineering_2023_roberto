package ch.fhnw.richards.Week_09.annotations.ORMexample.view;

import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.stream.Collectors;

public class TopBar extends HBox {
    private final Model model;
    private final ObservableList<Integer> customerNumbers;
    private Integer selectedCustomer;
    private ObservableList<Integer> invoiceNumbers;
    private Integer selectedInvoice;

    public final ComboBox<Integer>cmbCustomer = new ComboBox<>();
    public final ComboBox<Integer>cmbInvoice = new ComboBox<>();
    private final Region spacer = new Region();
    private final Label lblCustomer = new Label("Customer Nr.");
    private final Label lblInvoice = new Label("Invoice Nr.");

    public TopBar(Model model, InvoiceView invView) {
        super();
        this.model = model;

        // Prepare the data
        customerNumbers = fillCustomerNumbers();
        cmbCustomer.setItems(customerNumbers);
        selectFirstCustomer();

        invoiceNumbers = fillInvoiceNumbers();
        cmbInvoice.setItems(invoiceNumbers);
        updateInvoices(selectedCustomer);
        invView.showInvoice(selectedInvoice);

        // General formatting options
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        spacer.setPrefWidth(50);

        // Add components to view
        this.getChildren().addAll(lblCustomer, cmbCustomer, spacer, lblInvoice, cmbInvoice);
    }

    private ObservableList<Integer> fillCustomerNumbers() {
        return FXCollections.observableArrayList(
                model.getCustomers().stream()
                        .map(c -> c.getID()).collect(Collectors.toList())
        );
    }
    private ObservableList<Integer> fillInvoiceNumbers() {
        return FXCollections.observableArrayList(
                model.getInvoices().stream()
                        .filter(i -> i.getCustomerID().equals(selectedCustomer))
                        .map(i -> i.getID()).collect(Collectors.toList())
        );
    }

    private void selectFirstCustomer() {
        selectedCustomer = customerNumbers.get(0);
        cmbCustomer.setValue(selectedCustomer);
    }

    public void updateInvoices(Integer newCustomer) {
        selectedCustomer = newCustomer;
        invoiceNumbers = fillInvoiceNumbers();
        cmbInvoice.setItems(invoiceNumbers);
        selectedInvoice = invoiceNumbers.get(0);
        cmbInvoice.setValue(selectedInvoice);
    }

}
