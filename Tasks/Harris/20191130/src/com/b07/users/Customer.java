package com.b07.users;

import java.io.Serializable;

public class Customer extends User implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -4709747993521981008L;
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  /**
   * Constructor
   * 
   * @param id
   * @param name
   * @param age
   * @param address
   */
  public Customer(int id, String name, int age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
  }

  /**
   * Constructor
   * 
   * @param id
   * @param name
   * @param age
   * @param address
   * @param authenticated
   */
  public Customer(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.authenticated = authenticated;
  }



}
