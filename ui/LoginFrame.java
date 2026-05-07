package ui;
import entities.*;
import repository.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel backgroundLabel = new JLabel(new ImageIcon("resources/background.jpeg"));  
        backgroundLabel.setBounds(0, -10, 1000, 700);  
        add(backgroundLabel);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);  
        loginPanel.setOpaque(false);  
        loginPanel.setBackground(Color.BLUE); 
        loginPanel.setBounds(550, 150, 370, 350);  

        JLabel titleLabel = new JLabel("Welcome to Central Library");
        titleLabel.setBounds(30, 30, 350, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(60, 100, 100, 30);  
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 15));

        usernameField = new JTextField(15);
        usernameField.setBounds(140, 100, 160, 30);
        usernameField.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 150, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));

        passwordField = new JPasswordField(15);
        passwordField.setBounds(140, 150, 160, 30);
        passwordField.setFont(new Font("Arial", Font.BOLD, 13));
        

        JButton toggleButton = new JButton();
        passwordField.setEchoChar('•');

        ImageIcon show = new ImageIcon("resources/show.png");
        ImageIcon hide = new ImageIcon("resources/hide.png");
        toggleButton.setIcon(scaleIcon(show, 20, 20));
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleButton.setBounds(300, 155, 20, 20);

        toggleButton.addActionListener(new ActionListener() {
            private boolean isPasswordVisible = false;
            public void actionPerformed(ActionEvent e) {
                if (isPasswordVisible) {
                    passwordField.setEchoChar('•');
                    toggleButton.setIcon(scaleIcon(show, 20, 20));
                } else {
                    passwordField.setEchoChar((char) 0);
                    toggleButton.setIcon(scaleIcon(hide, 20, 20));
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 200, 100, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setOpaque(true);
        
        JLabel extraLabel = new JLabel("Are you new here? Then click");
        extraLabel.setBounds(90, 260, 350, 30);
        extraLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(140, 290, 150, 30);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setOpaque(true);
    
        loginPanel.add(titleLabel);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(toggleButton);
        loginPanel.add(loginButton);
        loginPanel.add(extraLabel);
        loginPanel.add(registerButton);

        backgroundLabel.add(loginPanel);  

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();  
                dispose();  
            }
        });
        setVisible(true);
    }


    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patron patron = PatronFileHandler.findPatronByUsername(username);
        if (patron != null) {
            if (patron.getPassword().equals(password)) {
                new PatronDashboardFrame(patron);
                dispose();
                return;
            }
        }

        Admin admin = AdminFileHandler.findAdminByUsername(username);
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                new AdminDashboardFrame();
                dispose();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }


    private static ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

}
