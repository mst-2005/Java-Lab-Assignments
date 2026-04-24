import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        // Create a single instance of InventoryManager class
        InventoryManager manager = InventoryManager.getInstance();

        // Create new products
        NewProduct p1 = new NewProduct("Laptop");
        NewProduct p2 = new NewProduct("Smartphone");

        // Create legacy item and wrap it in a ProductAdapter
        LegacyItem l1 = new LegacyItem("L-001", "Desktop Monitor");
        ProductAdapter pa1 = new ProductAdapter(l1);

        // Add products to the InventoryManager instance
        manager.addProduct(p1);
        manager.addProduct(p2);
        manager.addProduct(pa1);

        // Iterate through the inventory using Iterator
        System.out.println("Inventory Details:");
        System.out.println("------------------");
        Iterator<Product> iterator = manager.returnInventory();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            // Call displayDetails() method to print the product details
            product.displayDetails();
        }
    }
}
