package com.b07.store;

import java.sql.SQLException;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InvalidAddressException;
import com.b07.exceptions.InvalidAgeException;
import com.b07.exceptions.InvalidQuantityException;
import com.b07.exceptions.InvalidRoleException;
import com.b07.exceptions.ItemIdNotFoundException;
import com.b07.exceptions.RoleIdNotFoundException;
import com.b07.exceptions.UserIdNotFoundException;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.users.Customer;
import com.b07.users.Employee;

public class EmployeeInterface {

  private Employee currentEmployee = null;
  private InventoryImpl inventory = new InventoryImpl();

  /**
   * Constructor
   * 
   * @param employee
   * @param inventory
   */
  public EmployeeInterface(Employee employee, InventoryImpl inventory) {
    this.currentEmployee = employee;
    this.inventory = inventory;
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
   * @throws SQLException
   * @throws InvalidQuantityException
   * @throws ItemIdNotFoundException
   */
  public boolean restockInventory(ItemImpl item, int quantity)
      throws ItemIdNotFoundException, InvalidQuantityException, SQLException {

    DatabaseUpdateHelper.updateInventoryQuantity(quantity, item.getId());
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
    DatabaseInsertHelper.insertUserRole(userId, 3);
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
    DatabaseInsertHelper.insertUserRole(userId, 2);
    return userId;
  }

  public int createAccount(Customer customer)
      throws UserIdNotFoundException, DatabaseInsertException, SQLException {

    return DatabaseInsertHelper.insertAccount(customer.getId());
  }

  public boolean authenticateEmployee(Employee employeeToAuthenticate, String employeePassword)
      throws SQLException {

    return employeeToAuthenticate.authenticate(employeePassword);
  }


}
