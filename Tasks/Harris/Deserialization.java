
package com.b07.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseInsertHelper;
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
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLogImpl;

public class Deserialization {

  private static boolean clearDatabase() {
    Path path = Paths.get("inventorymgmt.db");
    try {
      Files.deleteIfExists(path);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }


  public static Object deserialize(String fileLocationAndName)
      throws SQLException, FileNotFoundException, IOException {
    try {
      FileInputStream fileInput = new FileInputStream(fileLocationAndName);
      ObjectInputStream in = new ObjectInputStream(fileInput);
      Object theEntireDatabase = in.readObject();
      in.close();
      fileInput.close();
      return theEntireDatabase;
    } catch (IOException i) {
      i.printStackTrace();
      System.out.println("Deserialization failed");
      return null;
    } catch (ClassNotFoundException c) {
      System.out.println("Cat class not found");
      c.printStackTrace();
      System.out.println("Deserialization failed");
      return null;
    }
  }


  public static void insertTheNewDatabase(DatabaseInOneObject theEntireDatabase)
      throws InvalidAddressException, InvalidAgeException, DatabaseInsertException, SQLException,
      UserIdNotFoundException, RoleIdNotFoundException, InvalidRoleException, InvalidItemException,
      InvalidPriceException, ItemIdNotFoundException, InvalidQuantityException,
      SaleIdNotFoundException, ClassNotFoundException {
    Boolean removeOldDatabase = clearDatabase();
    if (removeOldDatabase == true) {

      HashMap<Integer, ArrayList<Object>> allusers = theEntireDatabase.getAllusers();
      for (Integer key : allusers.keySet()) {
        ArrayList<Object> singleUser = allusers.get(key);
        DatabaseInsertHelper.insertNewUser((String) singleUser.get(0), (int) singleUser.get(1),
            (String) singleUser.get(2), (String) singleUser.get(5));
        DatabaseInsertHelper.insertUserRole(key, (int) singleUser.get(3));
        DatabaseInsertHelper.insertRole((String) singleUser.get(4));
      }

      List<ItemImpl> allitems = theEntireDatabase.getAllitems();
      for (int i = 0; i < allitems.size(); i++) {
        DatabaseInsertHelper.insertItem(allitems.get(i).getName(), allitems.get(i).getPrice());
      }

      InventoryImpl inventory = theEntireDatabase.getInventory();
      HashMap<ItemImpl, Integer> itemMap = inventory.getItemMap();
      for (ItemImpl key : itemMap.keySet()) {
        DatabaseInsertHelper.insertInventory(key.getId(), itemMap.get(key));
      }

      SalesLogImpl allSales = theEntireDatabase.getAllSales();
      List<SaleImpl> allsales = allSales.getSales();
      for (int i = 0; i < allsales.size(); i++) {
        DatabaseInsertHelper.insertSale(allsales.get(i).getUser().getId(),
            allsales.get(i).getTotalPrice());
        HashMap<ItemImpl, Integer> singleitemMap = allsales.get(i).getItemMap();
        for (ItemImpl key : singleitemMap.keySet()) {
          DatabaseInsertHelper.insertItemizedSale(allsales.get(i).getId(), key.getId(),
              singleitemMap.get(key));
        }


      }


    } else {
      System.out.println("Remove the old database unsuccessfully, please try again");
    }



  }



}

