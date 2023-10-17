import java.util.List;

public class User {
    private String username;
    private int password;
    private ShoppingCart shoppingCart;
    private List<Coupon> coupons;

    public User() {
    }

    public User(String username, int password, ShoppingCart shoppingCart, List<Coupon> coupons) {
        this.username = username;
        this.password = password;
        this.shoppingCart = shoppingCart;
        this.coupons = coupons;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Coupon> getCupons() {
        return coupons;
    }

    public void setCupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
}
