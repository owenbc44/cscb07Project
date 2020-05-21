package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.accounts.CustomerAccountImpl;
import com.b07.database.DatabaseSelector;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLogImpl;
import com.b07.store.ShoppingCart;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;
import com.b07.users.UserImpl;


/**
 * @author Asad Ali Kazim
 *
 */
public class DatabaseSelectHelper extends DatabaseSelector {

  /**
   * Use this to get all the role ids from the Roles able as a list.
   * 
   * @return list of the role ids.
   * @throws SQLException if the query goes wrong.
   */
  public static List<Integer> getRoleIds() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getRoles(connection);
    List<Integer> ids = new ArrayList<>();
    while (results.next()) {
      // System.out.println(results.getInt("ROLEID"));
      ids.add(results.getInt("ID"));
    }
    results.close();
    connection.close();
    return ids;
  }

  /**
   * Use this to get the role with the id roleId.
   * 
   * @param roleId the id of the role.
   * @return a string containing the role.
   * @throws SQLException if something goes wrong with the query.
   */
  public static String getRoleName(int roleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String role = DatabaseSelector.getRole(roleId, connection);
    connection.close();
    return role;
  }

  /**
   * Use this to get the roleId of the user with id userId.
   * 
   * @param userId the id of the user.
   * @return the roleId for the user
   * @throws SQLException if something goes wrong with the query
   */
  public static int getUserRoleId(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseSelector.getUserRole(userId, connection);
    connection.close();
    return roleId;
  }

  /**
   * Use this to get the UserId of all the users in UserRole table with RoleId roleId.
   * 
   * @param roleId the id of the role.
   * @return list of UserId's with RoleId roleId.
   * @throws SQLException if something goes wrong with the query.
   */
  public static List<Integer> getUsersByRole(int roleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUsersByRole(roleId, connection);
    List<Integer> userIds = new ArrayList<>();
    while (results.next()) {
      userIds.add(results.getInt("USERID"));
    }
    results.close();
    connection.close();
    return userIds;

  }

  /**
   * Use this to get a list of all the users in the User table.
   * 
   * @return a list of users.
   * @throws SQLException if something goes wrong with the query.
   */
  public static List<User> getUsersDetails() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUsersDetails(connection);
    List<User> users = new ArrayList<User>();
    while (results.next()) {
      User user = new UserImpl(0, null, 0, null);
      user.setId(results.getInt("ID"));
      user.setName(results.getString("NAME"));
      user.setAge(results.getInt("AGE"));
      user.setAddress(results.getString("ADDRESS"));
      users.add(user);
    }
    results.close();
    connection.close();
    return users;
  }

  /**
   * Use this to get user with id userId from Users table.
   * 
   * @param userId the id of the user.
   * @return the user with id userId, null if no user with id userId found.
   * @throws SQLException if something goes wrong with the query.
   */
  public static User getUserDetails(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    User user = new UserImpl(0, null, 0, null);
    while (results.next()) {
      user.setId(results.getInt("ID"));
      user.setName(results.getString("NAME"));
      user.setAge(results.getInt("AGE"));
      user.setAddress(results.getString("ADDRESS"));
    }
    results.close();
    connection.close();
    if (user.getId() == 0 && user.getName() == null && user.getAge() == 0
        && user.getAddress() == null) {
      return null;
    }
    return user;
  }

  /**
   * Use this to get admin with id adminId from Users table.
   * 
   * @param adminId the id of the admin.
   * @return the admin with id adminId, null if no admin with id adminId found.
   * @throws SQLException if something goes wrong with the query.
   */
  public static Admin getAdminDetails(int adminId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(adminId, connection);
    Admin admin = new Admin(0, null, 0, null);
    while (results.next()) {
      admin.setId(results.getInt("ID"));
      admin.setName(results.getString("NAME"));
      admin.setAge(results.getInt("AGE"));
      admin.setAddress(results.getString("ADDRESS"));
    }
    results.close();
    connection.close();
    if (admin.getId() == 0 && admin.getName() == null && admin.getAge() == 0
        && admin.getAddress() == null) {
      return null;
    }
    return admin;
  }

  /**
   * Use this to get employee with id employeeId from Users table.
   * 
   * @param employeeId the id of the employee.
   * @return the employee with id employeeId, null if no employee with id employeeId found.
   * @throws SQLException if something goes wrong with the query.
   */
  public static Employee getEmployeeDetails(int employeeId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(employeeId, connection);
    Employee employee = new Employee(0, null, 0, null);
    while (results.next()) {
      employee.setId(results.getInt("ID"));
      employee.setName(results.getString("NAME"));
      employee.setAge(results.getInt("AGE"));
      employee.setAddress(results.getString("ADDRESS"));
    }
    results.close();
    connection.close();
    if (employee.getId() == 0 && employee.getName() == null && employee.getAge() == 0
        && employee.getAddress() == null) {
      return null;
    }
    return employee;
  }

  /**
   * Use this to get customer with id customerId from Users table.
   * 
   * @param customerId the id of the customer.
   * @return the customer with id customerId, null if no customer with id customerId found.
   * @throws SQLException if something goes wrong with the query.
   */
  public static Customer getCustomerDetails(int customerId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(customerId, connection);
    Customer customer = new Customer(0, null, 0, null);
    while (results.next()) {
      customer.setId(results.getInt("ID"));
      customer.setName(results.getString("NAME"));
      customer.setAge(results.getInt("AGE"));
      customer.setAddress(results.getString("ADDRESS"));
    }
    results.close();
    connection.close();
    if (customer.getId() == 0 && customer.getName() == null && customer.getAge() == 0
        && customer.getAddress() == null) {
      return null;
    }
    return customer;
  }



  /**
   * Use this to get the hashed password of the user with id userId from UserPW table.
   * 
   * @param userId the id of the user.
   * @return the hashed password of the user, null if no user with id userId found
   * @throws SQLException if something goes wrong with the query.
   */
  public static String getPassword(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    User user = getUserDetails(userId);
    if (user == null) {
      return null;
    }
    String password = DatabaseSelector.getPassword(userId, connection);
    connection.close();
    return password;
  }

  /**
   * Use this to get all items from the Items table as a list.
   * 
   * @return list of all items in the Items table.
   * @throws SQLException if something goes wrong with the query.
   */
  public static List<ItemImpl> getAllItems() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAllItems(connection);
    List<ItemImpl> items = new ArrayList<>();
    while (results.next()) {
      ItemImpl item = new ItemImpl(0, null, null);
      item.setId(results.getInt("ID"));
      item.setName(results.getString("NAME"));
      item.setPrice(new BigDecimal(results.getString("PRICE")));
      items.add(item);
    }
    results.close();
    connection.close();
    return items;
  }

  /**
   * Use this to get the details of an item with id itemId from Items table.
   * 
   * @param itemId the id of the item
   * @return the item with id itemId
   * @throws SQLException if something goes wrong with the query.
   */
  public static ItemImpl getItem(int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItem(itemId, connection);
    ItemImpl item = new ItemImpl(0, null, null);
    while (results.next()) {
      item.setId(results.getInt("ID"));
      item.setName(results.getString("NAME"));
      item.setPrice(new BigDecimal(results.getString("PRICE")));
    }
    results.close();
    connection.close();
    if (item.getId() == 0 && item.getName() == null && item.getPrice() == null) {
      return null;
    }
    return item;
  }

  /**
   * Use this to get all rows from the Inventory table.
   * 
   * @return all the rows of the inventory table.
   * @throws SQLException if something goes wrong with the query.
   */
  public static InventoryImpl getInventory() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getInventory(connection);
    InventoryImpl inventory = new InventoryImpl();
    while (results.next()) {
      ItemImpl item = getItem(results.getInt("ITEMID"));
      Integer quantity = (Integer) results.getInt("QUANTITY");
      inventory.updateMap(item, quantity);
      inventory.setTotalItems(inventory.getTotalItems() + quantity);
    }
    results.close();
    connection.close();
    return inventory;
  }

  /**
   * Use this to get the quantity of the item with id itemId left in the Inventory.
   * 
   * @param itemId the id of the item
   * @return quantity of the item in the inventory
   * @throws SQLException if something goes wrong with the query
   */
  public static int getInventoryQuantity(int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
    connection.close();
    return quantity;
  }

  /**
   * Use this to get a SalesLogImpl of all sales in the Sales table.
   * 
   * @return a sales log (an array list of all sales)
   * @throws SQLException if something goes wrong with the query.
   */
  public static SalesLogImpl getSales() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSales(connection);
    SalesLogImpl salesLog = new SalesLogImpl();
    SaleImpl sale = new SaleImpl();
    while (results.next()) {
      sale.setId(results.getInt("ID"));
      sale.setUser(getUserDetails(results.getInt("USERID")));
      sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
      salesLog.addSale(sale);
    }
    results.close();
    connection.close();
    return salesLog;
  }



  /**
   * Use this to get a sale with id saleId from the Sales table.
   * 
   * @param saleId the id of the sale.
   * @return the sale.
   * @throws SQLException if something goes wrong with the query.
   */
  public static SaleImpl getSaleById(int saleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSaleById(saleId, connection);
    SaleImpl sale = new SaleImpl();
    while (results.next()) {
      sale.setId(results.getInt("ID"));
      sale.setUser(getUserDetails(results.getInt("USERID")));
      sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
    }
    results.close();
    connection.close();
    return sale;
  }

  /**
   * Use this to get all the sales as a list for the user with id userId.
   * 
   * @param userId the id of the user.
   * @return the sales list for the user.
   * @throws SQLException if something goes wrong with the query.
   */
  public static List<SaleImpl> getSalesToUser(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelectHelper.getSalesToUser(userId, connection);
    List<SaleImpl> sales = new ArrayList<>();
    SaleImpl sale = new SaleImpl();
    while (results.next()) {
      sale.setId(results.getInt("ID"));
      sale.setUser(getUserDetails(results.getInt("USERID")));
      sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
      sales.add(sale);
    }
    results.close();
    connection.close();
    return sales;
  }

  /**
   * Use this to get a list of all sales in the itemized sales table with the id saleId.
   * 
   * @param saleId the id of the sale.
   * @return a list of sales.
   * @throws SQLException if something goes wrong with the query.
   */
  public static List<SaleImpl> getItemizedSaleById(int saleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
    List<SaleImpl> sales = new ArrayList<SaleImpl>();

    while (results.next()) {
      SaleImpl sale = new SaleImpl();
      sale.setId(results.getInt("SALEID"));
      HashMap<ItemImpl, Integer> itemMap = new HashMap<ItemImpl, Integer>();
      itemMap.put(getItem(results.getInt("ITEMID")), ((Integer) (results.getInt("QUANTITY"))));
      sale.setItemMap(itemMap);
      sales.add(sale);
    }
    results.close();
    connection.close();
    return sales;
  }

  /**
   * Use this to get a saleslog of all the sales in the itemized sales table.
   * 
   * @return the sales log of all the sales.
   * @throws SQLException
   */
  public static SalesLogImpl getItemizedSales() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItemizedSales(connection);
    SalesLogImpl salesLog = new SalesLogImpl();
    while (results.next()) {
      SaleImpl sale = new SaleImpl();
      sale.setId(results.getInt("SALEID"));
      HashMap<ItemImpl, Integer> itemMap = new HashMap<ItemImpl, Integer>();
      itemMap.put(getItem(results.getInt("ITEMID")), ((Integer) (results.getInt("QUANTITY"))));
      sale.setItemMap(itemMap);
      salesLog.addSale(sale);
    }
    results.close();
    connection.close();
    return salesLog;
  }

  /**
   * Use this to get a list of all the accounts in the accountId table related to userId.
   * 
   * @param userId Id of the user.
   * @return the sales log of all the sales.
   * @throws SQLException
   */
  public static List<CustomerAccountImpl> getUserAccounts(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserAccounts(userId, connection);
    List<CustomerAccountImpl> accounts = new ArrayList<CustomerAccountImpl>();

    while (results.next()) {
      CustomerAccountImpl account = new CustomerAccountImpl(null);

      account.setId(results.getInt("id"));

      User user = getUserDetails(userId);
      account.setUser(user);

      HashMap<ItemImpl, Integer> items = getAccountDetails(account.getId());

      ShoppingCart cart = new ShoppingCart((Customer) user);

      for (ItemImpl key : items.keySet()) {
        int quantity = items.get(key);
        cart.addItem(key, quantity);
      }

      accounts.add(account);
    }
    results.close();
    connection.close();
    return accounts;
  }

  /**
   * Use this to get a list of all the users in the User table.
   * 
   * @return a list of users.
   * @throws SQLException if something goes wrong with the query.
   */
  public static HashMap<ItemImpl, Integer> getAccountDetails(int accountId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);
    HashMap<ItemImpl, Integer> items = new HashMap<ItemImpl, Integer>();
    while (results.next()) {
      ItemImpl item = getItem(results.getInt("itemId"));
      items.put(item, results.getInt("quantity"));
    }
    results.close();
    connection.close();
    return items;
  }
}
