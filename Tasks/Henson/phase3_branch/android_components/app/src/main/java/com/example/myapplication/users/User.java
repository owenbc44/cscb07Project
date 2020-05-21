package com.example.myapplication.users;

import com.example.myapplication.database.helper.DatabaseSelectHelper;
import com.example.myapplication.security.PasswordHelpers;

import java.sql.SQLException;


public abstract class User {
  // TODO: Complete this class based on UML provided on the assignment sheet.

  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRoleId() {
    return this.roleId;
  }

  /**
   * Use this to authenticate a user password provided
   * 
   * @param password the provided unhashed password
   * @return true if the password matches the one in the database, false otherwise.
   * @throws SQLException if something goes wrong with the database tasks.
   */
  public final boolean authenticate(String password) throws SQLException {

    String hashed = DatabaseSelectHelper.getPassword(this.id);

    if (hashed == null) {
      authenticated = false;
      return false;
    }

    authenticated = PasswordHelpers.comparePassword(hashed, password);
    return authenticated;


  }
}
