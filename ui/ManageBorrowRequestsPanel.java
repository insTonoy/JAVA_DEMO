package ui;

import entities.Borrow;
import repository.BorrowFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageBorrowRequestsPanel extends JPanel {
    private JTable borrowTable;
    private DefaultTableModel tableModel;
    private JButton acceptButton, declineButton;

    public ManageBorrowRequestsPanel() {
        setLayout(new BorderLayout());

        Borrow[] pendingRequests = BorrowFileHandler.getAllPendingRequests();

        String[] columnNames = {"Borrow ID", "Username", "Book Title", "Borrow Date", "Due Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        borrowTable = new JTable(tableModel);
        loadBorrowRequests(pendingRequests);

        add(new JScrollPane(borrowTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        acceptButton = new JButton("Accept");
        declineButton = new JButton("Decline");
        buttonPanel.add(acceptButton);
        buttonPanel.add(declineButton);
        add(buttonPanel, BorderLayout.SOUTH);

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus("accepted");
            }
        });

        declineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus("declined");
            }
        });
    }

    private void loadBorrowRequests(Borrow[] requests) {
        tableModel.setRowCount(0);
        for (Borrow borrow : requests) {
            if (borrow != null) {
                tableModel.addRow(new Object[]{
                        borrow.getBorrowId(),
                        borrow.getUserName(),
                        borrow.getBookTitle(),
                        borrow.getBorrowingDate(),
                        borrow.getDueDate(),
                        borrow.getApprovalStatus()
                });
            }
        }
    }

    private void updateRequestStatus(String newStatus) {
        int selectedRow = borrowTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to proceed.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String borrowId = (String) tableModel.getValueAt(selectedRow, 0);
        BorrowFileHandler.updateApprovalStatus(borrowId, newStatus);
        tableModel.setValueAt(newStatus, selectedRow, 5);
        JOptionPane.showMessageDialog(this, "Request " + newStatus + " successfully.");
    }
} 