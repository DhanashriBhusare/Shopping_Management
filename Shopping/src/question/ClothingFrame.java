package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClothingFrame extends JFrame {
    private ArrayList<Product> products;

    public ClothingFrame() {
        setTitle("Clothing");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        products = new ArrayList<>();
        initializeProducts();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.black);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(new JLabel("Clothing Products"));

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
        products.add(new Product("Men's Shirt", "$29.99", "Brand A", "Cotton, Casual", "4.5/5", "image1.jpeg"));
        products.add(new Product("Women's Dress", "$49.99", "Brand B", "Silk, Elegant", "4.8/5", "image2.jpeg"));
        // Add more products as needed
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
            backgroundIcon = new ImageIcon("image7.jpeg");
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

        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath);
        imageLabel.setIcon(icon);
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
                JOptionPane.showMessageDialog(null, "Item bought successfully!");
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
                new ClothingFrame();
            }
        });
    }
}