package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.b07.database.DatabaseUpdater;
import com.b07.enumerators.CompareStringToEnum;
import com.b07.exceptions.InvalidAddressException;
import com.b07.exceptions.InvalidAgeException;
import com.b07.exceptions.InvalidItemException;
import com.b07.exceptions.InvalidPriceException;
import com.b07.exceptions.InvalidQuantityException;
import com.b07.exceptions.InvalidRoleException;
import com.b07.exceptions.ItemIdNotFoundException;
import com.b07.exceptions.RoleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.Item;
import com.b07.users.User;

public class DatabaseUpdateHelper extends DatabaseUpdater {

  /**
   * Use this to update the role name of a role id in the Roles tables.
   * 
   * @param name the new name of the role.
   * @param id the id which is to be updated.
   * @return true if successful, false otherwise
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws InvalidRoleException if name not found in Roles enum.
   * @throws RoleIdNotFoundException if id not found in table.
   */
  public static boolean updateRoleName(String name, int id)
      throws SQLException, InvalidRoleException, RoleIdNotFoundException, ClassNotFoundException {

    if (!CompareStringToEnum.checkRole(name)) {
      throw new InvalidRoleException();
    }

    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
    if (!roleIds.contains(id)) {
      throw new RoleIdNotFoundException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
    connection.close();
    return complete;
  }

  /**
   * Use this to update the username of the user with id userId in the User table.
   * 
   * @param name the new name of the user.
   * @param userId the id of the user.
   * @return true if successful, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws UserIdNotFoundException if userId not found in table.
   */
  public static boolean updateUserName(String name, int userId)
      throws SQLException, UserIdNotFoundException, ClassNotFoundException {
    User user = DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserName(name, userId, connection);
    connection.close();
    return complete;
  }

  /**
   * Use this to update the age of the user with id userId in the Users table.
   * 
   * @param age the new age.
   * @param userId the id of the user.
   * @return true if successful, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws UserIdNotFoundException if userId not found in the table.
   * @throws InvalidAgeException if age <= 0.
   */
  public static boolean updateUserAge(int age, int userId)
      throws SQLException, UserIdNotFoundException, InvalidAgeException, ClassNotFoundException {
    User user = DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }
    if (age <= 0) {
      throw new InvalidAgeException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAge(age, userId, connection);
    connection.close();
    return complete;
  }

  /**
   * Use this to update the address of a user with id userId in the Users table.
   * 
   * @param address the new address.
   * @param userId the id of the user.
   * @return true if successful, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws UserIdNotFoundException if userId not found in the table.
   * @throws InvalidAddressException if the address is longer than 100 characters.
   */
  public static boolean updateUserAddress(String address, int userId) throws SQLException,
      UserIdNotFoundException, InvalidAddressException, ClassNotFoundException {
    User user = DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }
    if (address.length() > 100) {
      throw new InvalidAddressException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
    connection.close();
    return complete;

  }

  /**
   * Use this program to update the role of the user with id userId in the UserRoles table.
   * 
   * @param roleId the id of the new role.
   * @param userId the id of the user.
   * @return true if successful/ false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws RoleIdNotFoundException if roleId not found in the table.
   * @throws UserIdNotFoundException if userId not found in the table.
   */
  public static boolean updateUserRole(int roleId, int userId) throws SQLException,
      RoleIdNotFoundException, UserIdNotFoundException, ClassNotFoundException {
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
    if (!roleIds.contains(roleId)) {
      throw new RoleIdNotFoundException();
    }
    User user = DatabaseSelectHelper.getUserDetails(userId);
    if (user == null) {
      throw new UserIdNotFoundException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
    connection.close();
    return complete;

  }

  /**
   * Use this to update the item name of itemId in the Items table.
   * 
   * @param name the new name.
   * @param itemId the id of the item.
   * @return true if successful, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws InvalidItemException if name is not in the ItemTypes enum.
   * @throws ItemIdNotFoundException if itemId is not found in the table.
   */
  public static boolean updateItemName(String name, int itemId)
      throws SQLException, InvalidItemException, ItemIdNotFoundException, ClassNotFoundException {
    if (name == null || name.length() > 64 || !CompareStringToEnum.checkItemType(name)) {
      throw new InvalidItemException();
    }
    Item item = DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemName(name, itemId, connection);
    connection.close();
    return complete;

  }

  /**
   * Use this to update the price of an item with id itemId in the Items table.
   * 
   * @param price the new price.
   * @param itemId the id of the item.
   * @return true if successful, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws InvalidPriceException price is <= 0 or not 2dp precise.
   * @throws ItemIdNotFoundException if itemId is not found in the table.
   */
  public static boolean updateItemPrice(BigDecimal price, int itemId)
      throws SQLException, InvalidPriceException, ItemIdNotFoundException, ClassNotFoundException {
    if (price.compareTo(new BigDecimal("0")) != 1 || price.scale() != 2) {
      throw new InvalidPriceException();
    }
    Item item = DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);
    connection.close();
    return complete;
  }

  /**
   * Use this to update the quantity of item with id itemId in Inventory table.
   * 
   * @param quantity the new quantity.
   * @param itemId the id of the item.
   * @return true if successful, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws ItemIdNotFoundException if itemId is not found in the table.
   * @throws InvalidQuantityException if quantity <= 0.
   */
  public static boolean updateInventoryQuantity(int quantity, int itemId) throws SQLException,
      ItemIdNotFoundException, InvalidQuantityException, ClassNotFoundException {
    Item item = DatabaseSelectHelper.getItem(itemId);

    if (item == null) {
      throw new ItemIdNotFoundException();
    }
    if (quantity <= 0) {
      throw new InvalidQuantityException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
    connection.close();
    return complete;
  }

  /**
   * Use this program to update the status of an account.
   * 
   * @param accountId the id of the account.
   * @param active the status of the account.
   * @return true if successful/ false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   * @throws RoleIdNotFoundException if roleId not found in the table.
   * @throws UserIdNotFoundException if userId not found in the table.
   * 
   */
  public static boolean updateAccountStatus(int accountId, boolean active) throws SQLException,
      RoleIdNotFoundException, UserIdNotFoundException, ClassNotFoundException {

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountStatus(accountId, active, connection);
    connection.close();
    return complete;
  }
}
