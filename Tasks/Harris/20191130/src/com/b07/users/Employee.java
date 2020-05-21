package com.b07.users;

import java.io.Serializable;

public class Employee extends User implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -1874717577968758335L;
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  /**
   * Concstructor
   * 
   * @param id
   * @param name
   * @param age
   * @param address
   */
  public Employee(int id, String name, int age, String address) {
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
  public Employee(int id, String name, int age, String address, boolean authenticated) {
    // TODO Auto-generated constructor stub
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.authenticated = authenticated;
  }


}
