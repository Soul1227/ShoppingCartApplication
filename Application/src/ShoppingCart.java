import java.util.LinkedList;

public class ShoppingCart {
    private LinkedList<Product> products = new LinkedList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(LinkedList<Product> products) {
        this.products = products;
    }

    public void add(Product product) {
        products.add(product);
    }

    public void remove(int position) {
        try {
            products.remove(position);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("there is no item in that position");
        }
    }

    public void view() {
        for (int x = 0; x < products.size(); x++) {
            System.out.println("Item position: " + x + "\n " + products.get(x).toString());
        }
    }

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
