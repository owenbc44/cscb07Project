package com.b07.inventory;

import java.io.Serializable;
import java.util.HashMap;

public interface Inventory extends Serializable {

  public HashMap<ItemImpl, Integer> getItemMap();

  public void setItemMap(HashMap<ItemImpl, Integer> itemMap);

  /**
   * Updates an item:value pair to the map (increases value if item exists, makes new pair if not)
   * 
   * @param item the item to be added
   * @param value the quantity of the item
   */
  public void updateMap(ItemImpl item, Integer quantity);

  public int getTotalItems();

  public void setTotalItems(int total);

}
