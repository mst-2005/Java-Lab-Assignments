package com.example.jdbc_javafx.model;

import javafx.beans.property.*;

public class Restaurant {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty cuisine;
    private StringProperty location;

    public Restaurant() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.cuisine = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
    }

    public Restaurant(int id, String name, String cuisine, String location) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.cuisine = new SimpleStringProperty(cuisine);
        this.location = new SimpleStringProperty(location);
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

    public String getCuisine() {
        return cuisine.get();
    }

    public void setCuisine(String cuisine) {
        this.cuisine.set(cuisine);
    }

    public StringProperty cuisineProperty() {
        return cuisine;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }
}
