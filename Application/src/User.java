import java.util.List;

public class User {
    private String username;
    private String password;
    private ShoppingCart shoppingCart;
    private List<Coupon> coupons;

    public User() {
    }

    public User(String username, String password, ShoppingCart shoppingCart, List<Coupon> coupons) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
