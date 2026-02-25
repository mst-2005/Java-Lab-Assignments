import java.util.InputMismatchException;
import java.util.Scanner;

public class MainForVector {

    public static void main(String[] args) throws DimensionMismatchException {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter dimension (2 or 3): ");
            int dimension = sc.nextInt();

            if (dimension != 2 && dimension != 3) {
                throw new VectorException("Only 2D or 3D vectors allowed.");
            }

            double[] v1 = new double[dimension];
            double[] v2 = new double[dimension];

            System.out.println("Enter components of Vector 1:");
            for (int i = 0; i < dimension; i++) {
                v1[i] = sc.nextDouble();
            }

            System.out.println("Enter components of Vector 2:");
            for (int i = 0; i < dimension; i++) {
                v2[i] = sc.nextDouble();
            }

            Vector vector1 = new Vector(v1);
            Vector vector2 = new Vector(v2);

            Vector sum = vector1.add(vector2);
            System.out.print("Addition: ");
            sum.display();

            Vector diff = vector1.subtract(vector2);
            System.out.print("Subtraction: ");
            diff.display();

            double dot = vector1.dotProduct(vector2);
            System.out.println("Dot Product: " + dot);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numeric values.");
        } catch (VectorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}