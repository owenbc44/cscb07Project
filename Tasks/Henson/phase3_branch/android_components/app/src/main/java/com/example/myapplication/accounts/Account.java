package com.example.myapplication.accounts;

import com.example.myapplication.store.ShoppingCart;
import com.example.myapplication.users.User;
import com.example.myapplication.store.ShoppingCart;

public interface Account {
  
  public int getId();
  
  public void setId(int id);
  
  public User getUser();
  
  public void setUser(User user);
  
  public ShoppingCart getCart();
  
  public void setCart(ShoppingCart cart);
  
}
