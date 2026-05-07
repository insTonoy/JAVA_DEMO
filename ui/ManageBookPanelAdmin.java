package ui;
import entities.Book;
import repository.*;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageBookPanelAdmin extends JPanel {
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ManageBookPanelAdmin() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by BN:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        String[] columnNames = { "Book Title", "BN", "Author" };
        tableModel = new DefaultTableModel(columnNames, 0);
        booksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(booksTable);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadBookData();

        searchButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadBookData();
            }
        });

        
    }

    private void loadBookData() {
        Book[] books = BookListFileHandler.getAllBooks();
        tableModel.setRowCount(0);
        for (Book book : books) {
            if (book != null) {
                tableModel.addRow(new Object[] {
                        book.getBookTitle(),
                        book.getBn(),
                        book.getAuthor(),

                });
            }
        }
    }

    private void searchBook() {
        String searchbn = searchField.getText().trim();
        if (searchbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an BN to search.");
            return;
        }

        tableModel.setRowCount(0);
        Book book = BookListFileHandler.findBookByBn(searchbn);
        if (book != null) {
            tableModel.addRow(new Object[] {
                    book.getBookTitle(),
                    book.getBn(),
                    book.getAuthor(),
            });
        } else {
            JOptionPane.showMessageDialog(this, "No book found with BN: " + searchbn);
        }
    }
}

