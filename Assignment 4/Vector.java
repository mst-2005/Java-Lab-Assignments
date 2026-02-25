public class Vector {

    private final double[] components;

    // Constructor with dimension check
    public Vector(double[] components) throws VectorException {
        if (components.length != 2 && components.length != 3) {
            throw new VectorException("Only 2D or 3D vectors allowed.");
        }
        this.components = components;
    }

    public int getDimension() {
        return components.length;
    }

    // Addition
    public Vector add(Vector other) throws VectorException {
        if (this.getDimension() != other.getDimension()) {
            throw new VectorException("Dimension mismatch for addition.");
        }

        double[] result = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            result[i] = this.components[i] + other.components[i];
        }

        return new Vector(result);
    }

    // Subtraction
    public Vector subtract(Vector other) throws VectorException {
        if (this.getDimension() != other.getDimension()) {
            throw new VectorException("Dimension mismatch for subtraction.");
        }

        double[] result = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            result[i] = this.components[i] - other.components[i];
        }

        return new Vector(result);
    }

    // Dot Product
    public double dotProduct(Vector other) throws VectorException {
        if (this.getDimension() != other.getDimension()) {
            throw new VectorException("Dimension mismatch for dot product.");
        }

        double dot = 0;
        for (int i = 0; i < components.length; i++) {
            dot += this.components[i] * other.components[i];
        }

        return dot;
    }

    // Display method
    public void display() {
        System.out.print("(");
        for (int i = 0; i < components.length; i++) {
            System.out.print(components[i]);
            if (i < components.length - 1)
                System.out.print(", ");
        }
        System.out.println(")");
    }
}