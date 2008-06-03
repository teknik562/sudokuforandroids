package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class UserList extends ListActivity
  {
    private final String DB_NAME = "sudokuForAndroids";
    private final String DB_USERTABLE = "sfa_user"; 
    
      /**
       * This onCreate-Event creates the Database and nessecary Table (if not allready exists)
       * to handle the Users
       */
      public void onCreate(Bundle icicle)
        {
          super.onCreate(icicle);
          
          SQLiteDatabase db = null;
          ArrayList<String> results = new ArrayList<String>();
          
          try
            {
              this.createDatabase(DB_NAME, 1, MODE_PRIVATE, null);
              
              // open database an give reference to "db"
              db = this.openDatabase(DB_NAME, null);
              
              // create table for userdatas if not already exists
              db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_USERTABLE +
                          "( user_id INT PRIMARY KEY, " +
                          "user_name VARCHAR)");
              
              Cursor c = db.rawQuery("SELECT user_name FROM "+DB_USERTABLE, null);
              
              int nameColumn = c.getColumnIndex("user_name");
              
              if(c != null)
                {
                  if(c.first())
                    {
                      int i = 0;
                      
                      do
                        {
                          i++;
                          String user_name = c.getString(nameColumn);
                          results.add(user_name);
                        }while(c.next());
                    }
                }
              
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
          
          this.setListAdapter(new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, results)); 
            
        }
  }
