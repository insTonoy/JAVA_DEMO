package entities;

public class Patron extends User {
    private String patronId;
    private int bookBorrowedCount;

    public Patron(String username, String password, String name, String email, String patronId, int bookBorrowedCount) {
        super(username, password, name, email);
        this.patronId = patronId;
        this.bookBorrowedCount = bookBorrowedCount;
    }

    public void setPatronId(String patronId) {
        this.patronId = patronId;
    }
    
    public void setBookBorrowedCount(int bookBorrowedCount) {
        this.bookBorrowedCount = bookBorrowedCount;
    }
    
    public String getPatronId() {
        return patronId;
    }

    public int getBookBorrowedCount() {
        return bookBorrowedCount;
    }

    public void updateBorrowedCount(int bookCount) {
        this.bookBorrowedCount += bookCount;
    }
    
    public String toString() {
        return super.toString() + "," + patronId + "," + bookBorrowedCount;
    }

    public static Patron fromFileFormat(String data) {
        String[] parts = data.split(",");
        return new Patron(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
    }
}
