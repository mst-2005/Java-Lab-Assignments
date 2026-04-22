import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class JavaFXApplication extends Application {

    static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true";
    static final String USER = "root";
    static final String PASS = "Password";

    // Restaurant UI
    private ComboBox<String> restMenuBox = new ComboBox<>();
    private TextField rIdField = new TextField();
    private TextField rNameField = new TextField();
    private TextField rAddressField = new TextField();
    private TableView<Restaurant> restTable = new TableView<>();
    private ObservableList<Restaurant> restList = FXCollections.observableArrayList();

    // MenuItem UI
    private ComboBox<String> itemMenuBox = new ComboBox<>();
    private TextField mIdField = new TextField();
    private TextField mNameField = new TextField();
    private TextField mPriceField = new TextField();
    private TextField mResIdField = new TextField();
    private TableView<MenuItem> menuTable = new TableView<>();
    private ObservableList<MenuItem> menuList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initDatabase();

        TabPane tabPane = new TabPane();
        
        Tab restTab = new Tab("Restaurants", createRestaurantPane());
        restTab.setClosable(false);
        
        Tab menuTab = new Tab("Menu Items", createMenuItemPane());
        menuTab.setClosable(false);
        
        tabPane.getTabs().addAll(restTab, menuTab);

        Scene scene = new Scene(tabPane, 750, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu-Driven Database Application");
        primaryStage.show();

        loadRestaurants();
        loadMenuItems();
    }

    private void initDatabase() {
        String createRestSql = "CREATE TABLE IF NOT EXISTS Restaurant (" +
                "Id INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Address VARCHAR(200))";
        
        String createMenuSql = "CREATE TABLE IF NOT EXISTS MenuItem (" +
                "Id INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Price DOUBLE, " +
                "ResId INT, " +
                "FOREIGN KEY (ResId) REFERENCES Restaurant(Id) ON DELETE CASCADE)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement()) {
            st.executeUpdate(createRestSql);
            st.executeUpdate(createMenuSql);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to initialize DB: " + e.getMessage());
        }
    }

    // ──────────────────────── RESTAURANT UI & LOGIC ────────────────────────
    private VBox createRestaurantPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(10));
        
        // Menu-driven operation selector
        restMenuBox.getItems().addAll("Insert", "Select", "Update", "Delete");
        restMenuBox.setValue("Insert"); // default
        
        grid.add(new Label("Select Operation:"), 0, 0);
        grid.add(restMenuBox, 1, 0);
        
        grid.add(new Label("Restaurant ID:"), 0, 1); grid.add(rIdField, 1, 1);
        grid.add(new Label("Name:"), 0, 2);          grid.add(rNameField, 1, 2);
        grid.add(new Label("Address:"), 0, 3);       grid.add(rAddressField, 1, 3);

        Button btnExecute = new Button("Execute Operation");
        btnExecute.setOnAction(e -> executeRestaurantOperation());

        TableColumn<Restaurant, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        TableColumn<Restaurant, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(d -> d.getValue().nameProperty());
        TableColumn<Restaurant, String> colAddress = new TableColumn<>("Address");
        colAddress.setCellValueFactory(d -> d.getValue().addressProperty());

        restTable.getColumns().addAll(colId, colName, colAddress);
        restTable.setItems(restList);

        VBox pane = new VBox(15, new Label("Restaurant Operations Menu"), grid, btnExecute, restTable);
        pane.setPadding(new Insets(15));
        return pane;
    }

    private void executeRestaurantOperation() {
        String operation = restMenuBox.getValue();
        try {
            switch (operation) {
                case "Insert": insertRestaurant(); break;
                case "Select": selectRestaurant(); break;
                case "Update": updateRestaurant(); break;
                case "Delete": deleteRestaurant(); break;
            }
        } catch (Exception ex) {
            showAlert("Error", "Operation failed: " + ex.getMessage());
        }
    }

    private void insertRestaurant() throws SQLException {
        String sql = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(rIdField.getText()));
            ps.setString(2, rNameField.getText());
            ps.setString(3, rAddressField.getText());
            ps.executeUpdate();
            loadRestaurants();
            clearRestFields();
        }
    }

    private void selectRestaurant() throws SQLException {
        // If ID is provided, search by ID. Otherwise load all.
        if (rIdField.getText().isEmpty()) {
            loadRestaurants();
            return;
        }
        
        restList.clear();
        String sql = "SELECT * FROM Restaurant WHERE Id=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(rIdField.getText()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    restList.add(new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address")));
                    rNameField.setText(rs.getString("Name"));
                    rAddressField.setText(rs.getString("Address"));
                } else {
                    showAlert("Result", "No Restaurant found with ID " + rIdField.getText());
                }
            }
        }
    }

    private void updateRestaurant() throws SQLException {
        String sql = "UPDATE Restaurant SET Name=?, Address=? WHERE Id=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rNameField.getText());
            ps.setString(2, rAddressField.getText());
            ps.setInt(3, Integer.parseInt(rIdField.getText()));
            int rows = ps.executeUpdate();
            if (rows > 0) { loadRestaurants(); clearRestFields(); }
            else showAlert("Warning", "Restaurant ID not found.");
        }
    }

    private void deleteRestaurant() throws SQLException {
        String sql = "DELETE FROM Restaurant WHERE Id=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(rIdField.getText()));
            int rows = ps.executeUpdate();
            if (rows > 0) { loadRestaurants(); clearRestFields(); loadMenuItems(); }
            else showAlert("Warning", "Restaurant ID not found.");
        }
    }

    private void loadRestaurants() {
        restList.clear();
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Restaurant")) {
            while(rs.next()) {
                restList.add(new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void clearRestFields() { rIdField.clear(); rNameField.clear(); rAddressField.clear(); }

    // ──────────────────────── MENU ITEM UI & LOGIC ────────────────────────
    private VBox createMenuItemPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(10));
        
        // Menu-driven operation selector
        itemMenuBox.getItems().addAll("Insert", "Select", "Update", "Delete");
        itemMenuBox.setValue("Insert"); // default

        grid.add(new Label("Select Operation:"), 0, 0);
        grid.add(itemMenuBox, 1, 0);

        grid.add(new Label("Item ID:"), 0, 1);       grid.add(mIdField, 1, 1);
        grid.add(new Label("Item Name:"), 0, 2);     grid.add(mNameField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);         grid.add(mPriceField, 1, 3);
        grid.add(new Label("Restaurant ID:"), 0, 4); grid.add(mResIdField, 1, 4);

        Button btnExecute = new Button("Execute Operation");
        btnExecute.setOnAction(e -> executeMenuItemOperation());

        TableColumn<MenuItem, Integer> colId = new TableColumn<>("Item ID");
        colId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        TableColumn<MenuItem, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(d -> d.getValue().nameProperty());
        TableColumn<MenuItem, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(d -> d.getValue().priceProperty().asObject());
        TableColumn<MenuItem, Integer> colRId = new TableColumn<>("Rest. ID");
        colRId.setCellValueFactory(d -> d.getValue().resIdProperty().asObject());

        menuTable.getColumns().addAll(colId, colName, colPrice, colRId);
        menuTable.setItems(menuList);

        VBox pane = new VBox(15, new Label("Menu Item Operations Menu"), grid, btnExecute, menuTable);
        pane.setPadding(new Insets(15));
        return pane;
    }

    private void executeMenuItemOperation() {
        String operation = itemMenuBox.getValue();
        try {
            switch (operation) {
                case "Insert": insertMenuItem(); break;
                case "Select": selectMenuItem(); break;
                case "Update": updateMenuItem(); break;
                case "Delete": deleteMenuItem(); break;
            }
        } catch (Exception ex) {
            showAlert("Error", "Operation failed: " + ex.getMessage());
        }
    }

    private void insertMenuItem() throws SQLException {
        String sql = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(mIdField.getText()));
            ps.setString(2, mNameField.getText());
            ps.setDouble(3, Double.parseDouble(mPriceField.getText()));
            ps.setInt(4, Integer.parseInt(mResIdField.getText()));
            ps.executeUpdate();
            loadMenuItems();
            clearMenuFields();
        }
    }

    private void selectMenuItem() throws SQLException {
        // If ID is provided, search by ID. Otherwise load all.
        if (mIdField.getText().isEmpty()) {
            loadMenuItems();
            return;
        }
        
        menuList.clear();
        String sql = "SELECT * FROM MenuItem WHERE Id=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(mIdField.getText()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    menuList.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"), rs.getDouble("Price"), rs.getInt("ResId")));
                    mNameField.setText(rs.getString("Name"));
                    mPriceField.setText(String.valueOf(rs.getDouble("Price")));
                    mResIdField.setText(String.valueOf(rs.getInt("ResId")));
                } else {
                    showAlert("Result", "No Menu Item found with ID " + mIdField.getText());
                }
            }
        }
    }

    private void updateMenuItem() throws SQLException {
        String sql = "UPDATE MenuItem SET Name=?, Price=?, ResId=? WHERE Id=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mNameField.getText());
            ps.setDouble(2, Double.parseDouble(mPriceField.getText()));
            ps.setInt(3, Integer.parseInt(mResIdField.getText()));
            ps.setInt(4, Integer.parseInt(mIdField.getText()));
            int rows = ps.executeUpdate();
            if (rows > 0) { loadMenuItems(); clearMenuFields(); }
            else showAlert("Warning", "Menu Item ID not found.");
        }
    }

    private void deleteMenuItem() throws SQLException {
        String sql = "DELETE FROM MenuItem WHERE Id=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(mIdField.getText()));
            int rows = ps.executeUpdate();
            if (rows > 0) { loadMenuItems(); clearMenuFields(); }
            else showAlert("Warning", "Menu Item ID not found.");
        }
    }

    private void loadMenuItems() {
        menuList.clear();
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM MenuItem")) {
            while(rs.next()) {
                menuList.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"), rs.getDouble("Price"), rs.getInt("ResId")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void clearMenuFields() { mIdField.clear(); mResIdField.clear(); mNameField.clear(); mPriceField.clear(); }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ──────────────────────── DATA MODELS ────────────────────────
    public static class Restaurant {
        private final IntegerProperty id;
        private final StringProperty name;
        private final StringProperty address;
        
        public Restaurant(int id, String name, String address) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
        }
        public IntegerProperty idProperty() { return id; }
        public StringProperty nameProperty() { return name; }
        public StringProperty addressProperty() { return address; }
    }

    public static class MenuItem {
        private final IntegerProperty id;
        private final StringProperty name;
        private final DoubleProperty price;
        private final IntegerProperty resId;
        
        public MenuItem(int id, String name, double price, int resId) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.resId = new SimpleIntegerProperty(resId);
        }
        public IntegerProperty idProperty() { return id; }
        public StringProperty nameProperty() { return name; }
        public DoubleProperty priceProperty() { return price; }
        public IntegerProperty resIdProperty() { return resId; }
    }
}
