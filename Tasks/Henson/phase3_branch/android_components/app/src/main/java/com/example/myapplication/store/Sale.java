package com.example.myapplication.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.example.myapplication.inventory.ItemImpl;
import com.example.myapplication.users.User;

public interface Sale {

  public int getId();

  public void setId(int id);

  public User getUser();

  public void setUser(User user);

  public BigDecimal getTotalPrice();

  public void setTotalPrice(BigDecimal price);

  public HashMap<ItemImpl, Integer> getItemMap();

  public void setItemMap(HashMap<ItemImpl, Integer> itemMap);


}
