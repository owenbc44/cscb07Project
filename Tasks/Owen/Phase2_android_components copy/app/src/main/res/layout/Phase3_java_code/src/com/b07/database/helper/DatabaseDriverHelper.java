package com.b07.database.helper;

import java.sql.Connection;
import com.b07.database.DatabaseDriver;


/**
 * @author Asad Ali Kazim
 *
 */
public class DatabaseDriverHelper extends DatabaseDriver {

  protected static Connection connectOrCreateDataBase() {
    return DatabaseDriver.connectOrCreateDataBase();
  }

}
