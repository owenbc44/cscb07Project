package com.b07.accounts;

import java.io.Serializable;
import com.b07.store.ShoppingCart;
import com.b07.users.User;

public interface Account extends Serializable {

  public int getId();

  public void setId(int id);

  public User getUser();

  public void setUser(User user);

  public ShoppingCart getCart();

  public void setCart(ShoppingCart cart);

}
