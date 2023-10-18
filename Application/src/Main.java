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
        System.out.println("Welcome to our online shop\nFirst insert you username and password\nIf you are a new costumer please type 'new'");
        login();
        System.out.println("Welcome " + activatedUser.getUsername() + "\n");
        System.out.println("Our products are the following:");
        //show products
        products.forEach(product -> System.out.println(product.toString()));
        System.out.println("To navigate through our shop please use the following commands:");
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
                    System.out.println("Insert your username:");
                    login();
                }
                case "purchase" -> {
                    Purchase();
                }
                default -> System.out.println("Unknown command: " + command);
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
        if(activatedUser.getShoppingCart().calculate()==0){
            System.out.println("Thank you for using our service, and have a nice day!");
        }else{
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
        System.out.println("add productId -> adds the product to your cart.");
        System.out.println("view -> shows the items in your cart and its position in it.");
        System.out.println("remove productPosition -> remove the item in that position from your cart.");
        System.out.println("calculate -> shows the total price that the items in your cart sum.");
        System.out.println("change -> take you back to the login procedure, to change user.");
        System.out.println("purchase -> takes you to the final step to accept or decline the purchase.\n");
        System.out.println("help -> shows the commands");
    }

    /**
     * Initialize the Lists
     */
    private static void initiating() {
        users = new ArrayList<>();
        users.add(new User("Ana", 1234, new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(30, "30%"), new Coupon(40, "40%")))));
        users.add(new User("Juan", 5555, new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(20, "20%")))));
        users.add(new User("Paco", 6666, new ShoppingCart(new LinkedList<>()), new ArrayList<>(List.of(new Coupon(50, "50%")))));

        products = new ArrayList<>();
        products.add(new Product(1, "Tio Pepe", "white wine", 15.50));
        products.add(new Product(2, "Cabernet Sauvignon", "red wine", 25.50));
        products.add(new Product(3, "Merlot", "red wine", 35.50));
        products.add(new Product(4, "Syrah", "white wine", 45.50));
    }

    /**
     * Performs user login functionality, including username and password validation.
     * The method prompts the user to enter their username and password, and performs
     * the following actions:
     * - If the username is "new", it calls the CreateNewUser() method.
     * - If the username is invalid (contains non-alphabet characters), it prompts the
     * user to enter a valid username.
     * - If the password is invalid (not a 4-digit number), it prompts the user to enter
     * a valid password.
     * - Finally, it calls the CheckUser() method to verify the entered username and
     * password for authentication.
     */
    private static void login() {
        while (activatedUser == null) {
            String name = scanner.nextLine();
            if (name.equals("new")) {
                CreateNewUser();
            } else {
                while (CheckName(name)) {
                    System.out.println("insert your username (Only letter please)");
                    name = scanner.nextLine();
                }
                System.out.println("insert your password (A number with 4 digits)");
                int password = scanner.nextInt();
                while (CheckPassword(password)) {
                    System.out.println("insert your password (A number with 4 digits)");
                    password = scanner.nextInt();
                }
                CheckUser(name, password);
            }
        }
    }

    /**
     * Checks if the number password is between 1000 an 9999.
     *
     * @param password the number to be checked
     * @return True if password is smaller than 1000 or bigger than 9999, false otherwise.
     */
    private static boolean CheckPassword(int password) {
        return password < 1000 || password > 9999;
    }

    /**
     * Checks if a string contains only letters (alphabet).
     *
     * @param name The string to be checked.
     * @return false if the string contains only letters, true otherwise.
     */
    private static boolean CheckName(String name) {
        return !name.matches("^[a-zA-Z]+$");
    }

    /**
     * Checks the username and password given. if there is any user that
     * matches in the users List, it will be set as activatedUser.
     */
    private static void CheckUser(String name, int password) {
        users.forEach(user -> {
            if (user.getUsername().equals(name) && user.getPassword() == password) {
                activatedUser = user;
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
        System.out.println("**Creating a new user**");
        System.out.println("insert your username (Only letter please)");
        String name = scanner.nextLine();
        System.out.println("insert your password (A number with 4 digits)");
        int password = scanner.nextInt();

        User newUser = new User(name, password, new ShoppingCart(new LinkedList<>()), new ArrayList<>());
        users.add(newUser);
        activatedUser = newUser;
    }
}