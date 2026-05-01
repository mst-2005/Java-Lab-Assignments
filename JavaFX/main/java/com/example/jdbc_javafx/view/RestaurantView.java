package com.example.jdbc_javafx.view;

import com.example.jdbc_javafx.model.Restaurant;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RestaurantView {
    private VBox view;
    private TableView<Restaurant> tableView;
    private TextField nameField, cuisineField, locationField;
    private Button addButton, updateButton, deleteButton;

    public RestaurantView() {
        view = new VBox();
        createTableView();
        view.getChildren().addAll(tableView, createForm());
    }

    private void createTableView() {
        tableView = new TableView<>();
        TableColumn<Restaurant, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Restaurant, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Restaurant, String> cuisineColumn = new TableColumn<>("Cuisine");
        cuisineColumn.setCellValueFactory(cellData -> cellData.getValue().cuisineProperty());

        TableColumn<Restaurant, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(cuisineColumn);
        tableView.getColumns().add(locationColumn);
    }

    private GridPane createForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        nameField = new TextField();
        cuisineField = new TextField();
        locationField = new TextField();

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Cuisine:"), 0, 1);
        form.add(cuisineField, 1, 1);
        form.add(new Label("Location:"), 0, 2);
        form.add(locationField, 1, 2);

        form.add(addButton, 0, 3);
        form.add(updateButton, 1, 3);
        form.add(deleteButton, 2, 3);

        return form;
    }

    public VBox getView() {
        return view;
    }

    public TableView<Restaurant> getTableView() {
        return tableView;
    }

    public TextField getNameField() { return nameField; }
    public TextField getCuisineField() { return cuisineField; }
    public TextField getLocationField() { return locationField; }
    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
}
