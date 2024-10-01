package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ElectronicsFrame extends JFrame {
    private ArrayList<Product> products;

    public ElectronicsFrame() {
        setTitle("Electronics");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        products = new ArrayList<>();
        initializeProducts();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.black);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the header label
        JLabel header = new JLabel("Electronics Products");
        header.setForeground(Color.white); // Set the text color to white

        // Add the header label to the panel
        headerPanel.add(header);


        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        displayProducts(imagePanel, gbc, products);

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void initializeProducts() {
        products.add(new Product("Laptop", "$999.99", "Brand A", "16GB RAM, 512GB SSD", "4.7/5", "e-6image.jpeg"));
        products.add(new Product("Smartphone", "$699.99", "Brand B", "128GB Storage, 5G", "4.5/5", "e8.jpeg"));
        products.add(new Product("Headset", "$399.99", "Brand C", "10.5-inch, 64GB", "4.6/5", "e2-image.jpeg"));
        products.add(new Product("Smartwatch", "$199.99", "Brand D", "Heart Rate Monitor", "4.3/5", "e-4image.jpeg"));
        products.add(new Product("Bluetooth", "$149.99", "Brand E", "Noise Cancelling", "4.8/5", "e-3image.jpeg"));
        products.add(new Product("Charger", "$89.99", "Brand F", "10-hour Battery Life", "4.4/5", "e-5image.jpeg"));
    }

    private void displayProducts(JPanel panel, GridBagConstraints gbc, ArrayList<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            addImagePanel(panel, gbc, i % 2, i / 2, product.getImagePath(), product.getName(), product.getPrice(), product.getBrand(), product.getFeatures(), product.getRating());
        }
    }

    private class ImagePanel extends JPanel {
        private ImageIcon backgroundIcon;

        public ImagePanel() {
            backgroundIcon = new ImageIcon("background.jpeg");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void addImagePanel(JPanel mainImagePanel, GridBagConstraints gbc, int gridX, int gridY, String imagePath, String productName, String price, String brand, String features, String rating) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        imagePanel.setPreferredSize(new Dimension(400, 300));

        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath);

        if (icon.getIconWidth() == -1) {
            System.err.println("Image not found: " + imagePath);
            imageLabel.setText("Image not found");
        } else {
            Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        }
        
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(5, 1));
        detailsPanel.add(new JLabel(productName, JLabel.CENTER));
        detailsPanel.add(new JLabel(price, JLabel.CENTER));
        detailsPanel.add(new JLabel("Brand: " + brand, JLabel.CENTER));
        detailsPanel.add(new JLabel("Features: " + features, JLabel.CENTER));
        detailsPanel.add(new JLabel("Rating: " + rating, JLabel.CENTER));
        imagePanel.add(detailsPanel, BorderLayout.NORTH);

        JButton buyButton = new JButton("Buy");
        buyButton.setPreferredSize(new Dimension(60, 25));
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new OrderSummaryFrame(new Product(productName, price, brand, features, rating, imagePath));
            }
        });
        imagePanel.add(buyButton, BorderLayout.SOUTH);

        gbc.gridx = gridX;
        gbc.gridy = gridY;
        mainImagePanel.add(imagePanel, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ElectronicsFrame();
            }
        });
    }
}
