package entities;

public class Book {
    private String bookTitle;
    private String bn;
    private String author;

    public Book(String bookTitle, String bn, String author) {
        this.bookTitle = bookTitle;
        this.bn = bn;
        this.author = author;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBn() {
        return bn;
    }

    public String getAuthor() {
        return author;
    }


    public String toString() {
        return bookTitle + "," + bn + "," + author + "," ;
    }

    public static Book fromFileFormat(String line) {
        String[] parts = line.split(",");
        return new Book(parts[0], parts[1], parts[2]);
    }
}
