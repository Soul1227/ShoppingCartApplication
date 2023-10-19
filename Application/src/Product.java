/**
 * The Product class represents an item that can be added to a shopping cart.
 */
public class Product {
    private String id;
    private String name;
    private String description;
    private double price;

    /**
     * Constructs an empty product with default values.
     */
    public Product() {
    }

    /**
     * Constructs a product with the provided values.
     *
     * @param id          the unique identifier of the product
     * @param name        the name of the product
     * @param description a brief description of the product
     * @param price       the price of the product
     */
    public Product(String id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
