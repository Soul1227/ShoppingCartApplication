/**
 * The Coupon class represents a discount coupon that can be applied to an order during
 * an online shopping session.
 */
public class Coupon {
    private int discount;
    private String name;

    /**
     * Constructs an empty coupon with default values.
     */
    public Coupon() {
    }

    /**
     * Constructs a coupon with the provided discount and name.
     *
     * @param discount the discount amount to be applied when using the coupon
     * @param name     the name or code associated with the coupon
     */
    public Coupon(int discount, String name) {
        this.discount = discount;
        this.name = name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
