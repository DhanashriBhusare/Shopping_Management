package question;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {
    private JTextField textField;
    private JPasswordField passwordField;

    LoginFrame() {
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("WELCOME TO SHOPPING MANAGEMENT SYSTEM");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        lblNewLabel.setBounds(338, 47, 903, 57);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("LOGIN PAGE");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 28));
        lblNewLabel_1.setBounds(458, 209, 200, 43);
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Email Id");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblNewLabel_2.setBounds(300, 324, 158, 32);
        getContentPane().add(lblNewLabel_2);
        
        textField = new JTextField();
        textField.setBounds(445, 324, 219, 32);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("Sign In");
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnNewButton.setBounds(492, 486, 120, 43);
        getContentPane().add(btnNewButton);
        
        JLabel lblNewLabel_2_1 = new JLabel("Password");
        lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblNewLabel_2_1.setBounds(300, 404, 158, 32);
        getContentPane().add(lblNewLabel_2_1);
        
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnSignUp.setBounds(428, 564, 120, 32);
        getContentPane().add(btnSignUp);
        
        JButton btnNewButton_1 = new JButton("Forget");
        btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnNewButton_1.setBounds(587, 564, 120, 32);
        getContentPane().add(btnNewButton_1);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(445, 404, 219, 32);
        getContentPane().add(passwordField);
        
        final JCheckBox chckbxNewCheckBox = new JCheckBox("Show");
        chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        chckbxNewCheckBox.setBounds(670, 407, 59, 32);
        chckbxNewCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.setEchoChar(chckbxNewCheckBox.isSelected() ? (char) 0 : '•');
            }
        });
        getContentPane().add(chckbxNewCheckBox);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/img6.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1650, 1080, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(0, -28, 1540, 1108);
        getContentPane().add(l1);
        
        setBounds(0, 0, 1600, 850);
        setVisible(true);
        
        // Action listener for Sign In button
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Both email and password must be provided.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Connect to the database and verify credentials
                Connection connection = null;
                try {
                    String url = "jdbc:mysql://localhost:3306/Shopping";
                    String user = "root";
                    String pass = "root";

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(url, user, pass);

                    String sql = "SELECT * FROM signuppage WHERE email = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "User authenticated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Navigate to MainFrame
                        dispose(); // Close the login frame
                        new MainFrame(); // Open the main frame
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email or password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Close the connection in the finally block to avoid memory leaks
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Action listener for Sign Up button
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the login frame
                new RegistrationForm(); // Open the sign-up frame
            }
        });

        // Action listener for Forget Password button
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ForgetPasswordFrame(); // Open the forget password frame without closing the login frame
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}