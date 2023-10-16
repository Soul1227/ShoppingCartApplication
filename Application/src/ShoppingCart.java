public class ShoppingCart {
    private Product[] products;

    public ShoppingCart() {
    }

    public ShoppingCart(Product[] products) {
        this.products = products;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}
