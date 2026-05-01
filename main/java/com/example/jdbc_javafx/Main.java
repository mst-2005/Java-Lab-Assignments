package com.example.jdbc_javafx;

import com.example.jdbc_javafx.controller.MenuItemController;
import com.example.jdbc_javafx.controller.RestaurantController;
import com.example.jdbc_javafx.view.MainView;
import com.example.jdbc_javafx.view.MenuItemView;
import com.example.jdbc_javafx.view.RestaurantView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private RestaurantView restaurantView;
    private MenuItemView menuItemView;

    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();

        // Lazily initialize and show the Restaurant view
        mainView.getManageRestaurantsItem().setOnAction(e -> {
            if (restaurantView == null) {
                restaurantView = new RestaurantView();
                new RestaurantController(restaurantView); // Controller binds to the view
            }
            mainView.getBorderPane().setCenter(restaurantView.getView());
        });

        // Lazily initialize and show the Menu Item view
        mainView.getManageMenuItemsItem().setOnAction(e -> {
            if (menuItemView == null) {
                menuItemView = new MenuItemView();
                new MenuItemController(menuItemView); // Controller binds to the view
            }
            mainView.getBorderPane().setCenter(menuItemView.getView());
        });

        Scene scene = new Scene(mainView.getBorderPane(), 800, 600);
        primaryStage.setTitle("Restaurant Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
