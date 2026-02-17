public class Book {
    public int bookId;
    public String title;
    public double price;
    public String ISBN;
    public String genre;
    public String author;

    // Default Constructor
    public Book() {
        this.bookId = 0;
        title = "Ek tha raja Ek Thi Raani";
        price = 100000.0;
        ISBN = "ABCD";
        genre = "Fiction";
        author = "MST";
    }

    // Parameterized Constructor
    public Book(int bookId, String title, double price, String ISBN, String genre, String author) 
            throws InvalidBookException {

        if (price < 0) {
            throw new InvalidBookException("Price cannot be negative!");
        }

        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.ISBN = ISBN;
        this.genre = genre;
        this.author = author;
    }

    // Copy Constructor
   public Book(Book other) {
        this.bookId = other.bookId;
        this.title = other.title;
        this.price = other.price;
        this.ISBN = other.ISBN;
        this.genre = other.genre;
        this.author = other.author;
    }

    // Method to display book details
    public void displayBook(Book b) {
        System.out.println("ID: " + b.bookId);
        System.out.println("Title: " + b.title);
        System.out.println("Author: " + b.author);
        System.out.println("Price: " + b.price);
        System.out.println("ISBN: " + b.ISBN);
        System.out.println("Genre: " + b.genre);
        System.out.println("----------------------------");
    }
}
