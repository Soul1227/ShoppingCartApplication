import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingCart {
    private List<Product> products = new ArrayList<Product>();

    public ShoppingCart() {
    }

    public ShoppingCart(List<Product> products) {
        this.products = products;
    }

    /**
     * Creates a new product and add it to the ShoppingCart.
     *
     * @param name        of the product.
     * @param description of the product.
     * @param price       of the product.
     * @return a new product.
     */
    public Product Add(String name, String description, double price) {
        Product product = new Product(UUID.randomUUID().toString(), name, description, price);
        this.products.add(product);
        return product;
    }

    /**
     * Removes an item from the user's ShoppingCart.
     *
     * @param id of the item to delete.
     * @return true if the item is deleted, false otherwise.
     */
    public boolean Remove(String id) {
        int index = -1;
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            this.products.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the products in the cart.
     *
     * @return a List of product.
     */
    public List<Product> View() {
        return this.products;
    }

    /**
     * Calculates the total sum of all the items' prices.
     *
     * @return The total price of all items as a double.
     */
    public double Calculate() {
        double totalprice = 0;
        for (int i = 0; i < this.products.size(); i++) {
            totalprice += this.products.get(i).getPrice();
        }
        return totalprice;
    }
}
