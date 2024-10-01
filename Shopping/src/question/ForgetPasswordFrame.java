package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgetPasswordFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    ForgetPasswordFrame() {
        setTitle("Reset Password");
        setSize(400, 300);
        setLayout(new FlowLayout());
        
        add(new JLabel("Enter your email:"));
        emailField = new JTextField(20);
        add(emailField);
        
        add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField(20);
        add(newPasswordField);
        
        add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField(20);
        add(confirmPasswordField);
        
        JButton resetButton = new JButton("Reset Password");
        add(resetButton);
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Database connection and password reset logic
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping", "root", "root");
                    
                    // Check if the email exists in the database
                    String checkEmailQuery = "SELECT * FROM signuppage WHERE email = ?";
                    PreparedStatement checkEmailStmt = connection.prepareStatement(checkEmailQuery);
                    checkEmailStmt.setString(1, email);
                    ResultSet rs = checkEmailStmt.executeQuery();

                    if (rs.next()) {
                        // Update the password in the database
                        String updatePasswordQuery = "UPDATE signuppage SET password = ? WHERE email = ?";
                        PreparedStatement updatePasswordStmt = connection.prepareStatement(updatePasswordQuery);
                        updatePasswordStmt.setString(1, newPassword);
                        updatePasswordStmt.setString(2, email);
                        updatePasswordStmt.executeUpdate();

                        // Inform the user
                        JOptionPane.showMessageDialog(null, "Your password has been reset successfully.", "Reset Password", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Email not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    rs.close();
                    checkEmailStmt.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }

                dispose();
                new LoginFrame(); // Return to login frame
            }
        });

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
    }

    public static void main(String[] args) {
        new ForgetPasswordFrame();
    }
}