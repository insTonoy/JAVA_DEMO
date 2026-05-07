package ui;
import entities.Book;
import repository.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageBookPanel extends JPanel {
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ManageBookPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by BN:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Book");
        JButton addBooksButton = new JButton("Add Books");

        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(deleteButton);
        searchPanel.add(addBooksButton);


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
        addBooksButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                addBookFrame();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadBookData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                deleteBook();
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

    private void deleteBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            return;
        }

        String bnToDelete = tableModel.getValueAt(selectedRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the selected book?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            BookListFileHandler.deleteBookByBn(bnToDelete);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Book deleted successfully.");
        }
    }

    private void addBookFrame() {
        JFrame frame = new JFrame("Add New Book");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Book Title:");
        titleLabel.setBounds(50, 30, 100, 25);
        frame.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(160, 30, 150, 25);
        frame.add(titleField);

        JLabel bnLabel = new JLabel("BN:");
        bnLabel.setBounds(50, 70, 100, 25);
        frame.add(bnLabel);

        JTextField bnField = new JTextField();
        bnField.setBounds(160, 70, 150, 25);
        frame.add(bnField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 110, 100, 25);
        frame.add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(160, 110, 150, 25);
        frame.add(authorField);





        JButton addButton = new JButton("Add Book");
        addButton.setBounds(100, 240, 100, 30);
        frame.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(210, 240, 100, 30);
        frame.add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String bn = bnField.getText().trim();
                String author = authorField.getText().trim();

                if (title.isEmpty() || bn.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book book = new Book(title, bn, author);
                BookListFileHandler.addBook(book);

                JOptionPane.showMessageDialog(frame, "Book added successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
