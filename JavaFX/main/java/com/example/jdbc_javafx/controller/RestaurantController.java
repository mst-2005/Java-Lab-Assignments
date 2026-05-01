package com.example.jdbc_javafx.controller;

import com.example.jdbc_javafx.dao.RestaurantDAO;
import com.example.jdbc_javafx.model.Restaurant;
import com.example.jdbc_javafx.view.RestaurantView;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class RestaurantController {
    private RestaurantView view;
    private RestaurantDAO dao;

    public RestaurantController(RestaurantView view) {
        this.view = view;
        this.dao = new RestaurantDAO();
        loadRestaurants();
        setupHandlers();
    }

    private void loadRestaurants() {
        try {
            view.getTableView().setItems(FXCollections.observableArrayList(dao.getAllRestaurants()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load restaurants: " + e.getMessage());
        }
    }

    private void setupHandlers() {
        view.getAddButton().setOnAction(e -> {
            try {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(view.getNameField().getText());
                restaurant.setCuisine(view.getCuisineField().getText());
                restaurant.setLocation(view.getLocationField().getText());
                dao.addRestaurant(restaurant);
                loadRestaurants();
                clearFields();
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add restaurant: " + ex.getMessage());
            }
        });

        view.getUpdateButton().setOnAction(e -> {
            Restaurant selected = view.getTableView().getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    selected.setName(view.getNameField().getText());
                    selected.setCuisine(view.getCuisineField().getText());
                    selected.setLocation(view.getLocationField().getText());
                    dao.updateRestaurant(selected);
                    loadRestaurants();
                    clearFields();
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update restaurant: " + ex.getMessage());
                }
            }
        });

        view.getDeleteButton().setOnAction(e -> {
            Restaurant selected = view.getTableView().getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    dao.deleteRestaurant(selected.getId());
                    loadRestaurants();
                    clearFields();
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete restaurant: " + ex.getMessage());
                }
            }
        });

        view.getTableView().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                view.getNameField().setText(newSelection.getName());
                view.getCuisineField().setText(newSelection.getCuisine());
                view.getLocationField().setText(newSelection.getLocation());
            }
        });
    }

    private void clearFields() {
        view.getNameField().clear();
        view.getCuisineField().clear();
        view.getLocationField().clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

