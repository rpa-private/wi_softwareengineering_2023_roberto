package ch.fhnw.richards.Week_05.model;

import java.sql.*;

/**
 * We maintain a singleton database connection to the H2 server. The server is started when we first connect to it.
 */
public class Database {
    private static Connection cn; // Singleton

    public Connection getConnection() {
        if (cn == null) {
            try {
                cn = DriverManager.getConnection("jdbc:h2:~/orm", "sa", "");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return cn;
    }

    public void closeConnection() {
        try {
            cn.close();
        } catch (SQLException ignored) {
        }
        cn = null;
    }

    public void createDatabase() {
        if (cn == null) getConnection();
        try {
            Statement stmt = cn.createStatement();

            // Delete any old tables
            stmt.execute("DROP TABLE IF EXISTS LineItem");
            stmt.execute("DROP TABLE IF EXISTS Product");
            stmt.execute("DROP TABLE IF EXISTS Invoice");
            stmt.execute("DROP TABLE IF EXISTS Customer");

            stmt.execute("CREATE TABLE Product " +
                    "(ID INT NOT NULL AUTO_INCREMENT, " +
                    "Name VARCHAR(50) NOT NULL, " +
                    "Price INT NOT NULL, " +
                    "PRIMARY KEY (`ID`))");
            stmt.execute("CREATE TABLE LineItem " +
                    "(InvoiceID INT NOT NULL, " +
                    "ProductID INT NOT NULL, " +
                    "Quantity INT NOT NULL, " +
                    "SalesPrice INT NOT NULL, " +
                    "PRIMARY KEY (`InvoiceID`, `ProductID`))");
            stmt.execute("CREATE TABLE Customer " +
                    "(ID INT NOT NULL AUTO_INCREMENT, " +
                    "Name VARCHAR(50) NOT NULL, " +
                    "Email VARCHAR(50) NOT NULL, " +
                    "PRIMARY KEY (`ID`))");
            stmt.execute("CREATE TABLE Invoice " +
                    "(ID INT NOT NULL AUTO_INCREMENT, " +
                    "Date Date NOT NULL, " +
                    "CustomerID INT NOT NULL, " +
                    "PRIMARY KEY (`ID`))");
            stmt.execute("ALTER TABLE LineItem ADD CONSTRAINT fkInvoice FOREIGN KEY (InvoiceID) REFERENCES Invoice(ID)");
            stmt.execute("ALTER TABLE LineItem ADD CONSTRAINT fkProduct FOREIGN KEY (ProductID) REFERENCES Product(ID)");
            stmt.execute("ALTER TABLE Invoice ADD CONSTRAINT fkCustomer FOREIGN KEY (CustomerID) REFERENCES Customer(ID)");

            // Insert some products
            stmt.execute("INSERT INTO Product (ID, Name, Price) VALUES (0, 'Mouse', 30)");
            stmt.execute("INSERT INTO Product (ID, Name, Price) VALUES (1, 'Monitor', 250)");
            stmt.execute("INSERT INTO Product (ID, Name, Price) VALUES (2, 'PC', 1450)");
            stmt.execute("INSERT INTO Product (ID, Name, Price) VALUES (3, 'Keyboard', 60)");

            // Insert some customers
            stmt.execute("INSERT INTO Customer (ID, Name, Email) VALUES (0, 'fred', 'fred@email.com')");
            stmt.execute("INSERT INTO Customer (ID, Name, Email) VALUES (1, 'anna', 'anna@email.com')");

            // Insert some invoices and line items
            stmt.execute("INSERT INTO Invoice (ID, Date, CustomerID) VALUES (0, '2023-08-19', 0)"); // fred
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (0, 0, 1, 30)");
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (0, 3, 2, 60)");

            stmt.execute("INSERT INTO Invoice (ID, Date, CustomerID) VALUES (1, '2023-08-23', 0)"); // fred
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (1, 1, 2, 240)");
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (1, 2, 1, 1500)");

            stmt.execute("INSERT INTO Invoice (ID, Date, CustomerID) VALUES (2, '2023-08-21', 1)"); // anna
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (2, 0, 100, 25)");

            stmt.execute("INSERT INTO Invoice (ID, Date, CustomerID) VALUES (3, '2023-08-25', 1)"); // anna
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (3, 1, 10, 200)");
            stmt.execute("INSERT INTO LineItem (InvoiceID, ProductID, Quantity, SalesPrice) VALUES (3, 2, 10, 1300)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
