package com.b07.inventory;

import java.util.HashMap;

public interface Inventory {

  public HashMap<Item, Integer> getItemMap();

  public void setItemMap(HashMap<Item, Integer> itemMap);

  /**
   * Updates an item:value pair to the map (increases value if item exists, makes new pair if not)
   * 
   * @param item the item to be added
   * @param value the quantity of the item
   */
  public void updateMap(Item item, Integer value);

  public int getTotalItems();

  public void setTotalItems(int total);

}
