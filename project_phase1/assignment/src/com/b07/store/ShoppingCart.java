package com.b07.store;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InvalidQuantityException;
import com.b07.exceptions.ItemIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.Item;
import com.b07.users.Customer;

public class ShoppingCart {

  private HashMap<Item, Integer> items;
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
  public void addItem(Item item, int quantity) {
    if (items.containsKey(item)) {
      items.replace(item, quantity + items.get(item));
    } else {
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
  public void removeItem(Item item, int quantity) {
    if (items.containsKey(item)) {
      items.replace(item, items.get(item) - quantity);
      if (items.get(item) == 0) {
        items.remove(item);
      }

      total = total.subtract(new BigDecimal(quantity).multiply(item.getPrice()));
    }
  }

  /**
   * @return returns a list of all the items in the shopping cart
   */
  public List<Item> getItems() {
    List<Item> cart = new ArrayList<Item>();
    for (Item key : items.keySet()) {
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
  }

  /**
   * Commits a sale to the database, reduces the respective item amounts from inventory and clears
   * the shopping cart for the next customer
   * 
   * @return true if task successfully completed, false otherwise
   */
  public boolean checkOut() {
    if (this.customer != null) {

      for (Item key : items.keySet()) {
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


      for (Item key : items.keySet()) {
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
      total = new BigDecimal("0");
      return true;
    }

    return false;

  }



}
