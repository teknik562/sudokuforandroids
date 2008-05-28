package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class DatabaseConnect extends ListActivity
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
