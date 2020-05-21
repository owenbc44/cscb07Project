package com.b07.store;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import com.b07.accounts.Account;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;

public class SalesApplication {
  /**
   * This is the main method to run your entire program! Follow the "Pulling it together"
   * instructions to finish this off.
   * 
   * @param argv unused.
   */
  public static void main(String[] argv) {

    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      System.out.print("Unable to connect to Database, make sure correct .jar has been referenced");
    }
    try {


      System.out.println("What do you want to do?");
      System.out.println(" -1 - Setup New Database with an Admin and Employee account");
      System.out.println("  1 - Admin Login");
      System.out.println("  0 - Exit");
      System.out.println("o/w - More Options");

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      String input = bufferedReader.readLine();

      while (!input.equals("0")) {

        if (input.equals("-1")) {
          DatabaseDriverExtender.initialize(connection);


          System.out.println("What is the name of the admin?");
          String input1 = bufferedReader.readLine();
          System.out.println("What is the age of the admin?");
          String input2 = bufferedReader.readLine();
          System.out.println("What is the address of the admin?");
          String input3 = bufferedReader.readLine();
          System.out.println("What is the password of the admin?");
          String input4 = bufferedReader.readLine();

          int userId1 =
              DatabaseInsertHelper.insertNewUser(input1, Integer.valueOf(input2), input3, input4);
          System.out.println("The admin user id is " + userId1);

          System.out.println("What is the name of the employee?");
          input1 = bufferedReader.readLine();
          System.out.println("What is the age of the employee?");
          input2 = bufferedReader.readLine();
          System.out.println("What is the address of the employee?");
          input3 = bufferedReader.readLine();
          System.out.println("What is the password of the employee?");
          input4 = bufferedReader.readLine();

          int userId2 =
              DatabaseInsertHelper.insertNewUser(input1, Integer.valueOf(input2), input3, input4);
          System.out.println("The employee user id is " + userId2);

          int roleId1 = DatabaseInsertHelper.insertRole("ADMIN");
          int roleId2 = DatabaseInsertHelper.insertRole("EMPLOYEE");

          DatabaseInsertHelper.insertUserRole(userId1, roleId1);
          DatabaseInsertHelper.insertUserRole(userId2, roleId2);

        } else if (input.equals("1")) {

          System.out.println("Admin sign in time!");
          System.out.println("What is your user id?");

          String input1 = bufferedReader.readLine();

          int roleIdForAllegedAdmin = DatabaseSelectHelper.getUserRoleId(Integer.valueOf(input1));
          String roleName1 = DatabaseSelectHelper.getRoleName(roleIdForAllegedAdmin);

          if (roleName1.equals("ADMIN")) {
            System.out.println("What is your password?");
            String input2 = bufferedReader.readLine();

            Admin admin = (Admin) DatabaseSelectHelper.getAdminDetails(Integer.valueOf(input1));
            boolean login = admin.authenticate(input2);

            if (login == false) {
              System.out.println("Login failed! Please try again");
            } else {
              System.out.println("Logged in successfully!");
              System.out.println("What do you want to do?");
              System.out.println("  1 - Promote an Employee");
              System.out.println("  2 - View Books");
              System.out.println("o/w - Go Back to Previous Menu");

              input = bufferedReader.readLine();

              if (input.equals("1")) {
                System.out.println("UserId of the employee you want to promote please!");
                String input3 = bufferedReader.readLine();

                int roleIdForAllegedEmployee =
                    DatabaseSelectHelper.getUserRoleId(Integer.valueOf(input3));
                String roleName2 = DatabaseSelectHelper.getRoleName(roleIdForAllegedEmployee);

                if (roleName2.equals("EMPLOYEE")) {
                  Employee employeeToPromote =
                      (Employee) DatabaseSelectHelper.getEmployeeDetails(Integer.valueOf(input3));
                  boolean promotionSuccess = admin.promoteEmployee(employeeToPromote);

                  if (promotionSuccess == true) {
                    System.out.println("Employee " + input3 + " promoted successfully!");
                  } else {
                    System.out.println("Promotion failed!");
                  }

                } else {
                  System.out.println("That is not an Employee! Please try again");
                }



              } else if (input.equals("2")) {
                admin.viewBooks();
              }

            }

          } else {
            System.out.println("That is not an Admin! Please try again.");
          }



        } else {

          System.out.println("Choose an option!");
          System.out.println("  1 - Employee Login");
          System.out.println("  2 - Customer Login");
          System.out.println("o/w - Exit to previous menu");

          input = bufferedReader.readLine();

          if (input.equals("1")) {

            System.out.println("Employee sign in time!");
            System.out.println("What is your user id?");
            String input1 = bufferedReader.readLine();

            int roleIdForAllegedEmployee =
                DatabaseSelectHelper.getUserRoleId(Integer.valueOf(input1));
            String roleName1 = DatabaseSelectHelper.getRoleName(roleIdForAllegedEmployee);

            if (roleName1.equals("EMPLOYEE")) {
              System.out.println("What is your password?");
              String input2 = bufferedReader.readLine();

              Employee employee =
                  (Employee) DatabaseSelectHelper.getEmployeeDetails(Integer.valueOf(input1));
              boolean login = employee.authenticate(input2);

              if (login == true) {
                System.out.println("Logged in successfully!");
                EmployeeInterface empInt = new EmployeeInterface(employee, new InventoryImpl());

                System.out.println("Choose an option!");
                System.out.println("1 - Authenticate New Employee");
                System.out.println("2 - Make New Account");
                System.out.println("3 - Make New Employee");
                System.out.println("4 - Make New Customer");
                System.out.println("5 - Restock Inventory");
                System.out.println("6 - Exit to previous menu");

                input = bufferedReader.readLine();

                while (!input.equals("6")) {

                  if (input.equals("1")) {
                    System.out.println("UserId of employee to authenticate please");
                    String input3 = bufferedReader.readLine();

                    int roleIdForAllegedEmployee2 =
                        DatabaseSelectHelper.getUserRoleId(Integer.valueOf(input3));
                    String roleName2 = DatabaseSelectHelper.getRoleName(roleIdForAllegedEmployee2);

                    if (roleName2.equals("EMPLOYEE")) {
                      System.out.println("Employee password please");
                      String input4 = bufferedReader.readLine();

                      Employee employeeToAuthenticate =
                          (Employee) DatabaseSelectHelper.getUserDetails(Integer.valueOf(input3));
                      boolean login2 = employeeToAuthenticate.authenticate(input4);

                      if (login2 == true) {
                        System.out.println("Employee authenticated successfully!");
                      } else {
                        System.out.println("Employee authentication failed. Please try again");
                      }

                    }
                  } else if (input.equals("2")) {
                    System.out.println("The ID of the Customer you want to make an Account for?");
                    String input3 = bufferedReader.readLine();

                    Customer customer =
                        (Customer) DatabaseSelectHelper.getUserDetails(Integer.valueOf(input3));

                    if (customer.equals(null)) {
                      System.out.println("Customer does not exist!");
                    } else {
                      int accountId = DatabaseInsertHelper.insertAccount(Integer.valueOf(input3));
                      System.out
                          .println("Account created! Account ID is " + String.valueOf(accountId));
                    }



                  } else if (input.equals("3")) {

                    System.out.println("What is the name of the employee?");
                    String input10 = bufferedReader.readLine();
                    System.out.println("What is the age of the employee?");
                    String input11 = bufferedReader.readLine();
                    System.out.println("What is the address of the employee?");
                    String input12 = bufferedReader.readLine();
                    System.out.println("What is the password of the employee?");
                    String input13 = bufferedReader.readLine();

                    int employeeUserId = DatabaseInsertHelper.insertNewUser(input10,
                        Integer.valueOf(input11), input12, input13);
                    System.out.println("The employee user id is " + employeeUserId);

                    int employeeRoleId = DatabaseInsertHelper.insertRole("EMPLOYEE");
                    DatabaseInsertHelper.insertUserRole(employeeUserId, employeeRoleId);


                  } else if (input.equals("4")) {

                    System.out.println("What is the name of the customer?");
                    String input10 = bufferedReader.readLine();
                    System.out.println("What is the age of the customer?");
                    String input11 = bufferedReader.readLine();
                    System.out.println("What is the address of the customer?");
                    String input12 = bufferedReader.readLine();
                    System.out.println("What is the password of the customer?");
                    String input13 = bufferedReader.readLine();

                    int customerUserId = DatabaseInsertHelper.insertNewUser(input10,
                        Integer.valueOf(input11), input12, input13);
                    System.out.println("The customer user id is " + String.valueOf(customerUserId));

                    int customerRoleId = DatabaseInsertHelper.insertRole("CUSTOMER");
                    DatabaseInsertHelper.insertUserRole(customerUserId, customerRoleId);

                  } else if (input.equals("5")) {
                    System.out.println("What is the itemId of the item you want to restock?");
                    String input10 = bufferedReader.readLine();
                    System.out.println("What is the quantity you want to restock?");
                    String input11 = bufferedReader.readLine();

                    Item item = DatabaseSelectHelper.getItem(Integer.valueOf(input10));
                    boolean restocked = empInt.restockInventory(item, Integer.valueOf(input11));

                    if (restocked == true) {
                      System.out.println("Restock successful!");
                    } else {
                      System.out.println("Restock failed");
                    }

                  }

                  System.out.println("Choose an option!");
                  System.out.println("1 - Authenticate New Employee");
                  System.out.println("2 - Make New Account");
                  System.out.println("3 - Make New Employee");
                  System.out.println("4 - Make New Customer");
                  System.out.println("5 - Restock Inventory");
                  System.out.println("6 - Exit to previous menu");

                  input = bufferedReader.readLine();
                }

              } else {
                System.out.println("Login failed! Please try again");

              }

            } else {
              System.out.println("That is not an Employee! Please try again");
            }



          } else if (input.equals("2")) {
            System.out.println("Customer sign in time!");
            System.out.println("What is your user id?");
            String input1 = bufferedReader.readLine();

            int roleIdForAllegedCustomer =
                DatabaseSelectHelper.getUserRoleId(Integer.valueOf(input1));
            String roleName1 = DatabaseSelectHelper.getRoleName(roleIdForAllegedCustomer);

            if (roleName1.equals("CUSTOMER")) {

              System.out.println("What is your password?");
              String input2 = bufferedReader.readLine();

              Customer customer =
                  (Customer) DatabaseSelectHelper.getCustomerDetails(Integer.valueOf(input1));
              boolean login = customer.authenticate(input2);

              if (login == true) {
                System.out.println("Logged in successfully!");
                ShoppingCart cart = new ShoppingCart(customer);

                System.out.println("choose an option!");
                System.out.println("1 - List Current Items in Cart");
                System.out.println("2 - Add a quantity of an item to the cart");
                System.out.println("3 - Check total price of items in the cart");
                System.out.println("4 - Remove a quantity of an item from the cart");
                System.out.println("5 - Check Out");
                System.out.println("6 - Store Current Shopping Cart in an Account");
                System.out.println("7 - Retrieve an Account");
                System.out.println("8 - Exit to previous menu");

                input = bufferedReader.readLine();

                while (!input.equals("8")) {

                  if (input.equals("1")) {

                    List<Item> items = cart.getItems();
                    int lenList = items.size();

                    for (int i = 0; i < lenList; i++) {
                      System.out.println(items.get(i).getName());
                    }

                  } else if (input.equals("2")) {

                    System.out.println("What is the item id of the item you want to add to cart?");
                    String input10 = bufferedReader.readLine();
                    System.out.println("What is the quantity you want to add to cart?");
                    String input11 = bufferedReader.readLine();

                    Item item = DatabaseSelectHelper.getItem(Integer.valueOf(input10));

                    cart.addItem(item, Integer.valueOf(input11));

                  } else if (input.equals("3")) {

                    BigDecimal total = cart.getTotal();
                    System.out.println("Your current total is " + total.toString());

                  } else if (input.equals("4")) {

                    System.out
                        .println("What is the item id of the item you want to remove from cart?");
                    String input10 = bufferedReader.readLine();
                    System.out.println("What is the quantity you want to remove from cart?");
                    String input11 = bufferedReader.readLine();

                    Item item = DatabaseSelectHelper.getItem(Integer.valueOf(input10));

                    cart.removeItem(item, Integer.valueOf(input11));

                  } else if (input.equals("5")) {

                    boolean checkOut = cart.checkOut();

                    if (checkOut == true) {
                      System.out.println("Check out successful!");
                    } else {
                      System.out.println("Check out unsuccessful");
                    }

                  } else if (input.equals("6")) {
                    System.out
                        .println("The id for the account you want to store the shopping cart in?");
                    String input3 = bufferedReader.readLine();

                    List<Account> userAccounts =
                        DatabaseSelectHelper.getUserAccounts(Integer.valueOf(input1));


                    int numberOfAccounts = userAccounts.size();


                    for (int i = 0; i < numberOfAccounts; i++) {
                      if (userAccounts.get(i).getId() == Integer.valueOf(input3)) {

                        System.out
                            .println("Account found! Storing current shopping cart in account");

                        HashMap<Item, Integer> itemMap = cart.getItemMap();

                        for (Item key : itemMap.keySet()) {
                          DatabaseInsertHelper.insertAccountLine(Integer.valueOf(input3),
                              key.getId(), itemMap.get(key));
                        }
                      }
                    }

                  } else if (input.equals("7")) {
                    System.out.println(
                        "The id for the account you want to retrieve the shopping cart from?");
                    String input3 = bufferedReader.readLine();

                    List<Account> userAccounts =
                        DatabaseSelectHelper.getUserAccounts(Integer.valueOf(input1));


                    int numberOfAccounts = userAccounts.size();
                    Account account;

                    for (int i = 0; i < numberOfAccounts; i++) {
                      if (userAccounts.get(i).getId() == Integer.valueOf(input3)) {

                        System.out.println(
                            "Account found! Setting current shopping cart as the one in the account");
                        cart.clearCart();
                        account = userAccounts.get(i);
                        int accountId = account.getId();

                        HashMap<Item, Integer> itemMap = account.getCart().getItemMap();

                        for (Item key : itemMap.keySet()) {
                          cart.addItem(key, itemMap.get(key));
                        }
                      }
                    }

                  }


                  System.out.println("choose an option!");
                  System.out.println("1 - List Current Items in Cart");
                  System.out.println("2 - Add a quantity of an item to the cart");
                  System.out.println("3 - Check total price of items in the cart");
                  System.out.println("4 - Remove a quantity of an item from the cart");
                  System.out.println("5 - Check Out");
                  System.out.println("6 - Store Current Shopping Cart in an Account");
                  System.out.println("7 - Retrieve Shopping Cart from an Account");
                  System.out.println("8 - Exit to previous menu");

                  input = bufferedReader.readLine();
                }

              } else {
                System.out.println("Login failed! Please try again");

              }

            } else {
              System.out.println("That is not a customer! Please try again");
            }

          }

        }



        System.out.println("What do you want to do?");
        System.out.println(" -1 - Setup New Database with an Admin and Employee account");
        System.out.println("  1 - Admin Login");
        System.out.println("  0 - Exit");
        System.out.println("o/w - More Options");
        input = bufferedReader.readLine();

      }
      // TODO Check what is in argv
      // If it is -1
      /*
       * TODO This is for the first run only! Add this code:
       * DatabaseDriverExtender.initialize(connection); Then add code to create your first account,
       * an administrator with a password Once this is done, create an employee account as well.
       * 
       */
      // If it is 1
      /*
       * TODO In admin mode, the user must first login with a valid admin account This will allow
       * the user to promote employees to admins. Currently, this is all an admin can do.
       */
      // If anything else - including nothing
      /*
       * TODO Create a context menu, where the user is prompted with: 1 - Employee Login 2 -
       * Customer Login 0 - Exit Enter Selection:
       */
      // If the user entered 1
      /*
       * TODO Create a context menu for the Employee interface Prompt the employee for their id and
       * password Attempt to authenticate them. If the Id is not that of an employee or password is
       * incorrect, end the session If the Id is an employee, and the password is correct, create an
       * EmployeeInterface object then give them the following options: 1. authenticate new employee
       * 2. Make new User 3. Make new account 4. Make new Employee 5. Restock Inventory 6. Exit
       * 
       * Continue to loop through as appropriate, ending once you get an exit code (9)
       */
      // If the user entered 2
      /*
       * TODO create a context menu for the customer Shopping cart Prompt the customer for their id
       * and password Attempt to authenticate them If the authentication fails or they are not a
       * customer, repeat If they get authenticated and are a customer, give them this menu: 1. List
       * current items in cart 2. Add a quantity of an item to the cart 3. Check total price of
       * items in the cart 4. Remove a quantity of an item from the cart 5. check out 6. Exit
       * 
       * When checking out, be sure to display the customers total, and ask them if they wish to
       * continue shopping for a new order
       * 
       * For each of these, loop through and continue prompting for the information needed Continue
       * showing the context menu, until the user gives a 6 as input.
       */
      // If the user entered 0
      /*
       * TODO Exit condition
       */
      // If the user entered anything else:
      /*
       * TODO Re-prompt the user
       */



    } catch (Exception e) {
      // TODO Improve this!
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }
    }

  }
}
