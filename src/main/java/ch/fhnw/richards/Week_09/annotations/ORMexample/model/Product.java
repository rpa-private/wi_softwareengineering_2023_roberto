package ch.fhnw.richards.Week_09.annotations.ORMexample.model;

import ch.fhnw.richards.Week_09.annotations.ORM.Column;
import ch.fhnw.richards.Week_09.annotations.ORM.Entity;
import ch.fhnw.richards.Week_09.annotations.ORM.Table;
import ch.fhnw.richards.Week_09.annotations.ORM.ID;

@Entity
@Table(name="Product")
public class Product {
    @ID
    @Column(name="ID")
    private final Integer ID;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private Integer price;

    public Product(Integer productID) {
        this.ID = productID;
    }

    public Product(Integer productID, String name, Integer price) {
        this(productID);
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product " + ID + " is a " + name + " and costs " + price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
