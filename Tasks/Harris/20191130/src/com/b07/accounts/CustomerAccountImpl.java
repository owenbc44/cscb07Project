package com.b07.accounts;

import com.b07.store.ShoppingCart;
import com.b07.users.Customer;
import com.b07.users.User;

public class CustomerAccountImpl implements Account {

  private int id;
  private ShoppingCart cart;
  private Customer customer;

  public CustomerAccountImpl(ShoppingCart cart, Customer customer, int id) {

    this.cart = cart;
    this.customer = customer;
    this.id = id;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Customer getUser() {
    return this.customer;
  }

  @Override
  public void setUser(User user) {
    this.customer = (Customer) user;
  }

  @Override
  public ShoppingCart getCart() {
    return this.cart;
  }

  @Override
  public void setCart(ShoppingCart cart) {

    this.cart = cart;
  }

}
