import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InventoryManager {
    // Singleton instance
    private static InventoryManager instance;
    
    private List<Product> inventoryList;

    // Private constructor to prevent instantiation from outside
    private InventoryManager() {
        inventoryList = new ArrayList<>();
    }

    // Method to get the single instance of the class
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        inventoryList.add(product);
    }

    public Iterator<Product> returnInventory() {
        return inventoryList.iterator();
    }
}
