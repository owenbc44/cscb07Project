package com.b07.database.helper;

import java.math.BigDecimal;
import com.b07.database.ContentValues;
import com.b07.database.Cursor;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.SQLiteDatabase;
import com.b07.security.PasswordHelpers;

public class DatabaseDriverAndroidHelper extends DatabaseDriverAndroid {
  //INSERTS
  public long insertRole(String role) {
    super.insertRole(role);
  }

  public long insertNewUser(String name, int age, String address, String password) {
    super.insertNewUser(name, age, address, password);
  }

  public long insertUserRole(int userId, int roleId) {
    super.insertUserRole(userId, roleId);
  }

  public long insertItem(String name, BigDecimal price) {
    super.insertItem(name, price);
  }

  public long insertInventory(int itemId, int quantity) {
    super.insertInventory(itemId, quantity);
  }

  public long insertSale(int userId, BigDecimal totalPrice) {
    super.insertSale(userId, totalPrice);
  }

  public long insertItemizedSale(int saleId, int itemId, int quantity) {
    super.insertItemizedSale(saleId, itemId, quantity);
  }

  public long insertAccount(int userId, boolean active) {
    super.insertAccount(userId, active);
  }

  public long insertAccountLine(int accountId, int itemId, int quantity) {
    super.insertAccountLine(accountId, itemId, quantity);
  }
  
  //SELECT METHODS
  public Cursor getRoles() {
    super.getRoles();
  }

  public String getRole(int id) {
    super.getRole(id);

  }

  public int getUserRole(int userId) {
    super.getUserRole(userId);
  }


  public Cursor getUsersByRole(int roleId) {
    super.getUsersByRole(roleId);
  }

  public Cursor getUsersDetails() {
    super.getUsersDetails();
  }

  public Cursor getUserDetails(int userId) {
    super.getUserDetails(userId);
  }

  public String getPassword(int userId) {
    super.getPassword(userId);
  }

  public Cursor getAllItems() {
    super.getAllItems();
  }

  public  Cursor getItem(int itemId) {
    super.getItem(itemId);
  }

  public Cursor getInventory() {
    super.getInventory();
  }

  public int getInventoryQuantity(int itemId) {
    super.getInventoryQuantity(itemId);
  }

  public Cursor getSales() {
    super.getSales();
  }

  public Cursor getSaleById(int saleId) {
    super.getSaleById(saleId);
  }

  public Cursor getSalesToUser(int userId) {
    super.getSalesToUser(userId);
  }

  public Cursor getItemizedSales() {
    super.getItemizedSales();
  }

  public Cursor getItemizedSaleById(int saleId) {
    super.getItemizedSaleById(saleId);
  }

  public Cursor getUserAccounts(int userId) {
    super.getUserAccounts(userId);
  }

  public Cursor getAccountDetails(int accountId) {
    super.getAccountDetails(accountId);
  }

  public Cursor getUserActiveAccounts(int userId) {
    super.getUserActiveAccounts(userId);
  }

  public Cursor getUserInactiveAccounts(int userId) {
    super.getUserInactiveAccounts(userId);
  }

  //UPDATE METHODS

  public boolean updateRoleName(String name, int id) {
    super.updateRoleName(name, id);
  }

  public boolean updateUserName(String name, int id) {
    super.updateUserName(name, id);
  }

  public boolean updateUserAge(int age, int id) {
    super.updateUserAge(age, id);
  }

  public boolean updateUserAddress(String address, int id) {
    super.updateUserAddress(address, id);
  }

  public boolean updateUserRole(int roleId, int id) {
    super.updateUserRole(roleId, id);
  }

  public boolean updateItemName(String name, int id) {
    super.updateItemName(name, id);
  }

  public boolean updateItemPrice(BigDecimal price, int id) {
    super.updateItemPrice(price, id);
  }

  public boolean updateInventoryQuantity(int quantity, int id) {
    super.updateInventoryQuantity(quantity, id);
  }

  public boolean updateAccountStatus(int accountId, boolean active) {
    super.updateAccountStatus(accountId, active);
  }
}
