import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayListOfBooks {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Book> bookList = new ArrayList<>();

        try {
            // Predefined 8 books
            bookList.add(new Book(1, "Java Basics", 499.99, "ISBN001", "Education", "John Smith"));
            bookList.add(new Book(2, "Data Structures", 699.50, "ISBN002", "Computer Science", "Alice Brown"));
            bookList.add(new Book(3, "Operating Systems", 850.75, "ISBN003", "Computer Science", "Andrew Tan"));
            bookList.add(new Book(4, "Database Systems", 720.40, "ISBN004", "Technology", "Michael Lee"));
            bookList.add(new Book(5, "Python Programming", 550.00, "ISBN005", "Education", "David Clark"));
            bookList.add(new Book(6, "Artificial Intelligence", 999.99, "ISBN006", "Technology", "Sophia Martin"));
            bookList.add(new Book(7, "Web Development", 650.25, "ISBN007", "Programming", "Emma Wilson"));
            bookList.add(new Book(8, "Ek tha raja Ek Thi Raani", 100000.0, "ISBN008", "Fiction", "MST"));

        } catch (InvalidBookException e) {
            System.out.println("Error creating book: " + e.getMessage());
            return; // stop program if book creation fails
        }

        int n = 0;

        try {
            System.out.print("How many books (1 to " + bookList.size() + ") do you want to calculate sum/average for? ");
            n = sc.nextInt();

            if (n < 1 || n > bookList.size()) {
                System.out.println("Invalid number! Using all " + bookList.size() + " books.");
                n = bookList.size();
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a numeric value.");
            n = bookList.size();
        }

        double totalPrice = 0;
        Book highest = bookList.get(0);
        Book lowest = bookList.get(0);

        System.out.println("\n===== Selected Book Details =====");

        for (int i = 0; i < n; i++) {
            Book b = bookList.get(i);
            b.displayBook(b);

            totalPrice += b.price;

            if (b.price > highest.price) highest = b;
            if (b.price < lowest.price) lowest = b;
        }

        double average = totalPrice / n;

        System.out.println("\n===== Final Results =====");
        System.out.println("Total Books Considered: " + n);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Average Price: " + average);
        System.out.println("Highest Price Book: " + highest.title + " - " + highest.price);
        System.out.println("Lowest Price Book: " + lowest.title + " - " + lowest.price);

        sc.close();
    }
}
