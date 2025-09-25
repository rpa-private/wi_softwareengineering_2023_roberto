package ch.fhnw.richards.Week_09.annotations.ORMexample;

import ch.fhnw.richards.Week_09.annotations.ORM.Repository;
import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Product;

/**
 * Just a simple demo, to show that our code can update values in the database.
 */
public class MainTest {
    public static void main(String[] args) {
        Repository<Product, Integer> rep = new Repository<>(); // Note: first use also creates sample database

       Product product = new Product(4);
       rep.delete(product);

       System.out.print("Current product 3 is     ");
       product = rep.findOne(new Product(3));
       System.out.println(product);


        System.out.print("After update, product 3 is     ");
        product.setPrice(1000);
        product.setName("expensive");
        rep.save(product);
        product = rep.findOne(new Product(3));
        System.out.println(product);

        product = new Product(7);
        product.setName("New product");
        product.setPrice(10000);
        rep.save(product);
        product = rep.findOne(product);
        System.out.println("new product is     " + product);

        Repository.close();
    }
}
