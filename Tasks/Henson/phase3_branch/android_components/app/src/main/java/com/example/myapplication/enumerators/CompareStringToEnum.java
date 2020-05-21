package com.example.myapplication.enumerators;

public class CompareStringToEnum {

  public static boolean checkItemType(String str) {
    ItemTypes[] allItems = ItemTypes.values();

    for (ItemTypes item : allItems) {
      if (item.toString().equals(str)) {
        return true;
      }
    }

    return false;
  }

  public static boolean checkRole(String str) {
    Roles[] allRoles = Roles.values();

    for (Roles role : allRoles) {
      if (role.toString().equals(str)) {
        return true;
      }
    }

    return false;
  }

}
