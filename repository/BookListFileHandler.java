package repository;
import entities.Book;

import java.io.*;

public class BookListFileHandler {

    private static final String BOOK_FILE = "resources/BookList.txt";

    public static void addBook(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE, true))) {
            writer.write(book.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to BookList file: " + e.getMessage());
        }
    }

    public static void updateBookData(Book updatedBook) {
        File inputFile = new File(BOOK_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromFileFormat(line);
                if (book.getBn().equals(updatedBook.getBn())) { 
                    updatedData.append(updatedBook.toString()).append(System.lineSeparator());
                } else {
                    updatedData.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading BookList file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error updating BookList file: " + e.getMessage());
        }
    }

    public static Book[] getAllBooks() {
        int size = countLinesInFile();
        Book[] books = new Book[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                books[index++] = Book.fromFileFormat(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading BookList file: " + e.getMessage());
        }
        return books;
    }

    public static Book findBookByTitle(String title) {
        Book[] books = getAllBooks();
        for (Book book : books) {
            if (book != null && book.getBookTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public static Book findBookByBn(String bn) { 
        Book[] books = getAllBooks();
        for (Book book : books) {
            if (book != null && book.getBn().equals(bn)) {
                return book;
            }
        }
        return null;
    }

    public static void deleteBookByBn(String bn) { 
        File inputFile = new File(BOOK_FILE);
        StringBuilder updatedData = new StringBuilder();
        boolean bookFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromFileFormat(line);
                if (book.getBn().equals(bn)) { 
                    bookFound = true;
                } else {
                    updatedData.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading BookList file: " + e.getMessage());
            return;
        }

        if (!bookFound) {
            System.out.println("Book with BN " + bn + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error writing updated BookList file: " + e.getMessage());
        }

        System.out.println("Book with BN " + bn + " has been deleted.");
    }

    private static int countLinesInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in BookList file: " + e.getMessage());
        }
        return count;
    }
}
