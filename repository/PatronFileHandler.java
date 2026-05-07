package repository;
import entities.Patron;
import java.io.*;

public class PatronFileHandler {

    private static final String PATRON_FILE = "resources/Patron.txt";

    public static void addPatron(Patron patron) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATRON_FILE, true))) {
            writer.write(patron.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to Patron file: " + e.getMessage());
        }
    }

    public static void deletePatron(String userName) {
        File inputFile = new File(PATRON_FILE);
        StringBuilder updatedData = new StringBuilder();
        boolean patronFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Patron patron = Patron.fromFileFormat(line);
                if (patron.getUsername().equals(userName)) {
                    patronFound = true; 
                } else {
                    updatedData.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Patron file: " + e.getMessage());
            return;
        }

        if (!patronFound) {
            System.out.println("Patron with username " + userName + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error writing updated Patron file: " + e.getMessage());
        }

        System.out.println("Patron with username " + userName + " has been deleted.");
    }

    public static void updateProfile(String loggedInUser_Username, Patron updatedPatron) {
        File inputFile = new File(PATRON_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Patron patron = Patron.fromFileFormat(line);
                if (patron.getUsername().equals(loggedInUser_Username)) {
                    patron = updatedPatron;
                }
                updatedData.append(patron.toString()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading Patron file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error writing updated Patron file: " + e.getMessage());
        }
    }

    public static void updateBorrowCount(String username, int borrowCount) {
        File inputFile = new File(PATRON_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Patron patron = Patron.fromFileFormat(line);
                if (patron.getUsername().equals(username)) {
                    patron.updateBorrowedCount(borrowCount);
                }
                updatedData.append(patron.toString()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading borrow file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error updating borrow file: " + e.getMessage());
        }
    }

    public static Patron[] getAllPatrons() {
        int size = countLinesInFile();
        Patron[] patrons = new Patron[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(PATRON_FILE))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                patrons[index++] = Patron.fromFileFormat(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading Patron file: " + e.getMessage());
        }
        return patrons;
    }

    public static Patron findPatronByUsername(String username) {
        Patron[] patrons = getAllPatrons();
        for (Patron patron : patrons) {
            if (patron != null && patron.getUsername().equals(username)) {
                return patron;
            }
        }
        return null; 
    }

    private static int countLinesInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATRON_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in Patron file: " + e.getMessage());
        }
        return count;
    }
}