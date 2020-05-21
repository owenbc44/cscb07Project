package com.b07.users;

public class Employee extends User {

  /**
   * 
   */
  private static final long serialVersionUID = -8231377341908279905L;
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
