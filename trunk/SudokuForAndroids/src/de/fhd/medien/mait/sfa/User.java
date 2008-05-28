package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class User extends ListActivity
  {
    private final String DB_NAME = "sudokuForAndroids";
    private final String DB_USERTABLE = "sfa_user"; 
    
      public void onCreate(Bundle icicle)
        {
          super.onCreate(icicle);
          
          SQLiteDatabase db = null;
          
          try
            {
              this.createDatabase(DB_NAME, 1, MODE_PRIVATE, null);
              
              // open database an give reference to "db"
              db = this.openDatabase(DB_NAME, null);
              
              // create table for userdatas if not already exists
              db.execSQL("CREATE TABLE IF NOT EXISTS" + DB_USERTABLE +
                          "user_id INT PRIMARY KEY," +
                          "user_name VARCHAR");
            }
          catch(FileNotFoundException e)
            {
              
            } 
          finally
            {
              if (db != null)
                {
                  db.close();
                }
            }
            
        }
        
  }
