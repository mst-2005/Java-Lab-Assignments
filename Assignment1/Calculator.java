import java.util.Scanner;

public class Calculator
{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true)
            {
                System.out.println("Select one of the operations:\n1. Addition\n2. Subtraction\n3. Multiplication\n4. Division\n5. Modulus");
                System.out.println("Enter your choice (1-5): ");
                int ch = scan.nextInt();
                if (ch == 0) {
                    System.out.println("Exiting the program.");
                    scan.close(); 
                    break;
                }

                // Check for valid range before asking for numbers
                if (ch < 1 || ch > 5) {
                    System.out.println("Invalid operation! Please try again.");
                    continue;
                }

                ArithmeticOperations op = new ArithmeticOperations();
                System.out.print("Enter first number: ");
                op.num1 = scan.nextDouble();
                System.out.print("Enter second number: ");
                op.num2 = scan.nextDouble();
                switch(ch)
                {
                    case 1 -> System.out.println("Addition: " + op.add(op.num1, op.num2));
                    case 2 -> System.out.println("Subtraction: " + op.subtract(op.num1, op.num2));
                    case 3 -> System.out.println("Multiplication: " + op.multiply(op.num1, op.num2));
                    case 4 -> System.out.println("Division: " + op.divide(op.num1, op.num2));
                    case 5 -> System.out.println("Modulus: " + op.modulus(op.num1, op.num2));
                    default -> System.out.println("Invalid operation!");
                }
            }
    }
}
