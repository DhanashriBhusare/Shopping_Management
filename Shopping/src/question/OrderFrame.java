package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OrderFrame extends JFrame {
    public OrderFrame(String product) {
        setTitle("Order Product");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Ordering: " + product);
        JButton confirmButton = new JButton("Confirm Order");
        
        // Action for confirming the order
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Here you would typically save the order
                JOptionPane.showMessageDialog(null, "Order for " + product + " confirmed!");
                new MainFrame(); // Return to main frame
                dispose(); // Close order frame
            }
        });

        add(label);
        add(confirmButton);
        setVisible(true);
    }
}
