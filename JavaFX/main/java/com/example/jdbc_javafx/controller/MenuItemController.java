package com.example.jdbc_javafx.controller;

import com.example.jdbc_javafx.dao.MenuItemDAO;
import com.example.jdbc_javafx.model.MenuItem;
import com.example.jdbc_javafx.view.MenuItemView;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class MenuItemController {
    private MenuItemView view;
    private MenuItemDAO dao;

    public MenuItemController(MenuItemView view) {
        this.view = view;
        this.dao = new MenuItemDAO();
        loadMenuItems();
        setupHandlers();
    }

    private void loadMenuItems() {
        try {
            view.getTableView().setItems(FXCollections.observableArrayList(dao.getAllMenuItems()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load menu items: " + e.getMessage());
        }
    }

    private void setupHandlers() {
        view.getAddButton().setOnAction(e -> {
            try {
                MenuItem menuItem = new MenuItem();
                menuItem.setName(view.getNameField().getText());
                menuItem.setPrice(Double.parseDouble(view.getPriceField().getText()));
                menuItem.setRestaurantId(Integer.parseInt(view.getRestaurantIdField().getText()));
                dao.addMenuItem(menuItem);
                loadMenuItems();
                clearFields();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price and Restaurant ID must be valid numbers.");
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add menu item: " + ex.getMessage());
            }
        });

        view.getUpdateButton().setOnAction(e -> {
            MenuItem selected = view.getTableView().getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    selected.setName(view.getNameField().getText());
                    selected.setPrice(Double.parseDouble(view.getPriceField().getText()));
                    selected.setRestaurantId(Integer.parseInt(view.getRestaurantIdField().getText()));
                    dao.updateMenuItem(selected);
                    loadMenuItems();
                    clearFields();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price and Restaurant ID must be valid numbers.");
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update menu item: " + ex.getMessage());
                }
            }
        });

        view.getDeleteButton().setOnAction(e -> {
            MenuItem selected = view.getTableView().getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    dao.deleteMenuItem(selected.getId());
                    loadMenuItems();
                    clearFields();
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete menu item: " + ex.getMessage());
                }
            }
        });

        view.getTableView().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                view.getNameField().setText(newSelection.getName());
                view.getPriceField().setText(String.valueOf(newSelection.getPrice()));
                view.getRestaurantIdField().setText(String.valueOf(newSelection.getRestaurantId()));
            }
        });
    }

    private void clearFields() {
        view.getNameField().clear();
        view.getPriceField().clear();
        view.getRestaurantIdField().clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
