package question;

public class Product {
    private String name;
    private String price;
    private String brand;
    private String features;
    private String rating;
    private String imagePath;

    public Product(String name, String price, String brand, String features, String rating, String imagePath) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.features = features;
        this.rating = rating;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getFeatures() {
        return features;
    }

    public String getRating() {
        return rating;
    }

    public String getImagePath() {
        return imagePath;
    }
}