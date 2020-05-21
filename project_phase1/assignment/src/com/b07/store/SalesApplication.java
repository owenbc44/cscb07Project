package com.b07.store;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.inventory.InventoryImpl;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;

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
      System.out.print("NOOO");
    }
    try {
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
          System.out.println("What is your password?");
          String input2 = bufferedReader.readLine();

          User user = DatabaseSelectHelper.getUserDetails(Integer.valueOf(input1));
          boolean login = user.authenticate(input2);

          if (login == false) {
            System.out.println("login failed! please try again");
          }

          System.out.println("loggedin successfully!");

        } else {

          System.out.println("choose an option!");
          System.out.println("1 - Employee Login");
          System.out.println("2 - Customer Login");
          System.out.println("0 - Exit");

          input = bufferedReader.readLine();

          if (input.equals("1")) {

            System.out.println("Employee sign in time!");
            System.out.println("What is your user id?");
            String input1 = bufferedReader.readLine();
            System.out.println("What is your password?");
            String input2 = bufferedReader.readLine();

            User user = DatabaseSelectHelper.getUserDetails(Integer.valueOf(input1));
            boolean login = user.authenticate(input2);

            if (login == true) {
              System.out.println("loggedin successfully!");
              EmployeeInterface empInt =
                  new EmployeeInterface((Employee) user, new InventoryImpl());
              System.out.println("choose an option!");
              System.out.println("1 - Authenticate New Employee");
              System.out.println("2 - New User");
              System.out.println("3 - New Account");
              System.out.println("4 - Make New Employee");
              System.out.println("5 - Restock Inventory");
              System.out.println("6 - Exit");

              input = bufferedReader.readLine();

              while (!input.equals("6")) {
                System.out.println("choose an option!");
                System.out.println("1 - Authenticate New Employee");
                System.out.println("2 - New User");
                System.out.println("3 - New Account");
                System.out.println("4 - Make New Employee");
                System.out.println("5 - Restock Inventory");
                System.out.println("6 - Exit");

                input = bufferedReader.readLine();
              }



            } else {
              System.out.println("login failed!");
              break;
            }


          } else if (input.equals("2")) {
            System.out.println("Customer sign in time!");
            System.out.println("What is your user id?");
            String input1 = bufferedReader.readLine();
            System.out.println("What is your password?");
            String input2 = bufferedReader.readLine();

            User user = DatabaseSelectHelper.getUserDetails(Integer.valueOf(input1));
            boolean login = user.authenticate(input2);

            if (login == true) {
              System.out.println("loggedin successfully!");
              ShoppingCart cart = new ShoppingCart((Customer) user);
              System.out.println("choose an option!");
              System.out.println("1 - List Current Items in Cart");
              System.out.println("2 - Add a quantity of an item to the cart");
              System.out.println("3 - Check total price of items in the cart");
              System.out.println("4 - Remove a quantity of an item from the cart");
              System.out.println("5 - check out");
              System.out.println("6 - Exit");

              input = bufferedReader.readLine();

              while (!input.equals("6")) {
                System.out.println("choose an option!");
                System.out.println("1 - List Current Items in Cart");
                System.out.println("2 - Add a quantity of an item to the cart");
                System.out.println("3 - Check total price of items in the cart");
                System.out.println("4 - Remove a quantity of an item from the cart");
                System.out.println("5 - check out");
                System.out.println("6 - Exit");

                input = bufferedReader.readLine();
              }

            } else {
              System.out.println("login failed!");
              break;
            }

          }

        }



        System.out.println("What is your input?");
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
