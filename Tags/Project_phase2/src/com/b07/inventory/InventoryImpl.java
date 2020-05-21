package com.b07.inventory;

import java.util.HashMap;

public class InventoryImpl implements Inventory {

  private HashMap<Item, Integer> itemMap;
  private int totalItems = 0;

  @Override
  public HashMap<Item, Integer> getItemMap() {

    return itemMap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {

    this.itemMap = itemMap;
  }

  /**
   * Updates an item:value pair to the map (increases value if item exists, makes new pair if not)
   * 
   * @param item the item to be added
   * @param value the quantity of the item
   */
  @Override
  public void updateMap(Item item, Integer value) {

    if (itemMap.containsKey(item)) {
      itemMap.replace(item, value + itemMap.get(item));
    } else {
      itemMap.put(item, value);
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

}
