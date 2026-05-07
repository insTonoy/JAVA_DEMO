package ui;
import entities.*;
import repository.BookListFileHandler;
import repository.BorrowFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowPanel extends JPanel {
    private JTextField bnField;
    private JButton borrowButton;
    private JLabel messageLabel;

    private Patron loggedInUser;

    public BorrowPanel(Patron loggedInUser) {

        setLayout(null);
        this.loggedInUser = loggedInUser; 

        JLabel bnLabel = new JLabel("Enter BN:");
        bnLabel.setBounds(350, 130, 200, 30);
        bnLabel.setFont(new Font("Arial", Font.BOLD, 18));

        bnField = new JTextField(20);
        bnField.setBounds(480, 130, 200, 30);
        bnField.setFont(new Font("Arial", Font.BOLD, 18));

        borrowButton = new JButton("Request Borrow");
        borrowButton.setBounds(420, 250, 210, 40);
        borrowButton.setFont(new Font("Arial", Font.BOLD, 18));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(350, 300, 400, 30);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(bnLabel);
        add(bnField);
        add(borrowButton);
        add(messageLabel);

        borrowButton.addActionListener(new BorrowButtonListener());
    }

    private class BorrowButtonListener implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            String bn = bnField.getText().trim();

            if (bn.isEmpty()) {
                messageLabel.setText("Please enter an BN.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            
            Book book = BookListFileHandler.findBookByBn(bn);

            if (book == null) {
                messageLabel.setText("Book not found. Check BN.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            if (loggedInUser.getBookBorrowedCount()>=5) {
                messageLabel.setText("You can't borrow more than 5 books.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            
            String bookTitle = book.getBookTitle();
            LocalDate borrowingTime = LocalDate.now();
            LocalDate dueDate = borrowingTime.plusWeeks(2); 
           
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            
            String borrowingTimeStr = borrowingTime.format(formatter);
            String dueDateStr = dueDate.format(formatter);

            String approvalStatus = "pending";

            Borrow borrow = new Borrow(loggedInUser.getUsername(), bookTitle, borrowingTimeStr, dueDateStr, approvalStatus, 0.0);

           
            BorrowFileHandler.addBorrowRequest(borrow);

            messageLabel.setText("Borrow request submitted!");
            messageLabel.setForeground(Color.GREEN);

            bnField.setText("");
        }
    }
}

