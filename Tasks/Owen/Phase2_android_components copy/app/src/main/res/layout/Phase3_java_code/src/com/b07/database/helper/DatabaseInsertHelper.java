package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.b07.database.DatabaseInserter;
import com.b07.enumerators.CompareStringToEnum;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InvalidAddressException;
import com.b07.exceptions.InvalidAgeException;
import com.b07.exceptions.InvalidItemException;
import com.b07.exceptions.InvalidPriceException;
import com.b07.exceptions.InvalidQuantityException;
import com.b07.exceptions.InvalidRoleException;
import com.b07.exceptions.ItemIdNotFoundException;
import com.b07.exceptions.RoleIdNotFoundException;
import com.b07.exceptions.SaleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.Item;
import com.b07.store.SaleImpl;
import com.b07.users.Customer;
import com.b07.users.User;


/**
 * @author Asad Ali Kazim
 *
 */
public class DatabaseInsertHelper extends DatabaseInserter {

  /**
   * Use this to add new roles to the Roles table.
   * 
   * @param name the new role to be added.
   * @return the id of the role that was inserted.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws InvalidRoleException if name is not present in the Roles enum.
   * @throws ClassNotFoundException
   */
  public static int insertRole(String name)
      throws DatabaseInsertException, SQLException, InvalidRoleException, ClassNotFoundException {
    if (!CompareStringToEnum.checkRole(name)) {
      throw new InvalidRoleException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseInserter.insertRole(name, connection);
    connection.close();
    return roleId;
  }

  /**
   * Use this to add new users to the User table.
   * 
   * @param name the users name.
   * @param age the users age.
   * @param address the users address (must not exceed 100 characters).
   * @param password the users password (not hashed).
   * @return the id of the user that was inserted.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws InvalidAddressException if the address exceeds 100 characters.
   * @throws InvalidAgeException if age is <= 0.
   * @throws ClassNotFoundException
   */
  public static int insertNewUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, InvalidAddressException, InvalidAgeException,
      ClassNotFoundException {
    if (address.length() > 100) {
      throw new InvalidAddressException();
    }
    if (age <= 0) {
      throw new InvalidAgeException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
    connection.close();
    return userId;
  }

  /**
   * Use this to insert a relationship between a user and a role.
   * 
   * @param userId the id of the user.
   * @param roleId the role id of the user.
   * @return the unique relationship id.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of connection.
   * @throws UserIdNotFoundException if userId is not in the User table.
   * @throws RoleIdNotFoundException if roleId is not in the Roles table.
   * @throws ClassNotFoundException
   */
  public static int insertUserRole(int userId, int roleId) throws DatabaseInsertException,
      SQLException, UserIdNotFoundException, RoleIdNotFoundException, ClassNotFoundException {

    User user = DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }

    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
    if (!roleIds.contains(roleId)) {
      throw new RoleIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
    connection.close();
    return userRoleId;
  }

  /**
   * Use this to insert an item in the Items table.
   * 
   * @param name the name of the item.
   * @param price the price of the item.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws InvalidItemException if name is null or exceeds 64 characters or is not present in the
   *         ItemType enum.
   * @throws InvalidPriceException if price is not greater than zero or does not have 2dp precision.
   * @throws ClassNotFoundException
   */
  public static int insertItem(String name, BigDecimal price) throws DatabaseInsertException,
      SQLException, InvalidItemException, InvalidPriceException, ClassNotFoundException {

    if (name == null || name.length() > 64 || !CompareStringToEnum.checkItemType(name)) {
      throw new InvalidItemException();
    }
    if (price.compareTo(new BigDecimal("0")) != 1 || price.scale() != 2) {
      throw new InvalidPriceException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int itemId = DatabaseInserter.insertItem(name, price, connection);
    connection.close();
    return itemId;
  }

  /**
   * Use this to insert items to the Inventory table.
   * 
   * @param itemId the id of the item.
   * @param quantity the quantity of the item (must be greater than 0).
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws ItemIdNotFoundException if itemId is not present in the Items table.
   * @throws InvalidQuantityException if quantity is less than 0.
   * @throws ClassNotFoundException
   */
  public static int insertInventory(int itemId, int quantity) throws DatabaseInsertException,
      SQLException, ItemIdNotFoundException, InvalidQuantityException, ClassNotFoundException {

    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }

    Item item = DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
    connection.close();
    return inventoryId;
  }

  /**
   * Use this to insert sales into the Sales table.
   * 
   * @param customerId the id of the user.
   * @param totalPrice the total price of the sale.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws UserIdNotFoundException if userId not in the User table.
   * @throws ClassNotFoundException
   */
  public static int insertSale(int customerId, BigDecimal totalPrice)
      throws DatabaseInsertException, SQLException, UserIdNotFoundException,
      ClassNotFoundException {

    Customer customer = DatabaseSelectHelper.getCustomerDetails(customerId);
    if (customer == null) {
      throw new UserIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int saleId = DatabaseInserter.insertSale(customerId, totalPrice, connection);
    connection.close();
    return saleId;
  }

  /**
   * Use this to insert an itemized record for a specific item in itemized sale table.
   * 
   * @param saleId id of the sale.
   * @param itemId id of the item.
   * @param quantity the number of item purchased
   * @return the id of the inserted record
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws ItemIdNotFoundException if itemId is not present in the Items table.
   * @throws SaleIdNotFoundException if saleId is not in the Sales table.
   * @throws InvalidQuantityException if quantity is less than 0.
   * @throws ClassNotFoundException
   */
  public static int insertItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ItemIdNotFoundException,
      SaleIdNotFoundException, InvalidQuantityException, ClassNotFoundException {

    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }

    Item item = DatabaseSelectHelper.getItem(itemId);
    if (item == null) {
      throw new ItemIdNotFoundException();
    }

    SaleImpl sale = DatabaseSelectHelper.getSaleById(saleId);
    if (sale == null) {
      throw new SaleIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
    connection.close();
    return itemizedId;
  }

  /**
   * Use this to insert an Account into the Database.
   * 
   * @param userId id of the user the account is based off of.
   * @return the id of the inserted account
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws UserIdNotFoundException if userId is not in the User table.
   * @throws ClassNotFoundException
   */
  public static int insertAccount(int userId) throws DatabaseInsertException, SQLException,
      UserIdNotFoundException, ClassNotFoundException {

    User user = DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int accountId = DatabaseInserter.insertAccount(userId, true, connection);
    connection.close();
    return accountId;
  }

  /**
   * Use this to insert a Customer Account into the Database.
   * 
   * @param userId id of the user the account is based off of.
   * @return the id of the inserted account
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws UserIdNotFoundException if userId is not in the User table.
   * @throws ClassNotFoundException
   */
  public static int insertCustomerAccount(int customerId) throws DatabaseInsertException,
      SQLException, UserIdNotFoundException, ClassNotFoundException {

    Customer customer = DatabaseSelectHelper.getCustomerDetails(customerId);
    if (customer == null) {
      throw new UserIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int accountId = DatabaseInserter.insertAccount(customerId, true, connection);
    connection.close();
    return accountId;
  }

  /**
   * Use this to insert a single item into a given account for recovery next login.
   * 
   * @param accountId the id of the account.
   * @param itemId the id of the item.
   * @param quantity the quantity of the item (must be greater than 0).
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws ItemIdNotFoundException if itemId is not present in the Items table.
   * @throws InvalidQuantityException if quantity is less than 0.
   * @throws ClassNotFoundException
   */
  public static int insertAccountLine(int accountId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ItemIdNotFoundException,
      InvalidQuantityException, ClassNotFoundException {

    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }

    Item item = DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int accountLineId = DatabaseInserter.insertAccountLine(accountId, itemId, quantity, connection);
    connection.close();
    return accountLineId;
  }

}
