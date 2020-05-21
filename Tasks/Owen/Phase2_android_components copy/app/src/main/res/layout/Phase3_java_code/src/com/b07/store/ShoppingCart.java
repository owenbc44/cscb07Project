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
import com.b07.exceptions.SaleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.ItemImpl;
import com.b07.users.Customer;

public class ShoppingCart {

  private HashMap<ItemImpl, Integer> itemMap = new HashMap<ItemImpl, Integer>();
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

    for (ItemImpl key : itemMap.keySet()) {
      if (key.getId() == item.getId()) {
        itemFound = true;
        itemMap.replace(key, quantity + itemMap.get(key));
      }
    }

    if (!itemFound) {
      itemMap.put(item, quantity);
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

    for (ItemImpl key : itemMap.keySet()) {
      if (key.getId() == item.getId()) {
        if (itemMap.get(key) > quantity) {
          total = total.subtract(new BigDecimal(quantity).multiply(key.getPrice()));
          itemMap.replace(key, itemMap.get(key) - quantity);
        } else {
          total = total.subtract(new BigDecimal(itemMap.get(key)).multiply(key.getPrice()));
          itemMap.remove(key);
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
    for (ItemImpl key : itemMap.keySet()) {
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
    itemMap.clear();
    total = new BigDecimal("0");

  }

  /**
   * Commits a sale to the database, reduces the respective item amounts from inventory and clears
   * the shopping cart for the next customer
   * 
   * @return true if task successfully completed, false otherwise
   * @throws ClassNotFoundException
   */
  public boolean checkOut() throws ClassNotFoundException {
    if (this.customer != null && total != new BigDecimal("0")) {

      for (ItemImpl key : itemMap.keySet()) {
        int quantity = 0;
        try {
          quantity = DatabaseSelectHelper.getInventoryQuantity(key.getId());
        } catch (SQLException e) {
          return false;
        }
        if (quantity < itemMap.get(key)) {
          return false;
        }
      }

      total = total.multiply(TAXRATE);

      int saleId;
      try {
        saleId = DatabaseInsertHelper.insertSale(customer.getId(), total);
      } catch (UserIdNotFoundException | DatabaseInsertException | SQLException e) {
        return false;
      }

      try {
        for (ItemImpl key : itemMap.keySet()) {
          DatabaseInsertHelper.insertItemizedSale(saleId, key.getId(), itemMap.get(key));
          DatabaseUpdateHelper.updateInventoryQuantity(
              DatabaseSelectHelper.getInventoryQuantity(key.getId()) - itemMap.get(key),
              key.getId());
        }
      } catch (SQLException | ItemIdNotFoundException | SaleIdNotFoundException
          | InvalidQuantityException | DatabaseInsertException e) {
        return false;
      }

      clearCart();
      return true;
    }

    return false;

  }

  public HashMap<ItemImpl, Integer> getItemMap() {
    return this.itemMap;
  }

  public boolean containsItem(ItemImpl item) {

    for (ItemImpl key : itemMap.keySet()) {
      if (key.getId() == item.getId()) {
        return true;
      }
    }

    return false;
  }



}
