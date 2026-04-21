import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectorToDB {

    public static Connection getConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Connection successful!");
                return con;
            }
        } catch (SQLException e) {
            System.err.println("Connection failed! Check your URL, user, or password.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found! Ensure the connector JAR is in your classpath.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root"; // Note: Set this to your actual MySQL username (e.g., "root")
        String password = "YourPassword";

        try (Connection con = getConnection(url, user, password)) {
            if (con == null) return;

            try (Statement stmt = con.createStatement()) {
                // 1. Create tables and insert records
                createTables(stmt);
                insertRecords(con);

                System.out.println("\n--- 1. Records after Insertion ---");
                System.out.println("Restaurant Table:");
                printTable(con, "Restaurant");
                System.out.println("\nMenuItem Table:");
                printTable(con, "MenuItem");

                // 2. Select MenuItem where price <= 100
                System.out.println("\n--- 2. MenuItems with Price <= 100 ---");
                selectItemsByPrice(con, 100);

                // 3. Select MenuItem available in "Cafe Java"
                System.out.println("\n--- 3. MenuItems in 'Cafe Java' ---");
                selectItemsByRestaurant(con, "Cafe Java");

                // 4. Update MenuItem where price <= 100 to price = 200
                System.out.println("\n--- 4. Updating Prices (<= 100 to 200)... ---");
                updatePrices(con);
                System.out.println("MenuItems after Update:");
                printTable(con, "MenuItem");

                // 5. Delete MenuItem where name starts with P
                System.out.println("\n--- 5. Deleting Items starting with 'P'... ---");
                deleteItemsStartingWithP(con);
                System.out.println("MenuItems after Deletion:");
                printTable(con, "MenuItem");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables(Statement stmt) throws SQLException {
        // Drop existing tables to ensure clean run
        stmt.executeUpdate("DROP TABLE IF EXISTS MenuItem");
        stmt.executeUpdate("DROP TABLE IF EXISTS Restaurant");

        String createRestaurantTable = "CREATE TABLE Restaurant (" +
                "Id INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Address VARCHAR(200))";
        stmt.executeUpdate(createRestaurantTable);
        System.out.println("Table 'Restaurant' created successfully.");

        String createMenuItemTable = "CREATE TABLE MenuItem (" +
                "Id INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Price DOUBLE, " +
                "ResId INT, " +
                "FOREIGN KEY (ResId) REFERENCES Restaurant(Id))";
        stmt.executeUpdate(createMenuItemTable);
        System.out.println("Table 'MenuItem' created successfully.");
    }

    public static void insertRecords(Connection con) throws SQLException {
        String insertRestaurant = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(insertRestaurant)) {
            Object[][] restaurants = {
                    {1, "Cafe Java", "123 Main St"},
                    {2, "Pizza Palace", "456 Oak St"},
                    {3, "Burger Joint", "789 Pine St"},
                    {4, "Sushi World", "101 Maple Ave"},
                    {5, "Taco Fiesta", "202 Elm St"},
                    {6, "Pasta House", "303 Cedar Ln"},
                    {7, "Steakhouse", "404 Birch Rd"},
                    {8, "Vegan Delight", "505 Spruce Way"},
                    {9, "Breakfast Club", "606 Walnut Dr"},
                    {10, "Dessert Haven", "707 Ash Ct"}
            };
            for (Object[] r : restaurants) {
                pstmt.setInt(1, (int) r[0]);
                pstmt.setString(2, (String) r[1]);
                pstmt.setString(3, (String) r[2]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("10 records inserted into 'Restaurant' table.");
        }

        String insertMenuItem = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(insertMenuItem)) {
            Object[][] menuItems = {
                    {1, "Espresso", 50.0, 1},
                    {2, "Latte", 120.0, 1},
                    {3, "Pepperoni Pizza", 350.0, 2},
                    {4, "Cheeseburger", 150.0, 3},
                    {5, "California Roll", 250.0, 4},
                    {6, "Tacos", 80.0, 5},
                    {7, "Spaghetti", 200.0, 6},
                    {8, "Ribeye", 800.0, 7},
                    {9, "Pancakes", 90.0, 9},
                    {10, "Pastry", 60.0, 10}
            };
            for (Object[] m : menuItems) {
                pstmt.setInt(1, (int) m[0]);
                pstmt.setString(2, (String) m[1]);
                pstmt.setDouble(3, (double) m[2]);
                pstmt.setInt(4, (int) m[3]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("10 records inserted into 'MenuItem' table.");
        }
    }

    public static void selectItemsByPrice(Connection con, double maxPrice) throws SQLException {
        String query = "SELECT * FROM MenuItem WHERE Price <= ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDouble(1, maxPrice);
            try (ResultSet rs = pstmt.executeQuery()) {
                printResultSet(rs);
            }
        }
    }

    public static void selectItemsByRestaurant(Connection con, String restaurantName) throws SQLException {
        String query = "SELECT m.Id, m.Name, m.Price, m.ResId FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id WHERE r.Name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, restaurantName);
            try (ResultSet rs = pstmt.executeQuery()) {
                printResultSet(rs);
            }
        }
    }

    public static void updatePrices(Connection con) throws SQLException {
        String update = "UPDATE MenuItem SET Price = 200 WHERE Price <= 100";
        try (Statement stmt = con.createStatement()) {
            int rowsAffected = stmt.executeUpdate(update);
            System.out.println(rowsAffected + " records updated.");
        }
    }

    public static void deleteItemsStartingWithP(Connection con) throws SQLException {
        String delete = "DELETE FROM MenuItem WHERE Name LIKE 'P%'";
        try (Statement stmt = con.createStatement()) {
            int rowsAffected = stmt.executeUpdate(delete);
            System.out.println(rowsAffected + " records deleted.");
        }
    }

    public static void printTable(Connection con, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            printResultSet(rs);
        }
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // Print header
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.printf("%-20s", rsmd.getColumnName(i));
        }
        System.out.println("\n--------------------------------------------------------------------------------");

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.printf("%-20s", rs.getString(i));
            }
            System.out.println();
        }
    }
}
