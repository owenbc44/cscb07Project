package com.b07.serialization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class Serialization {

  public static void serialize(DatabaseInOneObject o)
      throws SQLException, FileNotFoundException, IOException {
    try {
      FileOutputStream fileOut = new FileOutputStream("theEntireDatabase.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(o);
      out.close();
      fileOut.close();
      System.out.println(
          "Serialization complete! The serialized file is called 'theEntireDatabase.ser' ");
    } catch (IOException i) {
      i.printStackTrace();
      System.out.println("Serialization failed, please try again");
    }


  }


}
