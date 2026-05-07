package ui;
import entities.Borrow;
import entities.Patron;
import repository.BorrowFileHandler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReturnPanel extends JPanel {
    private JTable borrowTable;
    private DefaultTableModel tableModel;
    private JButton returnButton;

    public ReturnPanel(Patron loggedInUser) {
        setLayout(new BorderLayout());

        Borrow[] borrowedBooks = BorrowFileHandler.getApprovedRequests(loggedInUser.getUsername());

        String[] columnNames = {"Borrow ID", "Book Title", "Borrow Date", "Due Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        borrowTable = new JTable(tableModel);
        loadBorrowedBooks(borrowedBooks);

        add(new JScrollPane(borrowTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        returnButton = new JButton("Return Book");
        buttonPanel.add(returnButton);
        add(buttonPanel, BorderLayout.SOUTH);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
    }

    private void loadBorrowedBooks(Borrow[] borrowedBooks) {
        tableModel.setRowCount(0);
        for (Borrow borrow : borrowedBooks) {
            if (borrow != null) {
                tableModel.addRow(new Object[]{
                        borrow.getBorrowId(),
                        borrow.getBookTitle(),
                        borrow.getBorrowingDate(),
                        borrow.getDueDate(),
                        borrow.getApprovalStatus()
                });
            }
        }
    }

    private void returnBook() {
        int selectedRow = borrowTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to return.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String borrowId = (String) tableModel.getValueAt(selectedRow, 0);
        BorrowFileHandler.returnBook(borrowId);
        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Book returned successfully.");
    }
}
