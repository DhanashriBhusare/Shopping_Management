package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Pattern;

public class RegistrationForm extends JFrame implements ActionListener {
    private JLabel labelFirstName, labelLastName, labelUsername, labelPassword, labelEmail, labelMobile, labelAddress;
    private JTextField firstNameField, lastNameField, usernameField, emailField, mobileField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JTextArea addressField;
    private Image backgroundImage;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Load background image
        backgroundImage = new ImageIcon("reg.jpg").getImage();
        
        // Create a panel to hold components
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Reduced padding

        // Title Label for Registration Page
        JLabel titleLabel = new JLabel("Registration Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Decreased font size
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Adding first name label and text field
        labelFirstName = new JLabel("First Name:");
        labelFirstName.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelFirstName, gbc);
        
        firstNameField = new JTextField(15); // Decreased width
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 1;
        gbc.ipady = 5; // Decreased height
        panel.add(firstNameField, gbc);

        // Adding last name label and text field
        labelLastName = new JLabel("Last Name:");
        labelLastName.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelLastName, gbc);
        
        lastNameField = new JTextField(15); // Decreased width
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);

        // Adding username label and text field
        labelUsername = new JLabel("Username:");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelUsername, gbc);
        
        usernameField = new JTextField(15); // Decreased width
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Adding password label and text field
        labelPassword = new JLabel("Password:");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelPassword, gbc);
        
        passwordField = new JPasswordField(15); // Decreased width
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Adding email label and text field
        labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(labelEmail, gbc);
        
        emailField = new JTextField(15); // Decreased width
        emailField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Adding mobile label and text field
        labelMobile = new JLabel("Mobile:");
        labelMobile.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(labelMobile, gbc);
        
        mobileField = new JTextField(15); // Decreased width
        mobileField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 1;
        panel.add(mobileField, gbc);

        // Adding address label and text area
        labelAddress = new JLabel("Address:");
        labelAddress.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(labelAddress, gbc);
        
        addressField = new JTextArea(3, 15); // Decreased width
        addressField.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(addressField);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Adding submit button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Decreased font size
        submitButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);
        
        // Add panel to frame
        add(panel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        // Validate all fields
        if (!isValidInput(firstName, lastName, username, password, email, mobile, address)) {
            return;
        }

        // Further processing, e.g., database insertion, goes here
        JOptionPane.showMessageDialog(this, "Registration Successful!");
    }

    private boolean isValidInput(String firstName, String lastName, String username, String password, String email, String mobile, String address) {
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() ||
            email.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }

        // Name validation (only letters)
        if (!firstName.matches("[A-Za-z]+")) {
            JOptionPane.showMessageDialog(this, "First Name must contain only letters.");
            return false;
        }
        if (!lastName.matches("[A-Za-z]+")) {
            JOptionPane.showMessageDialog(this, "Last Name must contain only letters.");
            return false;
        }

        // Username validation (alphanumeric, 5-15 characters)
        if (!username.matches("^[A-Za-z0-9]{5,15}$")) {
            JOptionPane.showMessageDialog(this, "Username must be 5-15 alphanumeric characters.");
            return false;
        }

        // Email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return false;
        }

        // Mobile number validation
        if (!mobile.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Mobile number must be 10 digits.");
            return false;
        }

        // Password strength validation (at least 8 characters, 1 digit, 1 uppercase)
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain at least 1 digit and 1 uppercase letter.");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}