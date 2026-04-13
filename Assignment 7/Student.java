import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Student {
    private static final String FILE_NAME = "Students.csv";
    private static final String TEMP_FILE = "Temp.csv";

    public static void main(String[] args) {
        System.out.println("Read: Initial State");
        displayRecords();

        System.out.println("\nCreate: Adding 3 new records");
        addRecords();
        displayRecords();

        System.out.println("\nUpdate: Updating marks for all rows");
        updateMarks();
        displayRecords();

        System.out.println("\nUpdate: Calculating percentage for all rows");
        calculatePercentageAndUpdate();
        displayRecords();

        System.out.println("\nDelete: Deleting a row (studentId: 3)");
        deleteRecord("3");
        displayRecords();

        System.out.println("\nDemonstrating Exception Handling");
        demonstrateException();
    }

    private static void displayRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while reading records: " + e.getMessage());
        }
    }

    private static void addRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write("3,Charlie,ECE,70,75,80,0,0,\n");
            bw.write("4,David,MECH,60,65,70,0,0,\n");
            bw.write("5,Eve,CIVIL,80,85,90,0,0,\n");
        } catch (IOException e) {
            System.out.println("IOException occurred while adding records: " + e.getMessage());
        }
    }

    private static void updateMarks() {
        Path inputPath = Paths.get(FILE_NAME);
        Path tempPath = Paths.get(TEMP_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
            
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    bw.write(line + "\n");
                    isHeader = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length >= 8) {
                    if (parts[6].trim().equals("0") || parts[6].trim().isEmpty()) parts[6] = "85";
                    if (parts[7].trim().equals("0") || parts[7].trim().isEmpty()) parts[7] = "88";
                    
                    line = String.join(",", parts);
                }
                bw.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while updating marks: " + e.getMessage());
            return;
        }

        try {
            Files.move(tempPath, inputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("IOException occurred while replacing file: " + e.getMessage());
        }
    }

    private static void calculatePercentageAndUpdate() {
        Path inputPath = Paths.get(FILE_NAME);
        Path tempPath = Paths.get(TEMP_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
            
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    bw.write(line + "\n");
                    isHeader = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length >= 8) {
                    try {
                        double m1 = Double.parseDouble(parts[3]);
                        double m2 = Double.parseDouble(parts[4]);
                        double m3 = Double.parseDouble(parts[5]);
                        double m4 = Double.parseDouble(parts[6]);
                        double m5 = Double.parseDouble(parts[7]);
                        double percentage = (m1 + m2 + m3 + m4 + m5) / 5.0;
                        
                        if (parts.length < 9) {
                            String[] newParts = Arrays.copyOf(parts, 9);
                            parts = newParts;
                        }
                        parts[8] = String.format(Locale.US, "%.2f", percentage);
                    } catch (NumberFormatException ex) {
                        // ignore if parsing fails
                    }
                    line = String.join(",", parts);
                }
                bw.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while calculating percentage: " + e.getMessage());
            return;
        }

        try {
            Files.move(tempPath, inputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("IOException occurred while replacing file: " + e.getMessage());
        }
    }

    private static void deleteRecord(String studentId) {
        Path inputPath = Paths.get(FILE_NAME);
        Path tempPath = Paths.get(TEMP_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length > 0 && parts[0].equals(studentId)) {
                    continue; // Skip the row to delete
                }
                bw.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while deleting record: " + e.getMessage());
            return;
        }

        try {
            Files.move(tempPath, inputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("IOException occurred while replacing file: " + e.getMessage());
        }
    }

    private static void demonstrateException() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("NonExistentFile.csv"));
            br.readLine();
            br.close();
        } catch (IOException e) {
            System.out.println("Exception Condition Caught!");
            System.out.println("Details: " + e.toString());
        }
    }
}