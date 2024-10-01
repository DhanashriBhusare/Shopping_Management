package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PaymentSection extends JFrame {
    private JTextField txtCardNumber, txtCVV, txtUPIId, txtPhoneNumber, txtBankName, txtAccountNumber, txtDeliveryAddress;
    private String amount;

    public PaymentSection(String amount) {
        this.amount = amount;
        // Set frame properties
        setTitle("Shopping Management System - Payment");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel lblTitle = new JLabel("Make Payment", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Payment Method Selection
        JLabel lblPaymentMethod = new JLabel("Select Payment Method:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblPaymentMethod, gbc);

        ButtonGroup paymentGroup = new ButtonGroup();
        JRadioButton rbCard = new JRadioButton("Credit/Debit Card");
        JRadioButton rbUPI = new JRadioButton("UPI");
        JRadioButton rbNetBanking = new JRadioButton("Net Banking");
        JRadioButton rbCashOnDelivery = new JRadioButton("Cash on Delivery");

        paymentGroup.add(rbCard);
        paymentGroup.add(rbUPI);
        paymentGroup.add(rbNetBanking);
        paymentGroup.add(rbCashOnDelivery);

        // Panel for Radio Buttons
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.setBackground(Color.LIGHT_GRAY);
        radioPanel.add(rbCard);
        radioPanel.add(rbUPI);
        radioPanel.add(rbNetBanking);
        radioPanel.add(rbCashOnDelivery);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(radioPanel, gbc);

        // Create and hide all payment detail panels initially
        JPanel cardDetailsPanel = createCardDetailsPanel();
        JPanel upiPanel = createUPIPanel();
        JPanel netBankingPanel = createNetBankingPanel();
        JPanel codPanel = createCODPanel();

        gbc.gridy = 2;
        mainPanel.add(cardDetailsPanel, gbc);
        gbc.gridy = 3;
        mainPanel.add(upiPanel, gbc);
        gbc.gridy = 4;
        mainPanel.add(netBankingPanel, gbc);
        gbc.gridy = 5;
        mainPanel.add(codPanel, gbc);

        // Initially hide all payment detail panels
        cardDetailsPanel.setVisible(false);
        upiPanel.setVisible(false);
        netBankingPanel.setVisible(false);
        codPanel.setVisible(false);

        // Payment Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnMakePayment = new JButton("Make Payment");
        JButton btnCancel = new JButton("Cancel Payment");
        buttonPanel.add(btnMakePayment);
        buttonPanel.add(btnCancel);
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // ActionListener for payment method selection
        rbCard.addActionListener(e -> togglePaymentPanels(cardDetailsPanel, upiPanel, netBankingPanel, codPanel));
        rbUPI.addActionListener(e -> togglePaymentPanels(upiPanel, cardDetailsPanel, netBankingPanel, codPanel));
        rbNetBanking.addActionListener(e -> togglePaymentPanels(netBankingPanel, cardDetailsPanel, upiPanel, codPanel));
        rbCashOnDelivery.addActionListener(e -> togglePaymentPanels(codPanel, cardDetailsPanel, upiPanel, netBankingPanel));

        // ActionListener for Make Payment button
        btnMakePayment.addActionListener(e -> handlePayment(rbCard, rbUPI, rbNetBanking, rbCashOnDelivery, amount, cardDetailsPanel, upiPanel, netBankingPanel, codPanel));

        btnCancel.addActionListener(e -> clearFields(cardDetailsPanel, upiPanel, netBankingPanel, codPanel));

        // Set the frame visibility
        setVisible(true);
    }

    private void togglePaymentPanels(JPanel selectedPanel, JPanel... otherPanels) {
        selectedPanel.setVisible(true);
        for (JPanel panel : otherPanels) {
            panel.setVisible(false);
        }
        pack(); // Adjust frame size based on the visibility of components
    }

    private JPanel createCardDetailsPanel() {
        JPanel cardDetailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        cardDetailsPanel.setPreferredSize(new Dimension(400, 300));

        cardDetailsPanel.add(new JLabel("Credit Card Number:"));
        txtCardNumber = new JTextField(15);
        cardDetailsPanel.add(txtCardNumber);

        cardDetailsPanel.add(new JLabel("CVV:"));
        txtCVV = new JTextField(5);
        cardDetailsPanel.add(txtCVV);

        cardDetailsPanel.add(new JLabel("Amount Paid:"));
        JTextField txtAmount = new JTextField(10);
        txtAmount.setText(amount);
        txtAmount.setEditable(false); // Prevent editing
        cardDetailsPanel.add(txtAmount);

        return cardDetailsPanel;
    }

    private JPanel createUPIPanel() {
        JPanel upiPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        upiPanel.setPreferredSize(new Dimension(400, 300));
        upiPanel.setBackground(Color.LIGHT_GRAY);

        upiPanel.add(new JLabel("UPI ID:"));
        txtUPIId = new JTextField(15);
        upiPanel.add(txtUPIId);

        upiPanel.add(new JLabel("Phone Number:"));
        txtPhoneNumber = new JTextField(15);
        upiPanel.add(txtPhoneNumber);

        upiPanel.add(new JLabel("Amount:"));
        JTextField txtUPIAmount = new JTextField(10);
        txtUPIAmount.setText(amount);
        txtUPIAmount.setEditable(false);
        upiPanel.add(txtUPIAmount);

        return upiPanel;
    }

    private JPanel createNetBankingPanel() {
        JPanel netBankingPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        netBankingPanel.setPreferredSize(new Dimension(400, 300));
        netBankingPanel.setBackground(Color.LIGHT_GRAY);

        netBankingPanel.add(new JLabel("Bank Name:"));
        txtBankName = new JTextField(15);
        netBankingPanel.add(txtBankName);

        netBankingPanel.add(new JLabel("Account Number:"));
        txtAccountNumber = new JTextField(15);
        netBankingPanel.add(txtAccountNumber);

        netBankingPanel.add(new JLabel("Amount:"));
        JTextField txtNetBankingAmount = new JTextField(10);
        txtNetBankingAmount.setText(amount);
        txtNetBankingAmount.setEditable(false);
        netBankingPanel.add(txtNetBankingAmount);

        return netBankingPanel;
    }

    private JPanel createCODPanel() {
        JPanel codPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        codPanel.setPreferredSize(new Dimension(400, 300));
        codPanel.setBackground(Color.LIGHT_GRAY);

        codPanel.add(new JLabel("Delivery Address:"));
        txtDeliveryAddress = new JTextField(15);
        codPanel.add(txtDeliveryAddress);

        codPanel.add(new JLabel("Amount:"));
        JTextField txtCODAmount = new JTextField(10);
        txtCODAmount.setText(amount);
        txtCODAmount.setEditable(false);
        codPanel.add(txtCODAmount);

        return codPanel;
    }

    private void handlePayment(JRadioButton rbCard, JRadioButton rbUPI, JRadioButton rbNetBanking, JRadioButton rbCashOnDelivery,
                               String amount, JPanel cardDetailsPanel, JPanel upiPanel, JPanel netBankingPanel, JPanel codPanel) {
        String selectedPaymentMethod;

        if (rbCard.isSelected()) {
            String cardNumber = txtCardNumber.getText();
            String cvv = txtCVV.getText();

            // Validate card number and CVV
            if (!isValidCardNumber(cardNumber) || !isValidCVV(cvv)) {
                JOptionPane.showMessageDialog(null, "Invalid card number or CVV.");
                return;
            }

            selectedPaymentMethod = "Credit/Debit Card";
            insertPaymentDetails(selectedPaymentMethod, cardNumber, null, cvv, amount, null, null, "Completed");
            JOptionPane.showMessageDialog(null, "Payment Successful!\nAmount: " + amount);
            dispose();
        } else if (rbUPI.isSelected()) {
            String upiId = txtUPIId.getText();
            String phoneNumber = txtPhoneNumber.getText();

            // Validate UPI ID and phone number
            if (!isValidUPIId(upiId) || !isValidPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Invalid UPI ID or phone number.");
                return;
            }

            selectedPaymentMethod = "UPI";
            insertPaymentDetails(selectedPaymentMethod, null, null, null, amount, upiId, null, "Completed");
            JOptionPane.showMessageDialog(null, "UPI Payment Successful!\nAmount: " + amount);
            dispose();
        } else if (rbNetBanking.isSelected()) {
            String bankName = txtBankName.getText();
            String accountNumber = txtAccountNumber.getText();

            // Validate bank name and account number
            if (bankName.isEmpty() || !isValidAccountNumber(accountNumber)) {
                JOptionPane.showMessageDialog(null, "Invalid bank name or account number.");
                return;
            }

            selectedPaymentMethod = "Net Banking";
            insertPaymentDetails(selectedPaymentMethod, null, null, null, amount, null, bankName, "Completed");
            JOptionPane.showMessageDialog(null, "Net Banking Payment Successful!\nAmount: " + amount);
            dispose();
        } else if (rbCashOnDelivery.isSelected()) {
            String deliveryAddress = txtDeliveryAddress.getText();

            // Validate delivery address
            if (deliveryAddress.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Delivery address cannot be empty.");
                return;
            }

            selectedPaymentMethod = "Cash on Delivery";
            insertPaymentDetails(selectedPaymentMethod, null, null, null, amount, null, null, "Pending");
            JOptionPane.showMessageDialog(null, "Order confirmed for Cash on Delivery.\nAmount: " + amount);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a payment method.");
        }
    }

    private void clearFields(JPanel cardDetailsPanel, JPanel upiPanel, JPanel netBankingPanel, JPanel codPanel) {
        for (Component comp : cardDetailsPanel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
        for (Component comp : upiPanel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
        for (Component comp : netBankingPanel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
        for (Component comp : codPanel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
        // Clear selection
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.clearSelection();
        cardDetailsPanel.setVisible(false);
        upiPanel.setVisible(false);
        netBankingPanel.setVisible(false);
        codPanel.setVisible(false);
    }

    private void insertPaymentDetails(String paymentMethod, String cardNumber, String cardType, String cvv, String amount, String upiId, String bankName, String orderStatus) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/Shopping"; 
        String user = "root"; 
        String password = "root"; 

        String query = "INSERT INTO PaymentDetails (paymentMethod, cardNumber, cardType, cvv, amount, upiId, bankName, orderStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, paymentMethod);
            pstmt.setString(2, cardNumber);
            pstmt.setString(3, cardType);
            pstmt.setString(4, cvv);
            pstmt.setString(5, amount);
            pstmt.setString(6, upiId);
            pstmt.setString(7, bankName);
            pstmt.setString(8, orderStatus);

            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving payment details: " + ex.getMessage());
        }
    }

    // Validation methods
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}"); // Only numeric and 16 digits
    }

    private boolean isValidCVV(String cvv) {
        return cvv.matches("\\d{3,4}"); // Only numeric and 3 or 4 digits
    }

    private boolean isValidUPIId(String upiId) {
        return upiId.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+$"); // Basic UPI ID validation
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}"); // Only numeric and 10 digits
    }

    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber.matches("\\d{9,18}"); // Numeric and 9 to 18 digits
    }

    public static void main(String[] args) {
        new PaymentSection("1000");
    }
}