package com.b07.users;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.RoleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.ItemImpl;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;

public class Admin extends User implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3633876439802959554L;
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  /**
   * Constructor
   * 
   * @param id
   * @param name
   * @param age
   * @param address
   */
  public Admin(int id, String name, int age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
  }

  /**
   * Constructor
   * 
   * @param id
   * @param name
   * @param age
   * @param address
   * @param authenticated
   */
  public Admin(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.authenticated = authenticated;
  }

  /**
   * Use this to promote any other employee to the role o ADMIN.
   * 
   * @param employee the employee to be promoted
   * @return true if successful, false otherwise.
   */
  public boolean promoteEmployee(Employee employee) throws SQLException {

    try {
      DatabaseUpdateHelper.updateUserRole(1, employee.getId());
    } catch (UserIdNotFoundException | RoleIdNotFoundException | SQLException e) {
      return false;
    }

    return true;
  }

  /**
   * Use to generate historic sales records for Admins
   * 
   * @throws SQLException
   */
  public void viewBooks() throws SQLException {
    BigDecimal sum = new BigDecimal(0);
    HashMap<ItemImpl, Integer> totalitemMap = new HashMap<ItemImpl, Integer>();
    SalesLog salesLog = new SalesLogImpl();
    salesLog = DatabaseSelectHelper.getSales();
    List<SaleImpl> sales = new ArrayList<SaleImpl>();
    sales = salesLog.getSales();
    for (int i = 0; i < sales.size(); i++) {
      sum = sum.add(sales.get(i).getTotalPrice());
      System.out.printf("Customer: <<%s>>\n", sales.get(i).getUser().getName());
      System.out.printf("Purchase Number: <<%d>>\n", sales.get(i).getId());
      System.out.printf("Total Purchase Price: <<%f>>\n", sales.get(i).getTotalPrice());
      System.out.print("Itemized Breakdown:");
      HashMap<ItemImpl, Integer> itemMap = new HashMap<ItemImpl, Integer>();
      itemMap = sales.get(i).getItemMap();
      for (ItemImpl k : itemMap.keySet()) {
        if (!(totalitemMap.containsKey(k))) {
          totalitemMap.put(k, itemMap.get(k));
        } else {
          totalitemMap.replace(k, totalitemMap.get(k) + itemMap.get(k));
        }
        System.out.printf("                    <<%s>>: <<%d>>\n", k.getName(), itemMap.get(k));
      }
      System.out.println("----------------------------------------\n");
    }
    for (ItemImpl j : totalitemMap.keySet()) {
      System.out.printf("Number <<%s>> Sold: <<%d>>\n", j.getName(), totalitemMap.get(j));
    }
    System.out.printf("TOTAL SALES: <<%f>>\n", sum);
  }


}


