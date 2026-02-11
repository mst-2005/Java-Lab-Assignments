//import java.util.Scanner;

public class ArithmeticOperations
{
    public double  num1, num2;
    public double add(double a, double b)
    {
        return a + b;
    }
    public double subtract(double a, double b)
    {
        return a - b;
    }
    public double multiply(double a, double b)
    {
        return a * b;
    }
    public double divide(double a, double b)
    {
        if (b == 0) {
            System.out.print("Error: Division by zero! "); //
            return 0; 
        }
        return a / b;
    }
    public double modulus(double a, double b)
    {
        if (b == 0) {
            System.out.print("Error: Cannot find modulus by zero! "); //
            return 0;
        }
        return a % b;
    }
    /*public static void main(String[] args) {
        ArithmeticOperations op = new ArithmeticOperations();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first number: ");
        op.num1 = sc.nextDouble();
        System.out.print("Enter second number: ");
        op.num2 = sc.nextDouble();

        System.out.println("Addition: " + op.add(op.num1, op.num2));
        System.out.println("Subtraction: " + op.subtract(op.num1, op.num2));
        System.out.println("Multiplication: " + op.multiply(op.num1, op.num2));
        System.out.println("Division: " + op.divide(op.num1, op.num2));
        System.out.println("Modulus: " + op.modulus(op.num1, op.num2));
    }*/
}
