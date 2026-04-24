public class LegacyItem {
    private String itemId;
    private String description;

    public LegacyItem(String itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }

    public void print() {
        System.out.println("Legacy Item - ID: " + itemId + ", Description: " + description);
    }
}
