package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DatabaseDriverAndroidHelper extends DatabaseDriverAndroid {

  public DatabaseDriverAndroidHelper(Context context){
    super(context);
  }

  //INSERTS
  public long insertRole(String role) {
    return super.insertRole(role);
  }

  public long insertNewUser(String name, int age, String address, String password) {
    return super.insertNewUser(name, age, address, password);
  }

  public long insertUserRole(int userId, int roleId) {
    return super.insertUserRole(userId, roleId);
  }

  public long insertItem(String name, BigDecimal price) {
    return super.insertItem(name, price);
  }

  public long insertInventory(int itemId, int quantity) {
    return super.insertInventory(itemId, quantity);
  }

  public long insertSale(int userId, BigDecimal totalPrice) {
    return super.insertSale(userId, totalPrice);
  }

  public long insertItemizedSale(int saleId, int itemId, int quantity) {
    return super.insertItemizedSale(saleId, itemId, quantity);
  }

  public long insertAccount(int userId, boolean active) {
    return super.insertAccount(userId, active);
  }

  public long insertAccountLine(int accountId, int itemId, int quantity) {
    return super.insertAccountLine(accountId, itemId, quantity);
  }
  
  //SELECT METHODS
  public Cursor getRoles() {
    return super.getRoles();
  }

  public String getRole(int id) {
    return super.getRole(id);

  }

  public int getUserRole(int userId) {
    try{
      return super.getUserRole(userId);
    } catch(Exception e) {
      return 0;
    }
  }


  public List<Integer> getUsersByRoleHelper(int roleId) {
    List<Integer> ids = new ArrayList<Integer>();
    Cursor adminIds =  super.getUsersByRole(roleId);
    while(adminIds.moveToNext()){
      int index;
      index = adminIds.getColumnIndexOrThrow("USERID");
      int id = adminIds.getInt(index);
      ids.add(id);
    }
    return ids;
  }

  public Cursor getUsersDetails() {
    return super.getUsersDetails();
  }

  public Cursor getUserDetails(int userId) {
    return super.getUserDetails(userId);
  }

  public String getPassword(int userId) {
    return super.getPassword(userId);
  }

  public List<List<String>> getAllItemsHelper() {
    List<List<String>> items = new ArrayList<>();
    Cursor itemList = super.getAllItems();
    if (itemList.moveToFirst()) {
      while (!itemList.isAfterLast()) {
        List<String> item = new ArrayList<>();
        String id = itemList.getString(itemList.getColumnIndexOrThrow("ID"));
        String name = itemList.getString(itemList.getColumnIndexOrThrow("NAME"));
        String price = itemList.getString(itemList.getColumnIndexOrThrow("PRICE"));
        item.add(id);
        item.add(name);
        item.add(price);
        items.add(item);
        itemList.moveToNext();
      }
    }
    return items;
  }

  public  Cursor getItem(int itemId) {
    return super.getItem(itemId);
  }

  public Cursor getInventory() {
    return super.getInventory();
  }

  public int getInventoryQuantity(int itemId) {
    return super.getInventoryQuantity(itemId);
  }

  public Cursor getSales() {
    return super.getSales();
  }

  public Cursor getSaleById(int saleId) {
    return super.getSaleById(saleId);
  }

  public Cursor getSalesToUser(int userId) {
    return super.getSalesToUser(userId);
  }

  public Cursor getItemizedSales() {
    return super.getItemizedSales();
  }

  public Cursor getItemizedSaleById(int saleId) {
    return super.getItemizedSaleById(saleId);
  }

  public Cursor getUserAccounts(int userId) {
    return super.getUserAccounts(userId);
  }

  public Cursor getAccountDetails(int accountId) {
    return super.getAccountDetails(accountId);
  }

  public Cursor getUserActiveAccounts(int userId) {
    return super.getUserActiveAccounts(userId);
  }

  public Cursor getUserInactiveAccounts(int userId) {
    return super.getUserInactiveAccounts(userId);
  }

  //UPDATE METHODS

  public boolean updateRoleName(String name, int id) {
    return super.updateRoleName(name, id);
  }

  public boolean updateUserName(String name, int id) {
    return super.updateUserName(name, id);
  }

  public boolean updateUserAge(int age, int id) {
    return super.updateUserAge(age, id);
  }

  public boolean updateUserAddress(String address, int id) {
    return super.updateUserAddress(address, id);
  }

  public boolean updateUserRole(int roleId, int id) {
    return super.updateUserRole(roleId, id);
  }

  public boolean updateItemName(String name, int id) {
    return super.updateItemName(name, id);
  }

  public boolean updateItemPrice(BigDecimal price, int id) {
    return super.updateItemPrice(price, id);
  }

  public boolean updateInventoryQuantity(int quantity, int id) {
    return super.updateInventoryQuantity(quantity, id);
  }

  public boolean updateAccountStatus(int accountId, boolean active) {
    return super.updateAccountStatus(accountId, active);
  }
}
