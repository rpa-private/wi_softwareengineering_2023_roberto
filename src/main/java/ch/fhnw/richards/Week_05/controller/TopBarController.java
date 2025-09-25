package ch.fhnw.richards.Week_05.controller;

import ch.fhnw.richards.Week_05.view.InvoiceView;
import ch.fhnw.richards.Week_05.view.TopBar;

public class TopBarController {
    public TopBarController(TopBar topBar, InvoiceView invView) {
        topBar.cmbCustomer.valueProperty().addListener( (o, oldCust, newCust) -> {
            topBar.updateInvoices(newCust);
        } );

        topBar.cmbInvoice.valueProperty().addListener( (o, oldInv, newInv) -> {
            if (newInv != null) { // Will briefly be null, when a new customer is selected
                invView.showInvoice(newInv);
            }
        } );
    }
}
