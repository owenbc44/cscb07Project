package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

public class SaleImpl implements Sale {

  private int id;
  private User user;
  private BigDecimal totalPrice;
  private HashMap<Item, Integer> itemMap = new HashMap<Item, Integer>();

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public User getUser() {
    return this.user;
  }

  @Override
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public BigDecimal getTotalPrice() {
    return this.totalPrice;
  }

  @Override
  public void setTotalPrice(BigDecimal price) {
    this.totalPrice = price;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    return this.itemMap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.itemMap = itemMap;

  }

}
