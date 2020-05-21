package com.b07.users;

public class Customer extends User {

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
    // TODO Auto-generated constructor stub
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
    // TODO Auto-generated constructor stub
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.authenticated = authenticated;
  }



}
