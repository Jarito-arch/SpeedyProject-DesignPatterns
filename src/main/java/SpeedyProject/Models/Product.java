package SpeedyProject.Models;

public class Product {
    private int id;
    private int commerceId;
    private String name;
    private double price;
    private String description;
    private String imagePath;

    public Product() {
    }

    public Product(int id, String name, double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(int commerceId) {
        this.commerceId = commerceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return id == ((Product) o).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
