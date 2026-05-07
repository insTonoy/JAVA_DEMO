package ui;
import entities.Patron;
import repository.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ProfileUpdateFrame extends JFrame {

    private Patron loggedInUser;
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField;

    public ProfileUpdateFrame(Patron loggedInUser) {
        this.loggedInUser = loggedInUser;

        setTitle("Update Profile");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(50, 80, 120, 25);  
        usernameField = new JTextField(loggedInUser.getUsername(), 15);
        usernameField.setBounds(180, 80, 200, 25);  
        
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(50, 120, 120, 25);  
        nameField = new JTextField(loggedInUser.getName(), 15);
        nameField.setBounds(180, 120, 200, 25);  
        
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(50, 160, 120, 25); 
        emailField = new JTextField(loggedInUser.getEmail(), 15);
        emailField.setBounds(180, 160, 200, 25);  
        
        JLabel passwordLabel = new JLabel("New Password: ");
        passwordLabel.setBounds(50, 200, 120, 25);  
        passwordField = new JPasswordField(15);
        passwordField.setBounds(180, 200, 200, 25);  
        
        JButton updateButton = new JButton("Update Profile");
        updateButton.setBounds(180, 260, 200, 35);  
        
        
        add(usernameLabel);
        add(usernameField);
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText().trim();
                String newName = nameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newPassword = new String(passwordField.getPassword()).trim();
          
                String loggedInUser_Username = loggedInUser.getUsername();
                loggedInUser.setUsername(newUsername);
                loggedInUser.setName(newName);
                loggedInUser.setEmail(newEmail);
                if (!newPassword.isEmpty()) {
                    loggedInUser.setPassword(newPassword);
                }
            
                BorrowFileHandler.updateUsername(loggedInUser_Username, newUsername);
                PatronFileHandler.updateProfile(loggedInUser_Username, loggedInUser);

                JOptionPane.showMessageDialog(ProfileUpdateFrame.this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
                ProfileUpdateFrame.this.dispose();

                if (!newPassword.isEmpty()) {
                    new LoginFrame();
                }
            }
        });

        setVisible(true);
    }


}
