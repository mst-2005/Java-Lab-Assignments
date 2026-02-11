public class Flower {
    public int noOfPetals;
    public String species;
    public boolean fragrance;
    public String color;
    public boolean edible;
    public String name;

    public boolean bloom(String season, String name) {
        // Use equalsIgnoreCase to avoid issues with "lily" vs "Lily"
        if ("Summer".equalsIgnoreCase(season) && "Rose".equalsIgnoreCase(name)) {
            System.out.println("Right season for me to bloom!");
            return true;
        } 
        else if ("Summer".equalsIgnoreCase(season) && "Lily".equalsIgnoreCase(name)) {
            System.out.println("Right season for the Lily!");
            return true; // Added return true
        } 
        else if ("Winter".equalsIgnoreCase(season) && "Rose".equalsIgnoreCase(name)) {
            System.out.println("I can't bloom in Winter!");
            return false;
        } 
        else {
            System.out.println(name + " cannot bloom in " + season);
            return false;
        }
    }

    public void pollinate() {
        System.out.println("This is the season for pollination!");
    }

    public static void printFlowerDetails(Flower f) {
        System.out.println("--- Flower Details ---");
        System.out.println("Name: " + f.name);
        System.out.println("Color: " + f.color);
        System.out.println("Species: " + f.species);
        System.out.println("Edible: " + f.edible);
        System.out.println("Petals: " + f.noOfPetals);
        System.out.println("Fragrance: " + f.fragrance);
        System.out.println("----------------------");
    }

    public static void main(String[] args) {
        // Flower 1: Jasmin
        Flower f1 = new Flower();
        f1.name = "Jasmin";
        f1.color = "white";
        f1.noOfPetals = 5;
        f1.fragrance = true;
        f1.edible = false;
        f1.species = "Jasmium";

        boolean isBloomed = f1.bloom("Summer", f1.name); // First declaration
        System.out.println("Is flower bloomed? " + isBloomed);
        f1.pollinate();
        printFlowerDetails(f1);

        // Flower 2: Lily
        Flower f2 = new Flower();
        f2.name = "lily";
        f2.color = "red";
        f2.noOfPetals = 5;
        f2.fragrance = true;
        f2.edible = false;
        f2.species = "roseid";

        // FIXED: Removed 'boolean' here because the variable is already declared above
        isBloomed = f2.bloom("Winter", f2.name); 
        System.out.println("Is flower bloomed? " + isBloomed);
        f2.pollinate();
        printFlowerDetails(f2);
    }
}