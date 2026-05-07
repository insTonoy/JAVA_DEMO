package ui;
import entities.Borrow;
import repository.*;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OverduePanel extends JPanel {
    
    private JTable borrowTable;
    private DefaultTableModel tableModel;

    public OverduePanel(){
        setLayout(new BorderLayout());

        String[] columnNames = { "BorrowID", "Username","Book Title", "Due Date", "Fines" };
        tableModel = new DefaultTableModel(columnNames, 0);
        borrowTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(borrowTable);

        add(scrollPane, BorderLayout.CENTER);

        loadBorrowBookData();
    }

    private void loadBorrowBookData() {
        Borrow[] borrows = BorrowFileHandler.getOverdueBorrowsWithFines();
       
        tableModel.setRowCount(0);
        for (Borrow borrow : borrows) {
            if (borrow != null) {
                tableModel.addRow(new Object[] {
                        borrow.getBorrowId(),
                        borrow.getUserName(),
                        borrow.getBookTitle(),
                        borrow.getDueDate(),
                        borrow.getFines()
                });
            }
        }
    }
}

