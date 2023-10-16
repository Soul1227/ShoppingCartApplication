public class User {
    private String username;
    private int password;
    private ShoppingCart shoppingCart;
    private Cupon[] cupons;

    public User() {
    }

    public User(String username, int password, ShoppingCart shoppingCart, Cupon[] cupons) {
        this.username = username;
        this.password = password;
        this.shoppingCart = shoppingCart;
        this.cupons = cupons;
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

    public Cupon[] getCupons() {
        return cupons;
    }

    public void setCupons(Cupon[] cupons) {
        this.cupons = cupons;
    }
}
