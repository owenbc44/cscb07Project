package com.b07.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.b07.accounts.CustomerAccountImpl;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InvalidAddressException;
import com.b07.exceptions.InvalidAgeException;
import com.b07.exceptions.InvalidItemException;
import com.b07.exceptions.InvalidPriceException;
import com.b07.exceptions.InvalidQuantityException;
import com.b07.exceptions.InvalidRoleException;
import com.b07.exceptions.ItemIdNotFoundException;
import com.b07.exceptions.RoleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.serialization.DatabaseInOneObject;
import com.b07.serialization.Serialization;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;

public class SalesApplication {
  /**
   * @param argv unused.
   */
  public static void main(String[] argv) {

    Connection connection = null;

    connection = DatabaseDriverExtender.connectOrCreateDataBase();

    if (connection == null) {
      System.out.print("Unable to connect to Database, make sure correct .jar has been referenced");
    } else {

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      String homePageOption = "";

      while (!homePageOption.equals("0")) {


        try {

          System.out.println("What do you want to do?");
          System.out.println(" -1 - Setup New Database with an Admin and Employee account");
          System.out.println("  1 - Admin Login");
          System.out.println("  0 - Exit App");
          System.out.println("o/w - More Options");

          homePageOption = bufferedReader.readLine();

          if (homePageOption.equals("-1")) {

            boolean alreadyInitialised = false;

            try {

              DatabaseDriverExtender.initialize(connection);

            } catch (ConnectionFailedException e) {
              System.out.println("Warning: Database already initialised");
              alreadyInitialised = true;
            } catch (Exception e) {
              System.out.println("Warning: Database already initialised");
              alreadyInitialised = true;
            }

            if (!alreadyInitialised) {

              try {
                // has item id 1
                DatabaseInsertHelper.insertItem("FISHING_ROD", new BigDecimal("10.00"));
                // has item id 2
                DatabaseInsertHelper.insertItem("HOCKEY_STICK", new BigDecimal("10.00"));
                // has item id 3
                DatabaseInsertHelper.insertItem("SKATES", new BigDecimal("10.00"));
                // has item id 4
                DatabaseInsertHelper.insertItem("RUNNING_SHOES", new BigDecimal("10.00"));
                // has item id 5
                DatabaseInsertHelper.insertItem("PROTEIN_BAR", new BigDecimal("10.00"));

                // initialise inventory with 100 of each item
                DatabaseInsertHelper.insertInventory(1, 100);
                DatabaseInsertHelper.insertInventory(2, 100);
                DatabaseInsertHelper.insertInventory(3, 100);
                DatabaseInsertHelper.insertInventory(4, 100);
                DatabaseInsertHelper.insertInventory(5, 100);

                DatabaseInsertHelper.insertRole("ADMIN"); // has role id 1
                DatabaseInsertHelper.insertRole("EMPLOYEE"); // has role id 2
                DatabaseInsertHelper.insertRole("CUSTOMER"); // has role id 3

              } catch (SQLException e) {
                System.out.println("Warning: SQL Exception occured");
              } catch (InvalidItemException e) {
                System.out.println("Warning: Invalid Item Exception occured");
              } catch (InvalidPriceException e) {
                System.out.println("Warning: Invalid Price Exception occured");
              } catch (DatabaseInsertException e) {
                System.out.println("Warning: Database Insert Exception occured");
              } catch (InvalidRoleException e) {
                System.out.println("Warning: Invalid Role Exception occured");
              } catch (ItemIdNotFoundException e) {
                System.out.println("Warning: Item ID Not Found Exception occured");
              } catch (InvalidQuantityException e) {
                System.out.println("Warning: Invalid Quantity Exception occured");
              } catch (Exception e) {
                System.out.println("Warning: An unexpected Exception occured. Please try again");
              }

              boolean errorWhileInitialisingAdmin = true;

              while (errorWhileInitialisingAdmin) {

                try {

                  System.out.println("What is the name of the admin?");
                  String adminName = bufferedReader.readLine();
                  System.out.println("What is the age of the admin?");
                  String adminAge = bufferedReader.readLine();
                  System.out.println("What is the address of the admin?");
                  String adminAddress = bufferedReader.readLine();
                  System.out.println("What is the password of the admin?");
                  String adminPassword = bufferedReader.readLine();

                  int adminId = DatabaseInsertHelper.insertNewUser(adminName,
                      Integer.valueOf(adminAge), adminAddress, adminPassword);
                  System.out.println("The admin user id is " + adminId);

                  int adminRoleId = 1;

                  DatabaseInsertHelper.insertUserRole(adminId, adminRoleId);

                  errorWhileInitialisingAdmin = false;

                } catch (IOException e) {
                  System.out.println("Warning: IO Exception occured. Please try again");
                } catch (NumberFormatException e) {
                  System.out.println("Warning: Number Format Exception occured. Please try again");
                } catch (InvalidAddressException e) {
                  System.out
                      .println("Warning: Invalid Address Exception occured. Please try again");
                } catch (InvalidAgeException e) {
                  System.out.println("Warning: Invalid Age Exception occured. Please try again");
                } catch (DatabaseInsertException e) {
                  System.out
                      .println("Warning: Database Insert Exception occured. Please try again");
                } catch (SQLException e) {
                  System.out.println("Warning: SQL Exception occured. Please try again");
                } catch (UserIdNotFoundException e) {
                  System.out
                      .println("Warning: User ID Not Found Exception occured. Please try again");
                } catch (RoleIdNotFoundException e) {
                  System.out
                      .println("Warning: Role ID Not Found Exception occured. Please try again");
                } catch (Exception e) {
                  System.out.println("Warning: An unexpected Exception occured. Please try again");
                }
              }

              boolean errorWhileInitialisingEmployee = true;

              while (errorWhileInitialisingEmployee) {

                try {

                  System.out.println("What is the name of the employee?");
                  String employeeName = bufferedReader.readLine();
                  System.out.println("What is the age of the employee?");
                  String employeeAge = bufferedReader.readLine();
                  System.out.println("What is the address of the employee?");
                  String employeeAddress = bufferedReader.readLine();
                  System.out.println("What is the password of the employee?");
                  String employeePassword = bufferedReader.readLine();

                  int emplyId = DatabaseInsertHelper.insertNewUser(employeeName,
                      Integer.valueOf(employeeAge), employeeAddress, employeePassword);
                  System.out.println("The employee user id is " + emplyId);

                  int emplyRoleId = 2;

                  DatabaseInsertHelper.insertUserRole(emplyId, emplyRoleId);

                  errorWhileInitialisingEmployee = false;

                } catch (IOException e) {
                  System.out.println("Warning: IO Exception occured. Please try again");
                } catch (NumberFormatException e) {
                  System.out.println("Warning: Number Format Exception occured. Please try again");
                } catch (InvalidAddressException e) {
                  System.out
                      .println("Warning: Invalid Address Exception occured. Please try again");
                } catch (InvalidAgeException e) {
                  System.out.println("Warning: Invalid Age Exception occured. Please try again");
                } catch (DatabaseInsertException e) {
                  System.out
                      .println("Warning: Database Insert Exception occured. Please try again");
                } catch (SQLException e) {
                  System.out.println("Warning: SQL Exception occured. Please try again");
                } catch (UserIdNotFoundException e) {
                  System.out
                      .println("Warning: User ID Not Found Exception occured. Please try again");
                } catch (RoleIdNotFoundException e) {
                  System.out
                      .println("Warning: Role ID Not Found Exception occured. Please try again");
                } catch (Exception e) {
                  System.out.println("Warning: An unexpected Exception occured. Please try again");
                }
              }
            }

          } else if (homePageOption.equals("1")) {
            try {

              System.out.println("Admin sign in time!");
              System.out.println("ID     NAME");
              List<Integer> adminList = DatabaseSelectHelper.getUsersByRole(1);
              for (int i = 0; i < adminList.size(); i++) {
                System.out.println(adminList.get(i) + "      "
                    + DatabaseSelectHelper.getAdminDetails(adminList.get(i)).getName());
              }

              System.out.println("What is your user id?");
              String adminId = bufferedReader.readLine();

              if (adminList.contains(Integer.valueOf(adminId))) {
                System.out.println("What is your password?");
                String adminPassword = bufferedReader.readLine();

                Admin admin = DatabaseSelectHelper.getAdminDetails(Integer.valueOf(adminId));
                boolean login = admin.authenticate(adminPassword);

                if (login) {

                  System.out.println("Logged in successfully as " + admin.getName());
                  String adminPageOption = "";

                  while (!(adminPageOption.equals("0") || adminPageOption.equals("9"))) {

                    try {


                      System.out.println("What do you want to do?");
                      System.out.println("  1 - Promote an Employee");
                      System.out.println("  2 - View Books");
                      System.out.println("  3 - View Inventory");
                      System.out.println("  4 - View Customer Accounts");
                      System.out.println("  5 - View Active Customer Accounts");
                      System.out.println("  6 - View Inactive Customer Accounts");
                      System.out.println("  7 - Serialize Database");
                      System.out.println("  8 - Reinitialise Database");
                      System.out.println("  9 - Exit to Home Menu");
                      System.out.println("  0 - Exit App");

                      adminPageOption = bufferedReader.readLine();

                      if (adminPageOption.equals("1")) {

                        System.out.println("ID     NAME");
                        List<Integer> employeeList = DatabaseSelectHelper.getUsersByRole(2);
                        for (int i = 0; i < employeeList.size(); i++) {
                          System.out.println(employeeList.get(i) + "      " + DatabaseSelectHelper
                              .getEmployeeDetails(employeeList.get(i)).getName());
                        }

                        if (employeeList.size() != 1) {

                          System.out.println("UserId of the employee you want to promote please!");
                          String employeeId = bufferedReader.readLine();

                          if (employeeList.contains(Integer.valueOf(employeeId))) {

                            Employee employeeToPromote = DatabaseSelectHelper
                                .getEmployeeDetails(Integer.valueOf(employeeId));
                            boolean promotionSuccess = admin.promoteEmployee(employeeToPromote);

                            if (promotionSuccess) {
                              System.out.println("Employee " + employeeToPromote.getName()
                                  + " promoted successfully!");
                            } else {
                              System.out.println("Promotion failed!");
                            }

                          } else {
                            System.out.println("That is not an Employee! Please try again");
                          }

                        } else {

                          System.out.println(
                              "You only have one employee left. Hire more before promoting them!");

                        }

                      } else if (adminPageOption.equals("2")) {

                        admin.viewBooks();

                      } else if (adminPageOption.equals("3")) {

                        InventoryImpl inventory = DatabaseSelectHelper.getInventory();
                        HashMap<ItemImpl, Integer> itemMap = inventory.getItemMap();

                        if (itemMap.isEmpty()) {
                          System.out.println("Your inventory is empty");
                        } else {
                          System.out.println("Item : Quantity");
                          for (ItemImpl key : itemMap.keySet()) {
                            System.out.println(key.getName() + " : " + itemMap.get(key));
                          }
                        }

                      } else if (adminPageOption.equals("4")) {

                        List<Integer> customerList = DatabaseSelectHelper.getUsersByRole(3);

                        if (customerList.size() == 0) {
                          System.out.println("You do not have any customers");
                        } else {
                          for (int i = 0; i < customerList.size(); i++) {
                            Customer customer =
                                DatabaseSelectHelper.getCustomerDetails(customerList.get(i));
                            List<CustomerAccountImpl> customerAccounts =
                                DatabaseSelectHelper.getCustomerAccounts(customerList.get(i));

                            if (customerAccounts.size() == 0) {
                              System.out
                                  .println("Customer " + customer.getName() + " has no accounts");
                            } else {
                              System.out.println(
                                  "Customer " + customer.getName() + " has the following accounts");
                              for (int j = 0; j < customerAccounts.size(); j++) {
                                CustomerAccountImpl customerAccount = customerAccounts.get(j);
                                System.out.println(customerAccount.getId());
                              }
                            }

                          }
                        }

                      } else if (adminPageOption.equals("5")) {

                        List<Integer> customerList = DatabaseSelectHelper.getUsersByRole(3);

                        if (customerList.size() == 0) {
                          System.out.println("You do not have any customers");
                        } else {
                          for (int i = 0; i < customerList.size(); i++) {
                            Customer customer =
                                DatabaseSelectHelper.getCustomerDetails(customerList.get(i));
                            List<CustomerAccountImpl> customerActiveAccounts =
                                DatabaseSelectHelper.getCustomerActiveAccounts(customerList.get(i));

                            if (customerActiveAccounts.size() == 0) {
                              System.out.println(
                                  "Customer " + customer.getName() + " has no active accounts");
                            } else {
                              System.out.println(
                                  "Customer " + customer.getName() + " has the following accounts");
                              for (int j = 0; j < customerActiveAccounts.size(); j++) {
                                CustomerAccountImpl customerAccount = customerActiveAccounts.get(j);
                                System.out.println(customerAccount.getId());
                              }
                            }

                          }
                        }

                      } else if (adminPageOption.equals("6")) {

                        List<Integer> customerList = DatabaseSelectHelper.getUsersByRole(3);

                        if (customerList.size() == 0) {
                          System.out.println("You do not have any customers");
                        } else {
                          for (int i = 0; i < customerList.size(); i++) {
                            Customer customer =
                                DatabaseSelectHelper.getCustomerDetails(customerList.get(i));
                            List<CustomerAccountImpl> customerInactiveAccounts =
                                DatabaseSelectHelper
                                    .getCustomerInactiveAccounts(customerList.get(i));

                            if (customerInactiveAccounts.size() == 0) {
                              System.out.println(
                                  "Customer " + customer.getName() + " has no inactive accounts");
                            } else {
                              System.out.println("Customer " + customer.getName()
                                  + " has the following inactive accounts");
                              for (int j = 0; j < customerInactiveAccounts.size(); j++) {
                                CustomerAccountImpl customerAccount =
                                    customerInactiveAccounts.get(j);
                                System.out.println(customerAccount.getId());
                              }
                            }

                          }
                        }

                      } else if (adminPageOption.equals("7")) {

                        DatabaseInOneObject theEntireDataBase = new DatabaseInOneObject();
                        Serialization.serialize(theEntireDataBase);

                      } else if (adminPageOption.equals("8")) {

                        // reinitialise database

                      } else if (adminPageOption.equals("0")) {

                        homePageOption = "0";

                      }

                    } catch (IOException e) {
                      System.out.println("Warning: IO Exception occured. Please try again");
                    } catch (NumberFormatException e) {
                      System.out
                          .println("Warning: Number Format Exception occured. Please try again");
                    } catch (SQLException e) {
                      System.out.println("Warning: SQL Exception occured. Please try again");
                    } catch (Exception e) {
                      System.out
                          .println("Warning: An unexpected Exception occured. Please try again");
                    }
                  }

                } else {
                  System.out.println("Login failed! Please try again");
                }

              } else {
                System.out.println("That is not an Admin! Please try again.");
              }

            } catch (IOException e) {
              System.out.println("Warning: IO Exception occured. Please try again");
            } catch (NumberFormatException e) {
              System.out.println("Warning: Number Format Exception occured. Please try again");
            } catch (SQLException e) {
              System.out.println("Warning: SQL Exception occured. Please try again");
            } catch (Exception e) {
              System.out.println("Warning: An unexpected Exception occured. Please try again");
            }

          } else if (!homePageOption.equals("0")) {

            try {
              System.out.println("What do you want to do?");
              System.out.println("  1 - Employee Login");
              System.out.println("  2 - Customer Login");
              System.out.println("  0 - Exit App");
              System.out.println("o/w - Exit to Home Menu");

              String optionPageOption = bufferedReader.readLine();

              if (optionPageOption.equals("1")) {

                try {

                  System.out.println("Employee sign in time!");
                  System.out.println("ID     NAME");
                  List<Integer> employeeList = DatabaseSelectHelper.getUsersByRole(2);
                  for (int i = 0; i < employeeList.size(); i++) {
                    System.out.println(employeeList.get(i) + "      "
                        + DatabaseSelectHelper.getEmployeeDetails(employeeList.get(i)).getName());
                  }

                  System.out.println("What is your user id?");
                  String employeeId = bufferedReader.readLine();

                  if (employeeList.contains(Integer.valueOf(employeeId))) {

                    System.out.println("What is your password?");
                    String employeePassword = bufferedReader.readLine();

                    Employee employee =
                        DatabaseSelectHelper.getEmployeeDetails(Integer.valueOf(employeeId));
                    boolean login = employee.authenticate(employeePassword);

                    if (login) {
                      System.out.println("Logged in successfully as " + employee.getName());
                      InventoryImpl inventory = DatabaseSelectHelper.getInventory();
                      EmployeeInterface empInt = new EmployeeInterface(employee, inventory);

                      String employeePageOption = "";

                      while (!(employeePageOption.equals("7") || employeePageOption.equals("0"))) {

                        try {

                          System.out.println("What do you want to do?");
                          System.out.println("1 - Authenticate New Employee");
                          System.out.println("2 - Make New Account");
                          System.out.println("3 - Make New Employee");
                          System.out.println("4 - Make New Customer");
                          System.out.println("5 - Restock Inventory");
                          System.out.println("6 - Shop on a Customers Behalf");
                          System.out.println("7 - Exit to Home Menu");
                          System.out.println("0 - Exit App");

                          employeePageOption = bufferedReader.readLine();

                          if (employeePageOption.equals("1")) {

                            System.out.println("ID     NAME");
                            List<Integer> employeeList1 = DatabaseSelectHelper.getUsersByRole(2);
                            for (int i = 0; i < employeeList1.size(); i++) {
                              System.out
                                  .println(employeeList1.get(i) + "      " + DatabaseSelectHelper
                                      .getEmployeeDetails(employeeList1.get(i)).getName());
                            }

                            System.out.println("UserId of employee to authenticate please");
                            String employeeIdToAuthenticate = bufferedReader.readLine();

                            if (employeeList1.contains(Integer.valueOf(employeeIdToAuthenticate))) {

                              System.out.println("Employee password please");
                              String employeePasswordToAuthenticate = bufferedReader.readLine();

                              Employee employeeToAuthenticate = DatabaseSelectHelper
                                  .getEmployeeDetails(Integer.valueOf(employeeIdToAuthenticate));

                              boolean authenticated = empInt.authenticateEmployee(
                                  employeeToAuthenticate, employeePasswordToAuthenticate);

                              if (authenticated) {
                                System.out.println("Employee " + employeeToAuthenticate.getName()
                                    + " authenticated successfully!");
                              } else {
                                System.out
                                    .println("Employee authentication failed. Please try again");
                              }

                            }
                          } else if (employeePageOption.equals("2")) {

                            System.out.println("ID     NAME");
                            List<Integer> customerList = DatabaseSelectHelper.getUsersByRole(3);
                            for (int i = 0; i < customerList.size(); i++) {
                              System.out
                                  .println(customerList.get(i) + "      " + DatabaseSelectHelper
                                      .getCustomerDetails(customerList.get(i)).getName());
                            }

                            System.out
                                .println("The ID of the Customer you want to make an Account for?");
                            String customerId = bufferedReader.readLine();

                            if (customerList.contains(Integer.valueOf(customerId))) {

                              Customer customer = DatabaseSelectHelper
                                  .getCustomerDetails(Integer.valueOf(customerId));

                              int accountId = empInt.createCustomerAccount(customer);
                              System.out.println("Account created for " + customer.getName()
                                  + "! Account ID is " + String.valueOf(accountId));

                            } else {
                              System.out.println("That is not a Customer! Please try again");
                            }

                          } else if (employeePageOption.equals("3")) {

                            System.out.println("What is the name of the employee?");
                            String employeeName = bufferedReader.readLine();
                            System.out.println("What is the age of the employee?");
                            String employeeAge = bufferedReader.readLine();
                            System.out.println("What is the address of the employee?");
                            String employeeAddress = bufferedReader.readLine();
                            System.out.println("What is the password of the employee?");
                            String employeePassword1 = bufferedReader.readLine();

                            int employeeUserId = empInt.createEmployee(employeeName,
                                Integer.valueOf(employeeAge), employeeAddress, employeePassword1);
                            System.out.println("The employee user id is " + employeeUserId);

                          } else if (employeePageOption.equals("4")) {

                            System.out.println("What is the name of the customer?");
                            String customerName = bufferedReader.readLine();
                            System.out.println("What is the age of the customer?");
                            String customerAge = bufferedReader.readLine();
                            System.out.println("What is the address of the customer?");
                            String customerAddress = bufferedReader.readLine();
                            System.out.println("What is the password of the customer?");
                            String customerPassword = bufferedReader.readLine();

                            int customerUserId = empInt.createCustomer(customerName,
                                Integer.valueOf(customerAge), customerAddress, customerPassword);
                            System.out.println("The customer user id is " + customerUserId);

                          } else if (employeePageOption.equals("5")) {

                            System.out.println("ID     NAME");
                            System.out.println("1      FISHING_ROD");
                            System.out.println("2      HOCKEY_STICK");
                            System.out.println("3      SKATES");
                            System.out.println("4      RUNNING_SHOES");
                            System.out.println("5      PROTEIN_BAR");


                            System.out.println("What is the Id of the item you want to restock?");
                            String itemId = bufferedReader.readLine();
                            System.out.println("What is the new quantity of the item?");
                            String itemQuantity = bufferedReader.readLine();

                            ItemImpl item = DatabaseSelectHelper.getItem(Integer.valueOf(itemId));
                            empInt.restockInventory(item, Integer.valueOf(itemQuantity));


                            System.out.println("Restock successful!");


                          } else if (employeePageOption.equals("6")) {
                            // shop on behalf of customer

                            List<Integer> customerList = DatabaseSelectHelper.getUsersByRole(3);

                            if (customerList.size() == 0) {
                              System.out.println("You do not have any customers");
                            } else {

                              System.out.println("ID     NAME");
                              for (int i = 0; i < customerList.size(); i++) {
                                System.out
                                    .println(customerList.get(i) + "      " + DatabaseSelectHelper
                                        .getCustomerDetails(customerList.get(i)).getName());
                              }

                              System.out.println("What is the user id of the customer?");
                              String customerId = bufferedReader.readLine();

                              if (customerList.contains(Integer.valueOf(customerId))) {

                                Customer customer = DatabaseSelectHelper
                                    .getCustomerDetails(Integer.valueOf(customerId));
                                ShoppingCart cart = new ShoppingCart(customer);
                                boolean gotAccount = false;
                                int gotThisAccount = 0;

                                String employeeCartOption = "";

                                while (!(employeeCartOption.equals("9")
                                    || employeeCartOption.equals("0"))) {

                                  try {

                                    System.out.println("Shopping for " + customer.getName());
                                    System.out.println("What do you want to do?");
                                    System.out.println("1 - List Current Items in Cart");
                                    System.out.println("2 - Add a Quantity of an Item to the Cart");
                                    System.out
                                        .println("3 - Check Total Price of Items in the Cart");
                                    System.out
                                        .println("4 - Remove a Quantity of an Item from the Cart");
                                    System.out.println("5 - Check Out");
                                    System.out
                                        .println("6 - Store Current Shopping Cart in an Account");
                                    System.out.println("7 - Retrieve Cart from an Account");
                                    System.out.println("8 - View Current Customer Details");
                                    System.out.println("9 - Exit to Previous Menu");
                                    System.out.println("0 - Exit App");

                                    employeeCartOption = bufferedReader.readLine();

                                    if (employeeCartOption.equals("1")) {

                                      HashMap<ItemImpl, Integer> itemMap = cart.getItemMap();

                                      if (itemMap.isEmpty()) {
                                        System.out.println("Your cart is empty");
                                      } else {
                                        System.out.println("Item         Quantity");
                                        for (ItemImpl key : itemMap.keySet()) {
                                          System.out.println(
                                              key.getName() + "             " + itemMap.get(key));
                                        }
                                      }

                                    } else if (employeeCartOption.equals("2")) {

                                      ArrayList<String> itemIds = new ArrayList<String>(
                                          Arrays.asList("1", "2", "3", "4", "5"));

                                      System.out.println("ID     NAME");
                                      System.out.println("1      FISHING_ROD");
                                      System.out.println("2      HOCKEY_STICK");
                                      System.out.println("3      SKATES");
                                      System.out.println("4      RUNNING_SHOES");
                                      System.out.println("5      PROTEIN_BAR");

                                      System.out.println(
                                          "What is the id of the item you want to add to cart?");
                                      String itemId = bufferedReader.readLine();
                                      System.out
                                          .println("What is the quantity you want to add to cart?");
                                      String itemQuantity = bufferedReader.readLine();

                                      if (itemIds.contains(itemId)) {
                                        ItemImpl item =
                                            DatabaseSelectHelper.getItem(Integer.valueOf(itemId));
                                        cart.addItem(item, Integer.valueOf(itemQuantity));
                                        System.out.println(itemQuantity + " of " + item.getName()
                                            + " added to cart");
                                      } else {
                                        System.out.println(
                                            "That is not a valid item id. Please try again");
                                      }



                                    } else if (employeeCartOption.equals("3")) {

                                      BigDecimal total = cart.getTotal();
                                      System.out
                                          .println("Your current total is " + total.toString());

                                    } else if (employeeCartOption.equals("4")) {

                                      ArrayList<String> itemIds = new ArrayList<String>(
                                          Arrays.asList("1", "2", "3", "4", "5"));

                                      System.out.println("ID     NAME");
                                      System.out.println("1      FISHING_ROD");
                                      System.out.println("2      HOCKEY_STICK");
                                      System.out.println("3      SKATES");
                                      System.out.println("4      RUNNING_SHOES");
                                      System.out.println("5      PROTEIN_BAR");

                                      System.out.println(
                                          "What is the id of the item you want to remove from cart?");
                                      String itemId = bufferedReader.readLine();
                                      System.out.println(
                                          "What is the quantity you want to remove from cart?");
                                      String itemQuantity = bufferedReader.readLine();

                                      if (itemIds.contains(itemId)) {
                                        ItemImpl item =
                                            DatabaseSelectHelper.getItem(Integer.valueOf(itemId));

                                        boolean removal =
                                            cart.removeItem(item, Integer.valueOf(itemQuantity));

                                        if (removal) {
                                          if (cart.containsItem(item)) {
                                            System.out.println(itemQuantity + " of "
                                                + item.getName() + " removed from cart");
                                          } else {
                                            System.out.println("Item completely removed from cart");
                                          }
                                        } else {
                                          System.out.println("This item is not in your cart");
                                        }

                                      } else {
                                        System.out.println(
                                            "That is not a valid item id. Please try again");
                                      }

                                    } else if (employeeCartOption.equals("5")) {

                                      boolean checkOut = cart.checkOut();

                                      if (checkOut) {
                                        System.out.println("Check out successful!");
                                        if (gotAccount) {
                                          DatabaseUpdateHelper.updateAccountStatus(gotThisAccount,
                                              false);
                                        }
                                      } else {
                                        System.out.println("Check out unsuccessful");

                                        for (ItemImpl key : cart.getItemMap().keySet()) {
                                          int quantity = 0;
                                          try {
                                            quantity = DatabaseSelectHelper
                                                .getInventoryQuantity(key.getId());
                                          } catch (SQLException e) {
                                          }
                                          if (quantity < cart.getItemMap().get(key)) {
                                            System.out.println("Sorry! We do not have enough "
                                                + key.getName() + " in stock");
                                          }
                                        }
                                      }

                                    } else if (employeeCartOption.equals("6")) {

                                      List<CustomerAccountImpl> customerAccounts =
                                          DatabaseSelectHelper
                                              .getCustomerActiveAccounts(customer.getId());
                                      int numberOfAccounts = customerAccounts.size();

                                      if (numberOfAccounts == 0) {
                                        System.out.println(
                                            "No active accounts found for customer. Would you like to make one? [Y/N]");
                                        String yesOrNo = bufferedReader.readLine();

                                        if (yesOrNo.equals("Y")) {

                                          int accountId = empInt.createCustomerAccount(customer);
                                          System.out.println(
                                              "Account created! Account ID is " + accountId);

                                          customerAccounts = DatabaseSelectHelper
                                              .getCustomerActiveAccounts(customer.getId());
                                          numberOfAccounts = customerAccounts.size();
                                        }
                                      }

                                      if (numberOfAccounts != 0) {
                                        System.out
                                            .println(" Account ID's for " + customer.getName());
                                        for (int i = 0; i < customerAccounts.size(); i++) {
                                          System.out.println(customerAccounts.get(i).getId());
                                        }

                                        System.out.println(
                                            "The id for the account you want to store the shopping cart in?");
                                        String accountId = bufferedReader.readLine();

                                        boolean accountFound = false;

                                        for (int i = 0; i < numberOfAccounts; i++) {
                                          if (customerAccounts.get(i).getId() == Integer
                                              .valueOf(accountId)) {

                                            System.out.println(
                                                "Account found! Storing current shopping cart in account");
                                            accountFound = true;

                                            HashMap<ItemImpl, Integer> itemMap = cart.getItemMap();

                                            for (ItemImpl key : itemMap.keySet()) {
                                              DatabaseInsertHelper.insertAccountLine(
                                                  Integer.valueOf(accountId), key.getId(),
                                                  itemMap.get(key));
                                            }
                                          }
                                        }

                                        if (!accountFound) {
                                          System.out.println("Account not found. Please try again");
                                        }

                                      } else {
                                        System.out.println(
                                            "No accounts found for customer. Please try later");
                                      }

                                    } else if (employeeCartOption.equals("7")) {

                                      List<CustomerAccountImpl> customerAccounts =
                                          DatabaseSelectHelper
                                              .getCustomerActiveAccounts(customer.getId());
                                      int numberOfAccounts = customerAccounts.size();

                                      if (numberOfAccounts != 0) {
                                        System.out
                                            .println(" Account ID's for " + customer.getName());
                                        for (int i = 0; i < customerAccounts.size(); i++) {
                                          System.out.println(customerAccounts.get(i).getId());
                                        }

                                        CustomerAccountImpl account;
                                        System.out.println(
                                            "The id for the account you want to retrieve the shopping cart from?");
                                        String accountId = bufferedReader.readLine();

                                        boolean accountFound = false;

                                        for (int i = 0; i < numberOfAccounts; i++) {
                                          if (customerAccounts.get(i).getId() == Integer
                                              .valueOf(accountId)) {

                                            System.out.println(
                                                "Account found! Setting current shopping cart as the one in the account");
                                            accountFound = true;
                                            gotAccount = true;
                                            gotThisAccount = customerAccounts.get(i).getId();

                                            cart.clearCart();
                                            account = customerAccounts.get(i);
                                            HashMap<ItemImpl, Integer> itemMap =
                                                account.getCart().getItemMap();

                                            for (ItemImpl key : itemMap.keySet()) {
                                              cart.addItem(key, itemMap.get(key));
                                            }
                                          }
                                        }

                                        if (!accountFound) {
                                          System.out.println("Account not found. Please try again");
                                        }
                                      } else {
                                        System.out.println(
                                            "No accounts found for customer. Would you like to make one? [Y/N]");
                                        String yesOrNo = bufferedReader.readLine();

                                        if (yesOrNo.equals("Y")) {

                                          int accountIdNew = empInt.createCustomerAccount(customer);
                                          System.out.println("Account created! Account ID is "
                                              + String.valueOf(accountIdNew));
                                        }
                                      }

                                    } else if (employeeCartOption.equals("8")) {

                                      System.out
                                          .println("The current customer you are shopping for is:");
                                      System.out.println("Name: " + customer.getName());
                                      System.out.println("Age: " + customer.getAge());
                                      System.out.println("Address: " + customer.getAddress());
                                      System.out.println("ID: " + customer.getId());

                                    } else if (employeeCartOption.equals("0")) {
                                      employeePageOption = "0";
                                      homePageOption = "0";
                                    }

                                  } catch (IOException e) {
                                    System.out
                                        .println("Warning: IO Exception occured. Please try again");
                                  } catch (ItemIdNotFoundException e) {
                                    System.out.println(
                                        "Warning: Item ID Not Found Exception occured. Please try again");
                                  } catch (InvalidQuantityException e) {
                                    System.out.println(
                                        "Warning: Invalid Quantity Exception occured. Please try again");
                                  } catch (DatabaseInsertException e) {
                                    System.out.println(
                                        "Warning: Database Insert Exception occured. Please try again");
                                  } catch (NumberFormatException e) {
                                    System.out.println(
                                        "Warning: Number Format Exception occurred. Please try again");
                                  } catch (SQLException e) {
                                    System.out.println(
                                        "Warning: SQL Exception occurred. Please try again");
                                  } catch (UserIdNotFoundException e) {
                                    System.out.println(
                                        "Warning: User ID Not Found Exception occured. Please try again");
                                  } catch (Exception e) {
                                    System.out.println(
                                        "Warning: An unexpected Exception occured. Please try again");
                                  }
                                }

                              } else {
                                System.out.println("That is not a Customer! Please try again");
                              }


                            }


                          } else if (employeePageOption.equals("0")) {
                            homePageOption = "0";
                          }

                        } catch (IOException e) {
                          System.out.println("Warning: IO Exception occured. Please try again");
                        } catch (InvalidQuantityException e) {
                          System.out.println(
                              "Warning: Invalid Quantity Exception occured. Please try again");
                        } catch (InvalidAddressException e) {
                          System.out.println(
                              "Warning: Invalid Address Exception occured. Please try again");
                        } catch (InvalidAgeException e) {
                          System.out
                              .println("Warning: Invalid Age Exception occured. Please try again");
                        } catch (DatabaseInsertException e) {
                          System.out.println(
                              "Warning: Database Insert Exception occured. Please try again");
                        } catch (UserIdNotFoundException e) {
                          System.out.println(
                              "Warning: User ID Not Found Exception occured. Please try again");
                        } catch (RoleIdNotFoundException e) {
                          System.out.println(
                              "Warning: Role ID Not Found Exception occured. Please try again");
                        } catch (NumberFormatException e) {
                          System.out.println(
                              "Warning: Number Format Exception occurred. Please try again");
                        } catch (SQLException e) {
                          System.out.println("Warning: SQL Exception occured. Please try again");
                        } catch (Exception e) {
                          System.out.println(
                              "Warning: An unexpected Exception occured. Please try again");
                        }

                      }

                    } else {
                      System.out.println("Login failed! Please try again");
                    }

                  } else {
                    System.out.println("That is not an Employee! Please try again");
                  }

                } catch (IOException e) {
                  System.out.println("Warning: IO Exception occured. Please try again");
                } catch (NumberFormatException e) {
                  System.out.println("Warning: Number Format Exception occurred. Please try again");
                } catch (SQLException e) {
                  System.out.println("Warning: SQL Exception occured. Please try again");
                } catch (Exception e) {
                  System.out.println("Warning: An unexpected Exception occured. Please try again");
                }


              } else if (optionPageOption.equals("2")) {

                try {

                  List<Integer> customerList = DatabaseSelectHelper.getUsersByRole(3);

                  if (customerList.size() == 0) {
                    System.out.println("No customers have been created yet.");
                    System.out.println("Wait a minute... who ARE you?");
                  } else {

                    System.out.println("Customer sign in time!");
                    System.out.println("ID     NAME");

                    for (int i = 0; i < customerList.size(); i++) {
                      System.out.println(customerList.get(i) + "     "
                          + DatabaseSelectHelper.getCustomerDetails(customerList.get(i)).getName());
                    }

                    System.out.println("What is your user id?");
                    String customerId = bufferedReader.readLine();

                    if (customerList.contains(Integer.valueOf(customerId))) {

                      System.out.println("What is your password?");
                      String customerPassword = bufferedReader.readLine();

                      Customer customer =
                          DatabaseSelectHelper.getCustomerDetails(Integer.valueOf(customerId));
                      boolean login = customer.authenticate(customerPassword);

                      if (login) {
                        System.out.println("Logged in successfully as " + customer.getName());
                        ShoppingCart cart = new ShoppingCart(customer);
                        boolean gotAccount = false;
                        int gotThisAccount = 0;

                        String customerCartOption = "";

                        while (!(customerCartOption.equals("8")
                            || customerCartOption.equals("0"))) {

                          try {

                            System.out.println("What do you want to do?");
                            System.out.println("1 - List Current Items in Cart");
                            System.out.println("2 - Add a Quantity of an Item to the Cart");
                            System.out.println("3 - Check Total Price of Items in the Cart");
                            System.out.println("4 - Remove a Quantity of an Item from the Cart");
                            System.out.println("5 - Check Out");
                            System.out.println("6 - Store Current Shopping Cart in an Account");
                            System.out.println("7 - Retrieve Cart from an Account");
                            System.out.println("8 - Exit to Home Menu");
                            System.out.println("0 - Exit App");

                            customerCartOption = bufferedReader.readLine();

                            if (customerCartOption.equals("1")) {

                              HashMap<ItemImpl, Integer> itemMap = cart.getItemMap();
                              if (itemMap.isEmpty()) {
                                System.out.println("Your cart is empty");
                              } else {
                                System.out.println("Quantity    Item");
                                for (ItemImpl key : itemMap.keySet()) {
                                  System.out.println(itemMap.get(key) + "     " + key.getName());
                                }
                              }

                            } else if (customerCartOption.equals("2")) {

                              ArrayList<String> itemIds =
                                  new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5"));

                              System.out.println("ID     NAME");
                              System.out.println("1      FISHING_ROD");
                              System.out.println("2      HOCKEY_STICK");
                              System.out.println("3      SKATES");
                              System.out.println("4      RUNNING_SHOES");
                              System.out.println("5      PROTEIN_BAR");

                              System.out
                                  .println("What is the id of the item you want to add to cart?");
                              String itemId = bufferedReader.readLine();
                              System.out.println("What is the quantity you want to add to cart?");
                              String itemQuantity = bufferedReader.readLine();

                              if (itemIds.contains(itemId)) {
                                ItemImpl item =
                                    DatabaseSelectHelper.getItem(Integer.valueOf(itemId));
                                cart.addItem(item, Integer.valueOf(itemQuantity));
                                System.out.println(
                                    itemQuantity + " of " + item.getName() + " added to cart");
                              } else {
                                System.out.println("That is not a valid item id. Please try again");
                              }

                            } else if (customerCartOption.equals("3")) {

                              BigDecimal total = cart.getTotal();
                              System.out.println("Your current total is " + total.toString());

                            } else if (customerCartOption.equals("4")) {

                              ArrayList<String> itemIds =
                                  new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5"));

                              System.out.println("ID     NAME");
                              System.out.println("1      FISHING_ROD");
                              System.out.println("2      HOCKEY_STICK");
                              System.out.println("3      SKATES");
                              System.out.println("4      RUNNING_SHOES");
                              System.out.println("5      PROTEIN_BAR");

                              System.out.println(
                                  "What is the id of the item you want to remove from cart?");
                              String itemId = bufferedReader.readLine();
                              System.out
                                  .println("What is the quantity you want to remove from cart?");
                              String itemQuantity = bufferedReader.readLine();

                              if (itemIds.contains(itemId)) {
                                ItemImpl item =
                                    DatabaseSelectHelper.getItem(Integer.valueOf(itemId));

                                boolean removal =
                                    cart.removeItem(item, Integer.valueOf(itemQuantity));

                                if (removal) {
                                  if (cart.containsItem(item)) {
                                    System.out.println(itemQuantity + " of " + item.getName()
                                        + " removed from cart");
                                  } else {
                                    System.out.println("Item completely removed from cart");
                                  }
                                } else {
                                  System.out.println("This item is not in your cart");
                                }

                              } else {
                                System.out.println("That is not a valid item id. Please try again");
                              }


                            } else if (customerCartOption.equals("5")) {

                              boolean checkOut = cart.checkOut();

                              if (checkOut) {
                                System.out.println("Check out successful!");
                                if (gotAccount) {
                                  DatabaseUpdateHelper.updateAccountStatus(gotThisAccount, false);
                                }
                              } else {

                                System.out.println("Check out unsuccessful");
                                for (ItemImpl key : cart.getItemMap().keySet()) {

                                  int quantity = 0;
                                  try {
                                    quantity =
                                        DatabaseSelectHelper.getInventoryQuantity(key.getId());
                                  } catch (SQLException e) {
                                  }

                                  if (quantity < cart.getItemMap().get(key)) {
                                    System.out.println("Sorry! We do not have enough "
                                        + key.getName() + " in stock");
                                  }
                                }
                              }

                            } else if (customerCartOption.equals("6")) {

                              List<CustomerAccountImpl> customerAccounts =
                                  DatabaseSelectHelper.getCustomerActiveAccounts(customer.getId());
                              int numberOfAccounts = customerAccounts.size();

                              if (numberOfAccounts != 0) {
                                System.out.println("Active Account ID's for " + customer.getName());
                                for (int i = 0; i < numberOfAccounts; i++) {
                                  System.out.println(customerAccounts.get(i).getId());
                                }

                                System.out.println(
                                    "The id for the account you want to store the shopping cart in?");
                                String accountId = bufferedReader.readLine();

                                boolean accountFound = false;

                                for (int i = 0; i < numberOfAccounts; i++) {
                                  if (customerAccounts.get(i).getId() == Integer
                                      .valueOf(accountId)) {

                                    System.out.println(
                                        "Account found! Storing current shopping cart in account");
                                    accountFound = true;

                                    HashMap<ItemImpl, Integer> itemMap = cart.getItemMap();

                                    for (ItemImpl key : itemMap.keySet()) {
                                      DatabaseInsertHelper.insertAccountLine(
                                          Integer.valueOf(accountId), key.getId(),
                                          itemMap.get(key));
                                    }
                                  }
                                }

                                if (!accountFound) {
                                  System.out.println("Account not found. Please try again");
                                }


                              } else {
                                System.out.println("You do not have any active accounts");
                              }


                            } else if (customerCartOption.equals("7")) {

                              List<CustomerAccountImpl> customerAccounts = DatabaseSelectHelper
                                  .getCustomerActiveAccounts(Integer.valueOf(customerId));
                              int numberOfAccounts = customerAccounts.size();

                              if (numberOfAccounts != 0) {

                                System.out.println("Account ID's for " + customer.getName());
                                for (int i = 0; i < numberOfAccounts; i++) {
                                  System.out.println(customerAccounts.get(i).getId());
                                }

                                System.out.println(
                                    "The id for the account you want to retrieve the shopping cart from?");
                                String accountId = bufferedReader.readLine();

                                CustomerAccountImpl account;

                                boolean accountFound = false;

                                for (int i = 0; i < numberOfAccounts; i++) {
                                  if (customerAccounts.get(i).getId() == Integer
                                      .valueOf(accountId)) {

                                    System.out.println(
                                        "Account found! Setting current shopping cart as the one in the account");
                                    accountFound = true;
                                    gotAccount = true;
                                    gotThisAccount = customerAccounts.get(i).getId();

                                    cart.clearCart();
                                    account = customerAccounts.get(i);
                                    HashMap<ItemImpl, Integer> itemMap =
                                        account.getCart().getItemMap();

                                    for (ItemImpl key : itemMap.keySet()) {
                                      cart.addItem(key, itemMap.get(key));
                                    }
                                  }
                                }

                                if (!accountFound) {
                                  System.out.println("Account not found. Please try again");
                                }

                              } else {
                                System.out.println("You do not have any active accounts");
                              }



                            } else if (customerCartOption.equals("0")) {
                              homePageOption = "0";
                            }

                          } catch (IOException e) {
                            System.out.println("Warning: IO Exception occured. Please try again");
                          } catch (ItemIdNotFoundException e) {
                            System.out.println(
                                "Warning: Item ID Not Found Exception occured. Please try again");
                          } catch (InvalidQuantityException e) {
                            System.out.println(
                                "Warning: Invalid Quantity Exception occured. Please try again");
                          } catch (DatabaseInsertException e) {
                            System.out.println(
                                "Warning: Database Insert Exception occured. Please try again");
                          } catch (NumberFormatException e) {
                            System.out.println(
                                "Warning: Number Format Exception occurred. Please try again");
                          } catch (SQLException e) {
                            System.out.println("Warning: SQL Exception occurred. Please try again");
                          } catch (Exception e) {
                            System.out.println(
                                "Warning: An unexpected Exception occured. Please try again");
                          }
                        }

                      } else {
                        System.out.println("Login failed! Please try again");
                      }

                    } else {
                      System.out.println("That is not a customer! Please try again");
                    }
                  }

                } catch (IOException e) {
                  System.out.println("Warning: IO Exception occurred. Please try again");
                } catch (NumberFormatException e) {
                  System.out.println("Warning: Number Format Exception occurred. Please try again");
                } catch (SQLException e) {
                  System.out.println("Warning: SQL Exception occurred. Please try again");
                } catch (Exception e) {
                  System.out.println("Warning: An unexpected Exception occured. Please try again");
                }

              } else if (optionPageOption.equals("0")) {
                homePageOption = "0";
              }

            } catch (IOException e) {
              System.out.println("Warning: IO Exception occurred. Please try again");
            } catch (Exception e) {
              System.out.println("Warning: An unexpected Exception occured. Please try again");
            }
          }

        } catch (IOException e) {
          System.out.println("Warning: IO Exception occurred. Please try again");
        } catch (Exception e) {
          System.out.println("Warning: An unexpected Exception occured. Please try again");
        }

      }

      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }

    }


  }
}
