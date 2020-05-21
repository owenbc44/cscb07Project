package com.example.myapplication.database.helper;

import com.example.myapplication.database.DatabaseDriver;

import java.sql.Connection;


/**
 * @author Asad Ali Kazim
 *
 */
public class DatabaseDriverHelper extends DatabaseDriver {

  protected static Connection connectOrCreateDataBase() {
    return DatabaseDriver.connectOrCreateDataBase();
  }

}
