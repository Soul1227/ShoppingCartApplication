import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static User activatedUser; // The logged user.
    static List<User> users = new ArrayList<User>();// list of users created.
    static List<Product> userProducts = new ArrayList<Product>(); // List of user's products.
    static List<Product> storeProducts = new ArrayList<Product>();// List of products at the store.
    static String command = ""; // user input.

    public static void main(String[] args) {
        initiating();
        System.out.println("Welcome to our online shop\ninsert 1 to log in\ninsert 2 if you are a new costumer");
        ChooseAuthentication();
        System.out.println("----------------------");
        System.out.println("-WELCOME TO THE STORE-");
        System.out.println("----------------------");
        /*
          From now on, the user will use the commands to add/remove items from his cart, check what is currently in
          the cart or check the total price, also it will be able to change user, going back to the login.
          The following loop will end when the command "purchase" is given.
         */
        while (!command.equals("purchase")) {
            if (!command.equals("create") && !command.equals("select")) {
                ShowCommands(); // commands to navigate at the store.
                command = scanner.nextLine().trim().toLowerCase();
            }
            switch (command) {
                case "select" -> {
                    //Select an item from the store and add it to the user's shopping cart.
                    ShowProducts(storeProducts);
                    Product productSelected = SelectProduct(storeProducts);
                    if (productSelected == null) continue;

                    productSelected = activatedUser.getShoppingCart().Add(productSelected.getName(), productSelected.getDescription(), productSelected.getPrice());
                    System.out.println("product added to the cart: " + productSelected.toString());
                    command = "";
                }
                case "view store" ->
                    //Shows the items in the store.
                        ShowProducts(storeProducts);
                case "create" -> {
                    //Creates a new item to add in the user's cart and to the store.
                    boolean correctPrice = false;
                    System.out.println("insert a name´s product:");
                    String name = scanner.nextLine();
                    System.out.println("insert a description:");
                    String description = scanner.nextLine();
                    System.out.println("insert price:");
                    double price = 0;
                    while (!correctPrice) {
                        try {
                            price = Double.parseDouble(scanner.nextLine());
                            correctPrice = true;
                        } catch (Exception ex) {
                            System.out.println("please inset a correct number (00.00)");
                        }
                    }
                    Product product = activatedUser.getShoppingCart().Add(name, description, price);
                    storeProducts.add(product);
                    System.out.println("product added to the cart: " + product.toString());
                    command = "";
                }
                case "add" -> {
                    //Choose between create and select.
                    System.out.println("If you want an item from the store type 'select'\n if you want to create a new one type 'create");
                    command = scanner.nextLine();
                }
                case "remove" -> {
                    //removes an item from the user's cart.
                    userProducts = activatedUser.getShoppingCart().View();
                    ShowProducts(userProducts);
                    Product productToRemove = SelectProduct(userProducts);
                    if (productToRemove == null) continue;

                    boolean removed = activatedUser.getShoppingCart().Remove(productToRemove.getId());
                    if (removed) {
                        System.out.println("the item " + productToRemove.toString() + " has been removed");
                    } else {
                        System.out.println("the item " + productToRemove.toString() + " couldnt be removed");
                    }
                }
                case "view cart" -> {
                    //Shows the items in the user's cart.
                    userProducts = activatedUser.getShoppingCart().View();
                    System.out.println("Your cart:");
                    ShowProducts(userProducts);
                }
                case "calculate" ->
                    //Shows the total price of the cart.
                        System.out.println("Total price:" + activatedUser.getShoppingCart().Calculate());
                case "help" ->
                    // Shows the commands to uses.
                        ShowCommands();
                case "change" -> {
                    //Changes users.
                    activatedUser = null;
                    login();
                }
                case "purchase" ->
                    //Buy the items in the user's cart.
                        Purchase();
                default -> System.out.println("Unknown command: " + command);
            }
        }
    }

    /**
     * Shows the products available in the List given.
     *
     * @param products List of products to show.
     */
    private static void ShowProducts(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + " -> " + products.get(i).toString());
        }
        System.out.println("");
    }

    /**
     * Select a product from a List.
     *
     * @param products List of products where to choose.
     * @return the product chosen.
     */
    private static Product SelectProduct(List<Product> products) {
        int position = -1;
        boolean correctPosition = false;
        while (!correctPosition) {
            try {
                position = Integer.parseInt(scanner.nextLine());
                correctPosition = true;
            } catch (Exception ex) {
                System.out.println("please inset a correct position");
            }
        }
        if (position >= products.size() || position < 0) {
            System.out.println("there is no item in that position");
            return null;
        }
        return products.get(position);
    }

    /**
     * Gives the user the option to choose between log in or sing in.
     */
    private static void ChooseAuthentication() {
        boolean chosen = false;
        while (!chosen) {
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    login();
                    chosen = true;
                }
                case "2" -> {
                    CreateNewUser();
                    chosen = true;
                }
                default ->
                        System.out.println("command not found\ninsert 1 to log in\ninsert 2 if you are a new costumer");
            }
        }
    }

    /**
     * Checks if there is any coupon for the user to use. If a coupon is used, a discount would be applied
     * to the final price, otherwise the final price would remain as normal.
     */
    private static void Purchase() {
        double discount = 0;
        System.out.println("The total price of your purchase is: " + activatedUser.getShoppingCart().Calculate());
        if (activatedUser.getShoppingCart().Calculate() == 0) {
            System.out.println("Thank you for using our service, and have a nice day!");
        } else {
            if (!activatedUser.getCupons().isEmpty()) discount = UsingCoupon();
            if (discount != 0) {
                double finalPrice = (discount / 100) * activatedUser.getShoppingCart().Calculate();
                DecimalFormat df = new DecimalFormat("0.00");
                System.out.println("The final price for your purchase is: " + df.format(finalPrice));
            }
            System.out.println("Thank you for using our service, and have a nice day!");
        }
    }

    /**
     * Shows the coupons the user have, and gives the option to use any or not.
     * if a coupon is used, this must be deleted form the user coupon List.
     *
     * @return the discount to use.
     */
    private static double UsingCoupon() {
        boolean cuponSelected = false;
        int discount = 0;
        System.out.println("You have the following coupons:");
        for (int x = 0; x <= activatedUser.getCupons().size() - 1; x++) {
            System.out.println("Coupon id " + x);
            System.out.println(activatedUser.getCupons().get(x).getName() + " Discount");
        }
        while (!cuponSelected) {
            System.out.println("If you want to use any, please type its id number, otherwise type 'no'");
            String command2 = scanner.nextLine();
            if (command2.equals("no")) {
                cuponSelected = true;
            } else {
                try {
                    int coupongId = Integer.parseInt(command2);
                    if (activatedUser.getCupons().get(coupongId) != null) {
                        cuponSelected = true;
                        discount = activatedUser.getCupons().get(coupongId).getDiscount();
                        activatedUser.getCupons().remove(coupongId);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("the number given does not match any coupon");
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("there is no coupon in that position");
                }
            }
        }
        return discount;
    }

    /**
     * Shows the user the commands to used.
     */
    private static void ShowCommands() {
        System.out.println("");
        System.out.println("Use the following commands to navigate throuth the store:");
        System.out.println("");
        System.out.println("add -> adds the product to your cart.");
        System.out.println("view cart-> shows the items in your cart and its position in it.");
        System.out.println("view store-> shows the items at the store.");
        System.out.println("remove -> remove the item in that position from your cart.");
        System.out.println("calculate -> shows the total price that the items in your cart sum.");
        System.out.println("change -> take you back to the login procedure, to change user.");
        System.out.println("purchase -> takes you to the final step to accept or decline the purchase.");
        System.out.println("help -> shows the commands");
        System.out.println("");
    }

    /**
     * Initialize the Lists
     */
    private static void initiating() {
        users.add(new User("Ana", Base64.getEncoder().encodeToString("1234".getBytes()), new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(30, "30%"), new Coupon(40, "40%")))));
        users.add(new User("Juan", Base64.getEncoder().encodeToString("5555".getBytes()), new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(20, "20%")))));
        users.add(new User("Paco", Base64.getEncoder().encodeToString("6666".getBytes()), new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(50, "50%")))));

        storeProducts.add(new Product("1", "Tio Pepe", "white wine", 15.50));
        storeProducts.add(new Product("2", "Cabernet Sauvignon", "red wine", 25.50));
        storeProducts.add(new Product("3", "Merlot", "red wine", 35.50));
        storeProducts.add(new Product("4", "Syrah", "white wine", 45.50));
    }

    /**
     * Ask the user to insert their username and password, calls
     * the CheckUser() method for authentication.
     */
    private static void login() {
        while (activatedUser == null) {
            System.out.println("insert your username");
            String name = scanner.nextLine();
            System.out.println("insert your password");
            String password = scanner.nextLine();
            password = Base64.getEncoder().encodeToString(password.getBytes());
            CheckUser(name, password);
        }
    }

    /**
     * Checks the username and password given. if there is any user that
     * matches in the users List, it will be set as activatedUser.
     */
    private static void CheckUser(String name, String password) {
        users.forEach(user -> {
            if (user.getUsername().equals(name) && user.getPassword().equals(password)) {
                activatedUser = user;
                System.out.println("welcome " + activatedUser.getUsername() + "\n");
            }
        });
        if (activatedUser == null) {
            System.out.println("User not found");
        }
    }

    /**
     * Creates a new User with the data given.
     * Adds this new User to the Users List and set it as activatedUser.
     */
    private static void CreateNewUser() {
        boolean nameTaken = true;
        String name = "";
        System.out.println("**Creating a new user**");
        while (nameTaken) {
            System.out.println("insert your username");
            name = scanner.nextLine();
            nameTaken = CheckName(name);
            if (nameTaken) System.out.println("The username is already taken, please choose another one");
        }
        System.out.println("insert your password");
        String password = scanner.nextLine();
        password = Base64.getEncoder().encodeToString(password.getBytes());
        User newUser = new User(name, password);
        users.add(newUser);
        activatedUser = newUser;
        System.out.println("welcome " + activatedUser.getUsername() + "\n");
    }

    /**
     * Checks if the username for a new user is already taken.
     *
     * @param name to check
     * @return true is there is anothe user with that name, false otherwise.
     */
    private static boolean CheckName(String name) {
        boolean found = false;
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                found = true;
                break;
            }
        }
        return found;
    }
}