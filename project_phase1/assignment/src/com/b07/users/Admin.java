package com.b07.users;

import java.sql.SQLException;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.InvalidRoleException;
import com.b07.exceptions.RoleIdNotFoundException;

public class Admin extends User {

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
  public Admin(int id, String name, int age, String address) {
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
  public Admin(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.authenticated = authenticated;
  }

  /**
   * Use this to promote any other employee to the role o ADMIN.
   * 
   * @param employee the employee to be promoted
   * @return true if successful, false otherwise.
   */
  public boolean promoteEmployee(Employee employee) {
    int roleId = employee.getRoleId();
    try {
      DatabaseUpdateHelper.updateRoleName("ADMIN", roleId);
    } catch (InvalidRoleException | RoleIdNotFoundException | SQLException e) {
      return false;
    }

    return true;
  }


}
