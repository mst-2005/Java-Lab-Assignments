package com.example.jdbc_javafx.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainView {
    private BorderPane borderPane;
    private MenuBar menuBar;
    private MenuItem manageRestaurants;
    private MenuItem manageMenuItems;

    public MainView() {
        borderPane = new BorderPane();
        createMenuBar();
        borderPane.setTop(menuBar);
    }

    private void createMenuBar() {
        menuBar = new MenuBar();
        Menu restaurantMenu = new Menu("Restaurant");
        manageRestaurants = new MenuItem("Manage Restaurants");
        restaurantMenu.getItems().add(manageRestaurants);

        Menu menuItemMenu = new Menu("Menu Item");
        manageMenuItems = new MenuItem("Manage Menu Items");
        menuItemMenu.getItems().add(manageMenuItems);

        menuBar.getMenus().addAll(restaurantMenu, menuItemMenu);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public MenuItem getManageRestaurantsItem() {
        return manageRestaurants;
    }

    public MenuItem getManageMenuItemsItem() {
        return manageMenuItems;
    }
}
