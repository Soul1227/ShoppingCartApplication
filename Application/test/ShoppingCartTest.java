import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    ShoppingCart shoppingCart;
    Product product1 = new Product(1,"tio Pepe","white wine",15.50);
    Product product2 = new Product(2,"Cabernet Sauvignon","red wine",25.50);
    Product product3 = new Product(3,"Merlot","red wine",35.50);
    Product product4 = new Product(4,"Syrah","white wine",45.50);

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(new LinkedList<>(Arrays.asList(product1,product2,product3,product4)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        Product product5 = new Product(5,"SolYSombra","white wine",12.50);
        shoppingCart.add(product5);
        assertEquals(5,shoppingCart.getProducts().size());
        assertTrue(shoppingCart.getProducts().contains(product5));
    }

    @Test
    void remove() {
        shoppingCart.remove(2);
        assertTrue(shoppingCart.getProducts().size()<4);
        assertFalse(shoppingCart.getProducts().contains(product3));
        shoppingCart.remove(20);
    }

    @Test
    void view() {
        shoppingCart.view();
    }

    @Test
    void calculate() {
        System.out.println(shoppingCart.calculate());
    }

    @Test
    void getProducts() {
    }

    @Test
    void setProducts() {
    }
}