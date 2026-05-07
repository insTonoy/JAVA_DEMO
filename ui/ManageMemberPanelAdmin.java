package ui;
import entities.Patron;
import repository.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageMemberPanelAdmin extends JPanel {
    private JTable memberTable;
    private DefaultTableModel tableModel;

    public ManageMemberPanelAdmin() {
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Account");

        buttonPanel.add(showAllButton);
        buttonPanel.add(deleteButton);

        String[] columnNames = { "Username", "Name", "Email", "Patron ID", "Currently Borrowed" };
        tableModel = new DefaultTableModel(columnNames, 0);
        memberTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadPatronData();

        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadPatronData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });
    }

    private void loadPatronData() {
        Patron[] patrons = PatronFileHandler.getAllPatrons();
        tableModel.setRowCount(0); 
        for (Patron patron : patrons) {
            if (patron != null) {
                tableModel.addRow(new Object[] {
                    patron.getUsername(),
                    patron.getName(),
                    patron.getEmail(),
                    patron.getPatronId(),
                    patron.getBookBorrowedCount() 
                });
            }
        }
    }

    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            return;
        }

        String userName = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the selected member?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            PatronFileHandler.deletePatron(userName);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Patron deleted successfully.");
        }
    }
}
