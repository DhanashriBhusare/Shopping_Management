package question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private ArrayList<Product> products;

    public MainFrame() {
        setTitle("Simple Shopping App");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        products = new ArrayList<>();
        initializeProducts();

        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        JPanel leftPanel = createSidePanelWithImages("", "", new String[]{"adv1.jpeg", "adv3.jpeg", "adv2.jpeg", "adv4.jpeg"});
        JPanel headerPanel = createHeaderPanel();

        ImagePanel imagePanel = new ImagePanel();
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(headerPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        displayProducts(imagePanel, products);

        setVisible(true);
    }

    private void initializeProducts() {
        products.add(new Product("Bag", "$99.99", "India Mart", "carry items", "4.5/5", "image1.jpeg"));
        products.add(new Product("Camera", "$149.99", "Panasonic", "Pixel Quality", "4.0/5", "image2.jpeg"));
        products.add(new Product("Teddy", "$199.99", "OSJS", "Cotton Teddy", "5.0/5", "image3.jpeg"));
        products.add(new Product("Shoes", "$299.99", "Nike", "Good, Support", "3.5/5", "image4.jpeg"));
        products.add(new Product("Phone", "$89.99", "Apple", "Camera Quality", "4.0/5", "image5.jpeg"));
        products.add(new Product("Bat", "$159.99", "Sparatan", "Blade, Handle", "4.7/5", "mainpage.jpeg"));
        products.add(new Product("Wallet", "$249.99", "GUCCI", "Security", "4.3/5", "mainpage5.jpeg"));
        products.add(new Product("Water Bottle", "$349.99", "Milton", "Size, Colour", "4.6/5", "mainpage2.jpeg"));
        products.add(new Product("Toy", "$499.99", "Lego", "", "5.0/5", "mainpage3.jpeg"));
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Welcome to the Home Page!");
            }
        });
        menuBar.add(homeItem);
        menuBar.add(Box.createHorizontalStrut(20));

        JMenu categoriesMenu = new JMenu("Categories");
        categoriesMenu.add(createMenuItem("Electronics"));

        JMenu clothingSubMenu = new JMenu("Clothing");
        clothingSubMenu.add(createMenuItem("Men"));
        clothingSubMenu.add(createMenuItem("Women"));
        clothingSubMenu.add(createMenuItem("Kids"));
        categoriesMenu.add(clothingSubMenu);
        categoriesMenu.add(createMenuItem("Home Appliances"));
        menuBar.add(categoriesMenu);
        menuBar.add(Box.createHorizontalStrut(20));

        JMenu orderMenu = new JMenu("Orders");
        orderMenu.add(createMenuItem("View Orders"));
        menuBar.add(orderMenu);
        menuBar.add(Box.createHorizontalStrut(20));

        JMenu accountMenu = new JMenu("Account");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have logged out.");
                new LoginFrame(); // Navigate to login frame
                dispose(); // Close the main frame
            }
        });
        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);

        return menuBar;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.black);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel header = new JLabel("Latest Products");
        header.setForeground(Color.white);
        headerPanel.add(header);

        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts(searchField.getText());
            }
        });
        headerPanel.add(searchField);
        headerPanel.add(searchButton);

        return headerPanel;
    }

    private void searchProducts(String searchTerm) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            // Check if the search term matches the product name, brand, or features
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                product.getBrand().toLowerCase().contains(searchTerm.toLowerCase()) ||
                (product.getFeatures() != null && product.getFeatures().toLowerCase().contains(searchTerm.toLowerCase()))) {
                filteredProducts.add(product);
            }
        }
        ImagePanel imagePanel = (ImagePanel) ((JScrollPane) getContentPane().getComponent(2)).getViewport().getView();
        imagePanel.removeAll();
        displayProducts(imagePanel, filteredProducts);
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void displayProducts(JPanel panel, ArrayList<Product> productList) {
        panel.removeAll();
        panel.setLayout(new GridLayout(0, 3));

        for (Product product : productList) {
            addImagePanel(panel, product.getImagePath(), product.getName(), product.getPrice(), product.getBrand(), product.getFeatures(), product.getRating());
        }

        panel.revalidate();
        panel.repaint();
    }

    private class ImagePanel extends JPanel {
        public ImagePanel() {
            setLayout(new GridLayout(0, 3));
        }
    }

    private JMenuItem createMenuItem(String title) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFrame(title);
            }
        });
        return menuItem;
    }

    private void openFrame(String title) {
        JFrame frame = null;
        switch (title) {
            case "Electronics":
                frame = new ElectronicsFrame();
                break;
            case "Men":
                frame = new MenClothingFrame();
                break;
            case "Women":
                frame = new WomenClothingFrame();
                break;
            case "Kids":
                frame = new KidsClothingFrame();
                break;
            case "Home Appliances":
                frame = new HomeAppliancesFrame();
                break;
            case "View Orders":
                frame = new OrderHistoryFrame();
                break;
            default:
                break;
        }
        if (frame != null) {
            frame.setVisible(true);
        }
    }

    private JPanel createSidePanelWithImages(String title, String content, String[] imagePaths) {
        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sidePanel.add(new JLabel(title, JLabel.CENTER));
        sidePanel.add(new JLabel(content, JLabel.CENTER));

        for (String imagePath : imagePaths) {
            JLabel imageLabel = new JLabel();
            ImageIcon icon = new ImageIcon(imagePath);
            imageLabel.setIcon(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setPreferredSize(new Dimension(50, 50));
            sidePanel.add(imageLabel);
        }

        return sidePanel;
    }

    private void addImagePanel(JPanel mainImagePanel, String imagePath, String productName, String price, String brand, String features, String rating) {
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
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderSummaryFrame(new Product(productName, price, brand, features, rating, imagePath));
            }
        });
        imagePanel.add(buyButton, BorderLayout.SOUTH);

        mainImagePanel.add(imagePanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}