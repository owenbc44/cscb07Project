package com.b07.serialization;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.store.SalesLogImpl;
import com.b07.users.User;

public class DatabaseInOneObject implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 8350377735884238873L;
  public HashMap<Integer, ArrayList<Object>> allusers = new HashMap<Integer, ArrayList<Object>>();
  public List<ItemImpl> allitems;
  public InventoryImpl inventory;
  public SalesLogImpl allSales;

  public DatabaseInOneObject() throws SQLException, ClassNotFoundException {
    List<User> allUsers = new ArrayList<User>();
    allUsers = DatabaseSelectHelper.getUsersDetails();

    for (int i = 0; i < allUsers.size(); i++) {
      ArrayList<Object> singleUser = new ArrayList<Object>();
      singleUser.add(allUsers.get(i).getName());
      singleUser.add(allUsers.get(i).getAge());
      singleUser.add(allUsers.get(i).getAddress());
      singleUser.add(DatabaseSelectHelper.getUserRoleId(allUsers.get(i).getId()));
      singleUser.add(DatabaseSelectHelper
          .getRoleName(DatabaseSelectHelper.getUserRoleId(allUsers.get(i).getId())));
      singleUser.add(DatabaseSelectHelper.getPassword(allUsers.get(i).getId()));
      singleUser.add(DatabaseSelectHelper.getUserAccounts(allUsers.get(i).getId()));
      allusers.put(allUsers.get(i).getId(), singleUser);
    }

    this.allitems = DatabaseSelectHelper.getAllItems();
    this.inventory = DatabaseSelectHelper.getInventory();
    this.allSales = DatabaseSelectHelper.getSales();
  }



  public HashMap<Integer, ArrayList<Object>> getAllusers() {
    return allusers;
  }

  public List<ItemImpl> getAllitems() {
    return allitems;
  }

  public InventoryImpl getInventory() {
    return inventory;
  }

  public SalesLogImpl getAllSales() {
    return allSales;
  }


}
