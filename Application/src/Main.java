import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static User activatedUser; // The logged user.
    static ArrayList<User> users; // list of users created.
    static ArrayList<Product> products; // List of products at the store.
    static String command = ""; // user input.

    public static void main(String[] args) {
        initiating();
        System.out.println("Welcome to our online shop\ninsert 1 to log in\ninsert 2 if you are a new costumer");
        ChooseAuthentication();
        ShowProducts();
        ShowCommands();// commands to navigate at the store.
        /*
          From now on, the user will use the commands to add/remove items from his cart, check what is currently in
          the cart or check the total price, also it will be able to change user, going back to the login.
          The following loop will end when the command "purchase" is given.
         */
        while (!command.equals("purchase")) {
            String input = scanner.nextLine();
            input = input.trim().toLowerCase();
            String[] tokens = input.split(" ");
            command = tokens[0];
            switch (command) {
                case "add" -> {
                    boolean itemFounded = false;
                    for (Product product : products) {
                        try {
                            if (product.getId() == Integer.parseInt(tokens[1])) {
                                activatedUser.getShoppingCart().add(product);
                                System.out.println("Product added to the cart: " + product);
                                itemFounded = true;
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("please introduce a valid number");
                            break;
                        }
                    }
                    if (!itemFounded) System.out.println("There is no item with that id");
                }
                case "remove" -> {
                    try {
                        Product productDeleted = activatedUser.getShoppingCart().getProducts().get(Integer.parseInt(tokens[1]));
                        activatedUser.getShoppingCart().remove(Integer.parseInt(tokens[1]));
                        System.out.println("Product removed from the cart: " + productDeleted);
                    } catch (NumberFormatException ex) {
                        System.out.println("please introduce a valid number");
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("There is no item in your cart in that position");
                    }
                }
                case "view" -> activatedUser.getShoppingCart().view();
                case "calculate" -> System.out.println("Total price:" + activatedUser.getShoppingCart().calculate());
                case "help" -> ShowCommands();
                case "change" -> {
                    activatedUser = null;
                    login();
                }
                case "purchase" -> Purchase();
                default -> System.out.println("Unknown command: " + command);
            }
        }
    }

    /**
     * Shows the products available
     */
    private static void ShowProducts() {
        System.out.println("Our products are the following:");
        products.forEach(product -> System.out.println(product.toString()));
        System.out.println("\n");
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
        System.out.println("The total price of your purchase is: " + activatedUser.getShoppingCart().calculate());
        if (activatedUser.getShoppingCart().calculate() == 0) {
            System.out.println("Thank you for using our service, and have a nice day!");
        } else {
            if (!activatedUser.getCupons().isEmpty()) discount = UsinCupon();
            if (discount != 0) {
                double finalPrice = (discount / 100) * activatedUser.getShoppingCart().calculate();
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
    private static double UsinCupon() {
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
                    int cupongId = Integer.parseInt(command2);
                    if (activatedUser.getCupons().get(cupongId) != null) {
                        cuponSelected = true;
                        discount = activatedUser.getCupons().get(cupongId).getDiscount();
                        activatedUser.getCupons().remove(cupongId);
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
        System.out.println("To navigate through our shop please use the following commands:");
        System.out.println("add productId -> adds the product to your cart.");
        System.out.println("view -> shows the items in your cart and its position in it.");
        System.out.println("remove productPosition -> remove the item in that position from your cart.");
        System.out.println("calculate -> shows the total price that the items in your cart sum.");
        System.out.println("change -> take you back to the login procedure, to change user.");
        System.out.println("purchase -> takes you to the final step to accept or decline the purchase.");
        System.out.println("help -> shows the commands\n");
    }

    /**
     * Initialize the Lists
     */
    private static void initiating() {
        users = new ArrayList<>();
        users.add(new User("Ana", "1234", new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(30, "30%"), new Coupon(40, "40%")))));
        users.add(new User("Juan", "5555", new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(20, "20%")))));
        users.add(new User("Paco", "6666", new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(50, "50%")))));

        products = new ArrayList<>();
        products.add(new Product(1, "Tio Pepe", "white wine", 15.50));
        products.add(new Product(2, "Cabernet Sauvignon", "red wine", 25.50));
        products.add(new Product(3, "Merlot", "red wine", 35.50));
        products.add(new Product(4, "Syrah", "white wine", 45.50));
    }

    /**
     * Ask the user to enter their username and password and calls
     * the CheckUser() method for authentication.
     */
    private static void login() {
        while (activatedUser == null) {
            System.out.println("insert your username");
            String name = scanner.nextLine();
            System.out.println("insert your password");
            String password = scanner.nextLine();
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
                System.out.println("welcome " + activatedUser.getUsername()+"\n");
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
        while(nameTaken){
            System.out.println("insert your username");
            name = scanner.nextLine();
            if (CheckName(name)){
                System.out.println("The username is already taken, please choose another one");
            }else {
                nameTaken=false;
            }
        }
        System.out.println("insert your password");
        String password = scanner.nextLine();
        User newUser = new User(name, password, new ShoppingCart(new LinkedList<>()), new ArrayList<>());
        users.add(newUser);
        activatedUser = newUser;
        System.out.println("welcome "+activatedUser.getUsername()+"\n");
    }

    /**
     * Checks if the username for a new user is already taken.
     *
     * @param name to check
     * @return true is there is anothe user with that name, false otherwise.
     */
    private static boolean CheckName(String name) {
        for (User user:users) {
            if (user.getUsername().equals(name)) return true;
        }
        return false;
    }
}