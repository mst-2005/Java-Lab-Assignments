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
import java.util.ArrayList;
import java.util.List;

public class JavaFXApplication extends Application {

    // Database constants
    static final String URL = "jdbc:mysql://localhost:3306/college_db?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "Password";

    // UI Components
    private TextField rollField = new TextField();
    private TextField nameField = new TextField();
    private TextField branchField = new TextField();
    private TextField marksField = new TextField();
    private TableView<Student> tableView = new TableView<>();

    // Data
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Database Management (JavaFX)");

        // 1. Database Initialization
        createTable();

        // 2. Input Form (GridPane)
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Roll No:"), 0, 0);
        grid.add(rollField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Branch:"), 0, 2);
        grid.add(branchField, 1, 2);
        grid.add(new Label("Marks:"), 0, 3);
        grid.add(marksField, 1, 3);

        // 3. Buttons (HBox)
        Button insertBtn = new Button("Insert");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button refreshBtn = new Button("Refresh View");

        HBox buttonBox = new HBox(10, insertBtn, updateBtn, deleteBtn, refreshBtn);
        buttonBox.setPadding(new Insets(10));

        // 4. Table Setup
        setupTable();

        // 5. Layout Assembly
        VBox root = new VBox(10, grid, buttonBox, tableView);
        root.setPadding(new Insets(10));

        // 6. Button Handlers
        insertBtn.setOnAction(e -> handleInsert());
        updateBtn.setOnAction(e -> handleUpdate());
        deleteBtn.setOnAction(e -> handleDelete());
        refreshBtn.setOnAction(e -> loadData());

        // Initial Data Load
        loadData();

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTable() {
        TableColumn<Student, Integer> rollCol = new TableColumn<>("Roll No");
        rollCol.setCellValueFactory(data -> data.getValue().rollNoProperty().asObject());

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Student, String> branchCol = new TableColumn<>("Branch");
        branchCol.setCellValueFactory(data -> data.getValue().branchProperty());

        TableColumn<Student, Double> marksCol = new TableColumn<>("Marks");
        marksCol.setCellValueFactory(data -> data.getValue().marksProperty().asObject());

        tableView.getColumns().add(rollCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(branchCol);
        tableView.getColumns().add(marksCol);
        tableView.setItems(studentList);
    }

    // ─Database Operations ──────────────────────────────────────────────────

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                + "roll_no INT PRIMARY KEY, "
                + "name VARCHAR(50), "
                + "branch VARCHAR(30), "
                + "marks DOUBLE)";
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            showAlert("Error", "Could not create table: " + e.getMessage());
        }
    }

    private void handleInsert() {
        try {
            int roll = Integer.parseInt(rollField.getText());
            String name = nameField.getText();
            String branch = branchField.getText();
            double marks = Double.parseDouble(marksField.getText());

            String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";
            try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, roll);
                ps.setString(2, name);
                ps.setString(3, branch);
                ps.setDouble(4, marks);
                ps.executeUpdate();
                loadData();
                clearFields();
            }
        } catch (Exception e) {
            showAlert("Error", "Insert failed: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        try {
            int roll = Integer.parseInt(rollField.getText());
            double marks = Double.parseDouble(marksField.getText());

            String sql = "UPDATE students SET marks=? WHERE roll_no=?";
            try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDouble(1, marks);
                ps.setInt(2, roll);
                int rows = ps.executeUpdate();
                if (rows > 0)
                    loadData();
                else
                    showAlert("Warning", "Roll No not found.");
            }
        } catch (Exception e) {
            showAlert("Error", "Update failed: " + e.getMessage());
        }
    }

    private void handleDelete() {
        try {
            int roll = Integer.parseInt(rollField.getText());
            String sql = "DELETE FROM students WHERE roll_no=?";
            try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, roll);
                int rows = ps.executeUpdate();
                if (rows > 0)
                    loadData();
                else
                    showAlert("Warning", "Roll No not found.");
            }
        } catch (Exception e) {
            showAlert("Error", "Delete failed: " + e.getMessage());
        }
    }

    private void loadData() {
        studentList.clear();
        String sql = "SELECT * FROM students ORDER BY roll_no";
        try (Connection con = getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                studentList.add(new Student(
                        rs.getInt("roll_no"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getDouble("marks")));
            }
        } catch (SQLException e) {
            showAlert("Error", "Could not load data: " + e.getMessage());
        }
    }

    private void clearFields() {
        rollField.clear();
        nameField.clear();
        branchField.clear();
        marksField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ── Student Model Class ──────────────────────────────────────────────────
    public static class Student {
        private final IntegerProperty rollNo;
        private final StringProperty name;
        private final StringProperty branch;
        private final DoubleProperty marks;

        public Student(int rollNo, String name, String branch, double marks) {
            this.rollNo = new SimpleIntegerProperty(rollNo);
            this.name = new SimpleStringProperty(name);
            this.branch = new SimpleStringProperty(branch);
            this.marks = new SimpleDoubleProperty(marks);
        }

        public IntegerProperty rollNoProperty() {
            return rollNo;
        }

        public StringProperty nameProperty() {
            return name;
        }

        public StringProperty branchProperty() {
            return branch;
        }

        public DoubleProperty marksProperty() {
            return marks;
        }
    }
}
