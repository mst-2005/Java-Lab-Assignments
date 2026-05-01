package com.example.jdbc_javafx.view;

import com.example.jdbc_javafx.model.MenuItem;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MenuItemView {
    private VBox view;
    private TableView<MenuItem> tableView;
    private TextField nameField, priceField, restaurantIdField;
    private Button addButton, updateButton, deleteButton;

    public MenuItemView() {
        view = new VBox();
        createTableView();
        view.getChildren().addAll(tableView, createForm());
    }

    private void createTableView() {
        tableView = new TableView<>();
        TableColumn<MenuItem, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<MenuItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<MenuItem, Integer> restaurantIdColumn = new TableColumn<>("Restaurant ID");
        restaurantIdColumn.setCellValueFactory(cellData -> cellData.getValue().restaurantIdProperty().asObject());

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(restaurantIdColumn);
    }

    private GridPane createForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        nameField = new TextField();
        priceField = new TextField();
        restaurantIdField = new TextField();

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Price:"), 0, 1);
        form.add(priceField, 1, 1);
        form.add(new Label("Restaurant ID:"), 0, 2);
        form.add(restaurantIdField, 1, 2);

        form.add(addButton, 0, 3);
        form.add(updateButton, 1, 3);
        form.add(deleteButton, 2, 3);

        return form;
    }

    public VBox getView() {
        return view;
    }

    public TableView<MenuItem> getTableView() {
        return tableView;
    }

    public TextField getNameField() { return nameField; }
    public TextField getPriceField() { return priceField; }
    public TextField getRestaurantIdField() { return restaurantIdField; }
    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
}
