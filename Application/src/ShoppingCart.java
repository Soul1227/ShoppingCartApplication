import java.util.LinkedList;

public class ShoppingCart {
    private LinkedList<Product> products = new LinkedList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(LinkedList<Product> products) {
        this.products = products;
    }

    /**
     * Adds an item to the user's ShoppingCart.
     * @param product to add in the cart.
     */
    public void add(Product product) {
        products.add(product);
    }

    /**
     * Removes an item from the user's ShoppingCart.
     * @param position of the item in the cart.
     */
    public void remove(int position) {
        try {
            products.remove(position);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("there is no item in that position");
        }
    }

    /**
     * Shows all the items in the user ShoppingCart.
     */
    public void view() {
        if (products.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            for (int x = 0; x < products.size(); x++) {
                System.out.println("Item position: " + x + "\n " + products.get(x).toString());
            }
        }
    }

    /**
     * Calculates the total sum of all the items' prices.
     *
     * @return The total price of all items as a double.
     */
    public double calculate() {
        double finalPrice = 0;
        for (Product product : products
        ) {
            finalPrice += product.getPrice();
        }
        return finalPrice;
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }
}
