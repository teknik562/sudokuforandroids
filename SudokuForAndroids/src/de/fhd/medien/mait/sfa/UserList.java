package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;


/**
 * Activity that displays the List of Users.
 * If not allready exists this class will create the usertable.
 * The usertable contains two columns ("user_id" & "user_name")
 * 
 * @author dac-xp
 *
 */
public class UserList extends ListActivity
  {
    // Name of the database
    private final String DB_NAME = "sudokuForAndroids";
    // Name of the user-table
    private final String DB_USERTABLE = "sfa_user";
    // identifier of the subactivity with the input-form
    protected static final int INPUTFORM_ID = 4711; 
    
      /**
       * This onCreate-Event creates the Database and nessecary Table (if not allready exists)
       * to handle the Users
       */
      public void onCreate(Bundle icicle)
        {
          super.onCreate(icicle);
          
          // Create empty database-object
          SQLiteDatabase db = null;
          // Arraylist that will contain user-names
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
              
              // Create a Cursor with all user-names from the database
              Cursor c = db.rawQuery("SELECT user_name FROM "+DB_USERTABLE, null);
              
              // get the column-index of the user-name-column
              int nameColumnIndex = c.getColumnIndex("user_name");

              // check if the database-cursor references not to "null"
              if(c != null)
                {
                  // check if the database-cursor is not empty
                  if(c.first())
                    {
                      int i = 0;

                      // write user-names to the array-list 
                      do
                        {
                          i++;
                          String user_name = c.getString(nameColumnIndex);
                          results.add(user_name);
                        }while(c.next());
                    }
                }
              
            }
          catch(FileNotFoundException e)
            {} 
          finally
            {
              // close database if open
              if(db != null)
                {
                  db.close();
                }
            }
          
          // write user-names to view
          this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results)); 
            
        }
      
      public boolean onCreateOptionsMenu(final Menu menu) 
        {
          super.onCreateOptionsMenu(menu);
          
          menu.add(0, 1, "Neuen Benutzer erstellen");
          
          return true;
        }
      
      public boolean onOptionsItemSelected(Menu.Item item)
        {
          if(item.getId() == 1)
            {
              Intent i = new Intent(this, UserForm.class);
              startSubActivity(i, INPUTFORM_ID);
            }
          return true;
        }
  }
