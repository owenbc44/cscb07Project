package com.example.myapplication.database.helper;

import com.example.myapplication.database.DatabaseInserter;
import com.example.myapplication.enumerators.CompareStringToEnum;
import com.example.myapplication.exceptions.DatabaseInsertException;
import com.example.myapplication.exceptions.InvalidAddressException;
import com.example.myapplication.exceptions.InvalidAgeException;
import com.example.myapplication.exceptions.InvalidItemException;
import com.example.myapplication.exceptions.InvalidPriceException;
import com.example.myapplication.exceptions.InvalidQuantityException;
import com.example.myapplication.exceptions.InvalidRoleException;
import com.example.myapplication.exceptions.ItemIdNotFoundException;
import com.example.myapplication.exceptions.RoleIdNotFoundException;
import com.example.myapplication.exceptions.SaleIdNotFoundException;
import com.example.myapplication.exceptions.UserIdNotFoundException;
import com.example.myapplication.inventory.Item;
import com.example.myapplication.store.SaleImpl;
import com.example.myapplication.users.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


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
   */
  public static int insertRole(String name)
      throws DatabaseInsertException, SQLException, InvalidRoleException {
    if (!CompareStringToEnum.checkRole(name)) {
      throw new InvalidRoleException();
    }
    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
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
   */
  public static int insertNewUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, InvalidAddressException, InvalidAgeException {
    if (address.length() > 100) {
      throw new InvalidAddressException();
    }
    if (age <= 0) {
      throw new InvalidAgeException();
    }
    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
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
   */
  public static int insertUserRole(int userId, int roleId) throws DatabaseInsertException,
      SQLException, UserIdNotFoundException, RoleIdNotFoundException {

    User user = com.example.myapplication.database.helper.DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }

    List<Integer> roleIds = com.example.myapplication.database.helper.DatabaseSelectHelper.getRoleIds();
    if (!roleIds.contains(roleId)) {
      throw new RoleIdNotFoundException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
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
   */
  public static int insertItem(String name, BigDecimal price)
      throws DatabaseInsertException, SQLException, InvalidItemException, InvalidPriceException {

    if (name == null || name.length() > 64 || !CompareStringToEnum.checkItemType(name)) {
      throw new InvalidItemException();
    }
    if (price.compareTo(new BigDecimal("0")) != 1 || price.scale() != 2) {
      throw new InvalidPriceException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
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
   */
  public static int insertInventory(int itemId, int quantity) throws DatabaseInsertException,
      SQLException, ItemIdNotFoundException, InvalidQuantityException {

    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }

    Item item = com.example.myapplication.database.helper.DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
    int inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
    connection.close();
    return inventoryId;
  }

  /**
   * Use this to insert sales into the Sales table.
   * 
   * @param userId the id of the user.
   * @param totalPrice the total price of the sale.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if there is a failure on the insert.
   * @throws SQLException if there is a failure on the closing of the connection.
   * @throws UserIdNotFoundException if userId not in the User table.
   */
  public static int insertSale(int userId, BigDecimal totalPrice)
      throws DatabaseInsertException, SQLException, UserIdNotFoundException {

    User user = com.example.myapplication.database.helper.DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
    int saleId = DatabaseInserter.insertSale(userId, totalPrice, connection);
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
   */
  public static int insertItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ItemIdNotFoundException,
          SaleIdNotFoundException, InvalidQuantityException {

    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }

    Item item = com.example.myapplication.database.helper.DatabaseSelectHelper.getItem(itemId);
    if (item == null) {
      throw new ItemIdNotFoundException();
    }

    SaleImpl sale = com.example.myapplication.database.helper.DatabaseSelectHelper.getSaleById(saleId);
    if (sale == null) {
      throw new SaleIdNotFoundException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
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
   */
  public static int insertAccount(int userId)
      throws DatabaseInsertException, SQLException, UserIdNotFoundException {

    User user = com.example.myapplication.database.helper.DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
    int AccId = DatabaseInserter.insertAccount(userId, connection);
    connection.close();
    return AccId;
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
   */
  public static int insertAccountLine(int accountId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ItemIdNotFoundException,
      InvalidQuantityException {

    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }

    Item item = com.example.myapplication.database.helper.DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }

    Connection connection = com.example.myapplication.database.helper.DatabaseDriverHelper.connectOrCreateDataBase();
    int accountLineId = DatabaseInserter.insertAccountLine(accountId, itemId, quantity, connection);
    connection.close();
    return accountLineId;
  }

}
