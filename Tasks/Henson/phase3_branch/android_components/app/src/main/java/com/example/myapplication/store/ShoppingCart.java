package com.example.myapplication.store;

import com.example.myapplication.database.helper.DatabaseInsertHelper;
import com.example.myapplication.database.helper.DatabaseSelectHelper;
import com.example.myapplication.database.helper.DatabaseUpdateHelper;
import com.example.myapplication.exceptions.DatabaseInsertException;
import com.example.myapplication.exceptions.InvalidQuantityException;
import com.example.myapplication.exceptions.ItemIdNotFoundException;
import com.example.myapplication.exceptions.UserIdNotFoundException;
import com.example.myapplication.inventory.ItemImpl;
import com.example.myapplication.users.Customer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCart {

  private HashMap<ItemImpl, Integer> items = new HashMap<ItemImpl, Integer>();
  private Customer customer = null;
  private BigDecimal total = new BigDecimal("0");
  private final BigDecimal TAXRATE = new BigDecimal("1.13");

  /**
   * Constructor
   * 
   * @param customer
   */
  public ShoppingCart(Customer customer) {
    this.customer = customer;
  }

  /**
   * Adds items to the shopping cart
   * 
   * @param item the item to be added
   * @param quantity the quantity of the item to be added
   */
  public void addItem(ItemImpl item, int quantity) {
    boolean itemFound = false;

    for (ItemImpl key : items.keySet()) {
      if (key.getId() == item.getId()) {
        itemFound = true;
        items.replace(key, quantity + items.get(key));
      }
    }

    if (!itemFound) {
      items.put(item, quantity);
    }

    total = total.add(new BigDecimal(quantity).multiply(item.getPrice()));

  }

  /**
   * Removes items from the shopping cart
   * 
   * @param item the item to be removed
   * @param quantity the quantity of the item to be removed
   */
  public boolean removeItem(ItemImpl item, int quantity) {

    for (ItemImpl key : items.keySet()) {
      if (key.getId() == item.getId()) {
        if (items.get(key) > quantity) {
          total = total.subtract(new BigDecimal(quantity).multiply(key.getPrice()));
          items.replace(key, items.get(key) - quantity);
        } else {
          total = total.subtract(new BigDecimal(items.get(key)).multiply(key.getPrice()));
          items.remove(key);
        }
        return true;
      }
    }

    return false;
  }

  /**
   * @return returns a list of all the items in the shopping cart
   */
  public List<ItemImpl> getItems() {
    List<ItemImpl> cart = new ArrayList<ItemImpl>();
    for (ItemImpl key : items.keySet()) {
      cart.add(key);
    }
    return cart;

  }

  /**
   * @return returns the current customer using the shopping cart
   */
  public Customer getCustomer() {
    return this.customer;
  }

  /**
   * @return returns the current total of the shopping cart
   */
  public BigDecimal getTotal() {
    return this.total;
  }

  /**
   * @return returns the tax rate applied
   */
  public BigDecimal getTaxRate() {
    return this.TAXRATE;
  }

  /**
   * empties the shopping cart
   */
  public void clearCart() {
    items.clear();
    total = new BigDecimal("0");

  }

  /**
   * Commits a sale to the database, reduces the respective item amounts from inventory and clears
   * the shopping cart for the next customer
   * 
   * @return true if task successfully completed, false otherwise
   */
  public boolean checkOut() {
    if (this.customer != null) {

      for (ItemImpl key : items.keySet()) {
        int quantity = 0;
        try {
          quantity = DatabaseSelectHelper.getInventoryQuantity(key.getId());
        } catch (SQLException e) {
          return false;
        }
        if (quantity < items.get(key)) {
          return false;
        }
      }


      for (ItemImpl key : items.keySet()) {
        try {
          DatabaseUpdateHelper.updateInventoryQuantity(
              DatabaseSelectHelper.getInventoryQuantity(key.getId()) - items.get(key), key.getId());
        } catch (ItemIdNotFoundException | InvalidQuantityException | SQLException e) {
          return false;
        }
      }

      total = total.multiply(TAXRATE);

      try {
        DatabaseInsertHelper.insertSale(customer.getId(), total);
      } catch (UserIdNotFoundException | DatabaseInsertException | SQLException e) {
        return false;
      }

      clearCart();
      return true;
    }

    return false;

  }

  public HashMap<ItemImpl, Integer> getItemMap() {
    return this.items;
  }

  public boolean containsItem(ItemImpl item) {

    for (ItemImpl key : items.keySet()) {
      if (key.getId() == item.getId()) {
        return true;
      }
    }

    return false;
  }



}
