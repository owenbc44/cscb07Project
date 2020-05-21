package com.b07.users;

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

public class Admin extends User {

  /**
   * 
   */
  private static final long serialVersionUID = -8953948514657167146L;
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
   * @throws ClassNotFoundException
   */
  public boolean promoteEmployee(Employee employee) throws SQLException, ClassNotFoundException {

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
   * @throws ClassNotFoundException
   */

  public void viewBooks() throws SQLException, ClassNotFoundException {
    BigDecimal sum = new BigDecimal(0);
    HashMap<String, Integer> totalitemMap = new HashMap<String, Integer>();
    totalitemMap.put("FISHING_ROD", 0);
    totalitemMap.put("HOCKEY_STICK", 0);
    totalitemMap.put("SKATES", 0);
    totalitemMap.put("RUNNING_SHOES", 0);
    totalitemMap.put("PROTEIN_BAR", 0);

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
      HashMap<ItemImpl, Integer> itemMapForSingleSale = new HashMap<ItemImpl, Integer>();
      itemMapForSingleSale = DatabaseSelectHelper.getItemizedSaleById(sales.get(i).getId());
      int j = 0;
      for (ItemImpl singleItem : itemMapForSingleSale.keySet()) {

        if (j == 0) {
          System.out.printf(" <<%s>>: <<%d>>\n", singleItem.getName(),
              itemMapForSingleSale.get(singleItem));

        } else {
          System.out.printf("                    <<%s>>: <<%d>>\n", singleItem.getName(),
              itemMapForSingleSale.get(singleItem));
        }
        j = j + 1;

        int n = totalitemMap.get(singleItem.getName());
        totalitemMap.replace(singleItem.getName(), n + itemMapForSingleSale.get(singleItem));


      }
      System.out.println("----------------------------------------");
    }
    for (String item : totalitemMap.keySet()) {
      System.out.printf("Number <<%s>> Sold: <<%d>>\n", item, totalitemMap.get(item));
    }
    System.out.printf("TOTAL SALES: <<%f>>\n", sum);
  }

}


