package com.b07.users;

/**
 * @author Asad Ali Kazim
 *
 */
public class UserImpl extends User {


  private int id;

  private String name;

  private int age;

  private String address;

  private int roleId;

  private boolean authenticated;

  /**
   * Constructor for UserImpl.
   * 
   * @param id
   * @param name
   * @param age
   * @param address
   */
  public UserImpl(int id, String name, int age, String address) {
    // TODO Auto-generated constructor stub
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
  }

}
