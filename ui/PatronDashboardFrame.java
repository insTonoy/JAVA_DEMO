package ui;
import entities.Patron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatronDashboardFrame extends JFrame {
    private JPanel contentPanel;
    private JPanel logoPanel, navigationPanel;
    private JButton logoutButton, profileButton;
    private JButton bookCollectionButton, borrowButton, returnButton;
    private Patron LoggedInUser;

    public PatronDashboardFrame(Patron loggedInUser) {
        this.LoggedInUser = loggedInUser;
        
        setTitle("Patron Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); 

        logoPanel = new JPanel();
        logoPanel.setBounds(0, 0, 1000, 60); 
        logoPanel.setLayout(null);
        logoPanel.setBackground(Color.BLUE); 

        JLabel libraryName = new JLabel("Patron Library");
        libraryName.setFont(new Font("Arial", Font.BOLD, 24));
        libraryName.setForeground(Color.WHITE);
        libraryName.setBounds(20, 10, 300, 40); 
        logoPanel.add(libraryName);

        profileButton = new JButton("Profile");
        profileButton.setBounds(720, 10, 130, 40);
        profileButton.setFont(new Font("Arial", Font.BOLD, 16));
        profileButton.setBackground(Color.CYAN); 
        profileButton.setForeground(Color.BLACK);
        logoPanel.add(profileButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(860, 10, 100, 40); 
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(Color.CYAN); 
        logoutButton.setForeground(Color.BLACK);
       
        logoPanel.add(logoutButton);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ProfileUpdateFrame(loggedInUser);  
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();  
                new LoginFrame();  
            }
        });

        navigationPanel = new JPanel();
        navigationPanel.setBounds(0, 60, 1000, 50); 
        navigationPanel.setLayout(new GridLayout(1, 3, 10, 0)); 
        navigationPanel.setBackground(Color.BLUE); 

        bookCollectionButton = createNavigationButton("Book Collection");
        borrowButton = createNavigationButton("Borrow");
        returnButton = createNavigationButton("Return");
        
        navigationPanel.add(bookCollectionButton);
        navigationPanel.add(borrowButton);
        navigationPanel.add(returnButton);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBounds(0, 110, 1000, 590); 
        contentPanel.setBackground(Color.WHITE); 

        contentPanel.add(new BookCollectionPanel(), "Book Collection"); 
        contentPanel.add(new BorrowPanel(loggedInUser), "Borrow");
        contentPanel.add(new ReturnPanel(loggedInUser), "Return");
        
        bookCollectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Book Collection");
            }
        });
        borrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Borrow");
            }
        });
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Return");
            }
        });

        add(logoPanel);
        add(navigationPanel);
        add(contentPanel);

        switchPanel("Book Collection");
        setVisible(true);
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.BLUE); 
        button.setForeground(Color.WHITE); 
        return button;
    }

    private void switchPanel(String section) {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, section);
        
        resetButtonColors();

        switch (section) {
            case "Book Collection":
                bookCollectionButton.setBackground(Color.BLUE); 
                break;
            case "Borrow":
                borrowButton.setBackground(Color.BLUE); 
                break;
            case "Return":
                returnButton.setBackground(Color.BLUE); 
                break;
        }
    }

    private void resetButtonColors() {
        bookCollectionButton.setBackground(Color.GRAY);
        borrowButton.setBackground(Color.GRAY);
        returnButton.setBackground(Color.GRAY);
    }
}
