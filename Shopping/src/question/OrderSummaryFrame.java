package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OrderSummaryFrame extends JFrame {
    private Product product;

    public OrderSummaryFrame(Product product) {
        this.product = product;
        setTitle("Order Summary");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Set background color of the frame to sky blue
        getContentPane().setBackground(new Color(135, 206, 235)); // RGB for sky blue

        // Creating a panel for the product details with GridBagLayout
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryPanel.setBackground(new Color(173, 216, 230)); // Light blue for summary panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        // Adding heading for the summary panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel headingLabel = new JLabel("Order Summary");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        summaryPanel.add(headingLabel, gbc);

        // Adding labels for product details
        gbc.gridy = 1; // Move down to next row
        summaryPanel.add(new JLabel("Product Name: " + product.getName()), gbc);

        gbc.gridy = 2;
        summaryPanel.add(new JLabel("Price: " + product.getPrice()), gbc);

        gbc.gridy = 3;
        summaryPanel.add(new JLabel("Brand: " + product.getBrand()), gbc);

        gbc.gridy = 4;
        summaryPanel.add(new JLabel("Features: " + product.getFeatures()), gbc);

        gbc.gridy = 5;
        summaryPanel.add(new JLabel("Rating: " + product.getRating()), gbc);

        // Adding label for address
        gbc.gridy = 6;
        summaryPanel.add(new JLabel("Address:"), gbc);

        // Adding JTextArea for address
        gbc.gridy = 7; // Move down for address area
        JTextArea addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        summaryPanel.add(new JScrollPane(addressArea), gbc);

        // Panel for the button to center it
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton continueButton = new JButton("Continue to Payment");
        continueButton.setPreferredSize(new Dimension(150, 30));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = addressArea.getText().trim(); // Get the address from the JTextArea

                // Validate address input
                if (address.isEmpty()) {
                    JOptionPane.showMessageDialog(OrderSummaryFrame.this, "Address must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit if validation fails
                }

                // Database connection details
                String url = "jdbc:mysql://localhost:3306/Shopping"; 
                String user = "root"; 
                String password = "root"; 

                // Inserting address into the database
                try {
                    Connection conn = DriverManager.getConnection(url, user, password);
                    String query = "INSERT INTO OrderSum1(ProductName, Price, Brand, Features, Rating, Address) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, product.getName());
                    stmt.setString(2, product.getPrice());
                    stmt.setString(3, product.getBrand());
                    stmt.setString(4, product.getFeatures());
                    stmt.setString(5, product.getRating());
                    stmt.setString(6, address);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(OrderSummaryFrame.this, "Order summary saved successfully!");
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(OrderSummaryFrame.this, "Error saving order summary: " + ex.getMessage());
                }

                // Proceed to payment section
                dispose(); // Close the order summary frame
                new PaymentSection(product.getPrice().replace("$", "").trim()); // Navigate to payment section
            }
        });

        buttonPanel.add(continueButton); // Add button to buttonPanel

        // Adding components to the frame
        add(Box.createRigidArea(new Dimension(0, 10))); 
        add(summaryPanel);
        add(Box.createRigidArea(new Dimension(0, 10))); 
        add(buttonPanel);

        setVisible(true);
    }
}
