package ch.fhnw.richards.Week_07.json.jacksonDemos;

import ch.fhnw.richards.Week_07.json.dataClasses.ClothesProduct;
import ch.fhnw.richards.Week_07.json.dataClasses.FoodProduct;
import ch.fhnw.richards.Week_07.json.dataClasses.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.LocalDateTime;

public class JsonClassHierarchies {
    public static void main(String[] args) {
        FoodProduct food = new FoodProduct(1, "Milk", LocalDateTime.of(2023, 3, 15, 12, 0, 0));
        ClothesProduct clothes = new ClothesProduct(2, "Shirt", ClothesProduct.Size.L);

        // Generate JSON from objects. This uses JsonMapper.builder because we are supporting
        // the types from java.time using an additional Jackson module (see pom.xml). This module
        // needs to be discovered and used - hence "findAndAddModules"
        ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        // Type validator for this class hierarchy - we specify the root class of the hierarchy
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Product.class)
                .build();
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
        String jsonFood = null;
        String jsonClothes = null;
        try {
            jsonFood = objectMapper.writeValueAsString(food);
            jsonClothes = objectMapper.writeValueAsString(clothes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("JSON for food: " + jsonFood);
        System.out.println("JSON for clothes: " + jsonClothes);

        // Generate objects from JSON
        Product food2 = null;
        Product clothes2 = null;
        try {
            food2 = objectMapper.readValue(jsonFood, Product.class);
            clothes2 = objectMapper.readValue(jsonClothes, Product.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Food products equal? " + food.equals(food2));
        System.out.println("Clothes products equal? " + clothes.equals(clothes2));
    }
}
