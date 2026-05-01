package com.example.jdbc_javafx.model;

import javafx.beans.property.*;

public class MenuItem {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty restaurantId;

    public MenuItem() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.restaurantId = new SimpleIntegerProperty();
    }

    public MenuItem(int id, String name, double price, int restaurantId) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.restaurantId = new SimpleIntegerProperty(restaurantId);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getRestaurantId() {
        return restaurantId.get();
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId.set(restaurantId);
    }

    public IntegerProperty restaurantIdProperty() {
        return restaurantId;
    }
}
