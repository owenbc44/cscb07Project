package com.b07.inventory;

import java.util.HashMap;

public class InventoryImpl implements Inventory {

  /**
   * 
   */
  private static final long serialVersionUID = 6916247833281781071L;
  private HashMap<ItemImpl, Integer> itemMap = new HashMap<ItemImpl, Integer>();
  private int totalItems = 0;

  @Override
  public HashMap<ItemImpl, Integer> getItemMap() {

    return itemMap;
  }

  @Override
  public void setItemMap(HashMap<ItemImpl, Integer> itemMap) {

    this.itemMap = itemMap;
  }

  /**
   * Updates an item:quantity pair to the map (creates new pair if item is not there, updates the
   * quantity for item if it is)
   * 
   * @param item the item to be added
   * @param quantity the (new) quantity of the item
   */
  @Override
  public void updateMap(ItemImpl item, Integer quantity) {

    boolean itemFound = false;

    for (ItemImpl key : itemMap.keySet()) {
      if (key.getId() == item.getId()) {
        itemFound = true;
        itemMap.replace(key, quantity);
      }
    }

    if (!itemFound) {
      itemMap.put(item, quantity);
    }
  }

  @Override
  public int getTotalItems() {

    return this.totalItems;
  }

  @Override
  public void setTotalItems(int total) {

    this.totalItems = total;
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
