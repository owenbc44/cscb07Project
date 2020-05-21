package com.b07.store;

import java.sql.SQLException;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InvalidAddressException;
import com.b07.exceptions.InvalidAgeException;
import com.b07.exceptions.InvalidRoleException;
import com.b07.exceptions.RoleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Employee;

public class EmployeeInterface {

  private Employee currentEmployee = null;
  private Inventory inventory;

  /**
   * Constructor
   * 
   * @param employee
   * @param inventory
   */
  public EmployeeInterface(Employee employee, Inventory inventory) {
    this.currentEmployee = employee;
    this.inventory = inventory;
  }

  /**
   * Constructor
   * 
   * @param inventory
   */
  public EmployeeInterface(Inventory inventory) {
    this.inventory = inventory;
  }

  public void setCurrentEmployee(Employee employee) {
    this.currentEmployee = employee;
  }

  /**
   * @return true if there is a current employee (current employee is not null).
   */
  public boolean hasCurrentEmployee() {
    return this.currentEmployee != null;
  }

  /**
   * Restocks the inventory amount of item with quantity.
   * 
   * @param item the item to be restocked.
   * @param quantity the quantity to be added.
   * @return true if it works, false otherwise.
   */
  public boolean restockInventory(Item item, int quantity) {
    inventory.updateMap(item, quantity);
    return true;
  }

  /**
   * Creates a new customer in the database.
   * 
   * @param name the name of the customer
   * @param age the age of the customer
   * @param address the address of the customer
   * @param password the password (unhashed) of the customer
   * @return returns the userId of the new customer
   * @throws InvalidAddressException
   * @throws InvalidAgeException
   * @throws DatabaseInsertException
   * @throws SQLException
   * @throws InvalidRoleException
   * @throws UserIdNotFoundException
   * @throws RoleIdNotFoundException
   */
  public int createCustomer(String name, int age, String address, String password)
      throws InvalidAddressException, InvalidAgeException, DatabaseInsertException, SQLException,
      InvalidRoleException, UserIdNotFoundException, RoleIdNotFoundException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
    int roleId = DatabaseInsertHelper.insertRole("CUSTOMER");
    DatabaseInsertHelper.insertUserRole(userId, roleId);
    return userId;
  }

  /**
   * Creates new employee in the database
   * 
   * @param name the name of the employee
   * @param age the age of the employee
   * @param address the address of the employee
   * @param password the password of the employee
   * @return the userId for the new employee
   * @throws InvalidAddressException
   * @throws InvalidAgeException
   * @throws DatabaseInsertException
   * @throws SQLException
   * @throws InvalidRoleException
   * @throws UserIdNotFoundException
   * @throws RoleIdNotFoundException
   */
  public int createEmployee(String name, int age, String address, String password)
      throws InvalidAddressException, InvalidAgeException, DatabaseInsertException, SQLException,
      InvalidRoleException, UserIdNotFoundException, RoleIdNotFoundException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
    int roleId = DatabaseInsertHelper.insertRole("EMPLOYEE");
    DatabaseInsertHelper.insertUserRole(userId, roleId);
    return userId;
  }


}
