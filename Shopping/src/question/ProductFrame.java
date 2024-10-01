package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ProductFrame extends JFrame {
    public ProductFrame() {
        setTitle("Product List");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        String[] products = {"Product 1", "Product 2", "Product 3"};
        JComboBox<String> productList = new JComboBox<>(products);
        JButton orderButton = new JButton("Order Product");
        
        // Action for ordering product
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productList.getSelectedItem();
                new OrderFrame(selectedProduct);
                dispose(); // Close product frame
            }
        });

        add(productList);
        add(orderButton);
        setVisible(true);
    }
}