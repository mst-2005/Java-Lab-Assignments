import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root"; // Note: Set this to your actual MySQL username (e.g., "root")
        String password = "YourPassword";

        try (Connection con = ConnectorToDB.getConnection(url, user, password)) {
            if (con == null) return;

            try (Statement stmt = con.createStatement()) {
                // 1. Create tables and insert records
                ConnectorToDB.createTables(stmt);
                ConnectorToDB.insertRecords(con);

                System.out.println("\nRecords after Insertion");
                System.out.println("Restaurant Table:");
                ConnectorToDB.printTable(con, "Restaurant");
                System.out.println("\nMenuItem Table:");
                ConnectorToDB.printTable(con, "MenuItem");

                // 2. Select MenuItem where price <= 100
                System.out.println("\n2. MenuItems with Price <= 100");
                ConnectorToDB.selectItemsByPrice(con, 100);

                // 3. Select MenuItem available in "Cafe Java"
                System.out.println("\n3. MenuItems in 'Cafe Java':-");
                ConnectorToDB.selectItemsByRestaurant(con, "Cafe Java");

                // 4. Update MenuItem where price <= 100 to price = 200
                System.out.println("\n4. Updating Prices (<= 100 to 200)");
                ConnectorToDB.updatePrices(con);
                System.out.println("MenuItems after Update:");
                ConnectorToDB.printTable(con, "MenuItem");

                // 5. Delete MenuItem where name starts with P
                System.out.println("\n5. Deleting Items starting with 'P'");
                ConnectorToDB.deleteItemsStartingWithP(con);
                System.out.println("MenuItems after Deletion:");
                ConnectorToDB.printTable(con, "MenuItem");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}