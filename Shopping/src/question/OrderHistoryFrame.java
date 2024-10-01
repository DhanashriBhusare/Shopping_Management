package question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderHistoryFrame extends JFrame {
    private DefaultTableModel tableModel;
    private JTable orderTable;

    public OrderHistoryFrame() {
        setTitle("Order History");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout
        setLayout(new BorderLayout());

        // Heading
        JLabel headingLabel = new JLabel("Your Orders", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headingLabel, BorderLayout.NORTH);

        // Create table model and table
        tableModel = new DefaultTableModel();
        orderTable = new JTable(tableModel);
        
        // Add columns to the table model
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Features");
        tableModel.addColumn("Rating");
        tableModel.addColumn("Address");

        loadOrderHistory();

        // Create scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
        
        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Cancel Order");
        cancelButton.addActionListener(new CancelOrderAction());
        buttonPanel.add(cancelButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private void loadOrderHistory() {
        String url = "jdbc:mysql://localhost:3306/Shopping";
        String user = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT ProductName, Price, Brand, Features, Rating, Address FROM OrderSum1";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String productName = rs.getString("ProductName");
                String price = rs.getString("Price");
                String brand = rs.getString("Brand");
                String features = rs.getString("Features");
                String rating = rs.getString("Rating");
                String address = rs.getString("Address");

                tableModel.addRow(new Object[]{productName, price, brand, features, rating, address});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading order history: " + ex.getMessage());
        }
    }

    private class CancelOrderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(OrderHistoryFrame.this, "Please select an order to cancel.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String productName = (String) tableModel.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(OrderHistoryFrame.this,
                    "Are you sure you want to cancel the order for " + productName + "?",
                    "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                deleteOrderFromDatabase(productName);
                tableModel.removeRow(selectedRow); // Remove the row from the table
            }
        }

        private void deleteOrderFromDatabase(String productName) {
            String url = "jdbc:mysql://localhost:3306/Shopping";
            String user = "root";
            String password = "root";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {

                // Delete from OrderSum1
                String deleteOrderQuery = "DELETE FROM OrderSum1 WHERE ProductName = '" + productName + "'";
                stmt.executeUpdate(deleteOrderQuery);
                
                // Also delete from PaymentDetails (assumes you have a matching reference, like OrderID)
                String deletePaymentQuery = "DELETE FROM PaymentDetails WHERE orderStatus = 'Completed' AND amount IN (SELECT Price FROM OrderSum1 WHERE ProductName = '" + productName + "')";
                stmt.executeUpdate(deletePaymentQuery);

                JOptionPane.showMessageDialog(OrderHistoryFrame.this, "Order cancelled successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(OrderHistoryFrame.this, "Error cancelling order: " + ex.getMessage());
            }
        }
    }
}