import repository.BorrowFileHandler;
import ui.LoginFrame;

public class Start {
    public static void main(String[] args) {
        BorrowFileHandler.updateFines();
        new LoginFrame();
    }
}
