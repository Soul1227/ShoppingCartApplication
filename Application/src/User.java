import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user of an online shopping system. Each user has a unique
 * username and password for authentication, a shopping cart to store items they wish to
 * purchase, and a list of coupons that can be applied to their orders.
 */
public class User {
    private String username;
    private String password;
    private ShoppingCart shoppingCart;
    private List<Coupon> coupons;

    /**
     * Constructs a new User with default values.
     */
    public User() {
    }

    /**
     * Constructs a new User with the provided username and password. Initializes an
     * empty shopping cart and an empty list of coupons.
     *
     * @param username the user's username
     * @param password the user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.shoppingCart = new ShoppingCart();
        this.coupons = new ArrayList<Coupon>();
    }

    /**
     * Constructs a new User with the provided username, password, shopping cart, and
     * list of coupons.
     *
     * @param username     the user's username
     * @param password     the user's password
     * @param shoppingCart the user's shopping cart
     * @param coupons      the list of coupons associated with the user
     */
    public User(String username, String password, ShoppingCart shoppingCart, List<Coupon> coupons) {
        this.username = username;
        this.password = password;
        this.shoppingCart = shoppingCart;
        this.coupons =coupons;
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
