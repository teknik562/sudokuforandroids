package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


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
    ArrayList<String> results = new ArrayList<String>();
    
      /**
       * This onCreate-Event creates the Database and nessecary Table (if not allready exists)
       * to handle the Users
       */
      public void onCreate(Bundle icicle)
        {
          super.onCreate(icicle);
          
          // Create empty database-object
          SQLiteDatabase db = null;
          
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
              Cursor c = db.rawQuery("SELECT user_name FROM "+DB_USERTABLE+" ORDER BY user_name ASC", null);
              
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
                          this.results.add(user_name);
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
          this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.results)); 
            
        }
      
      /**
       * This method creates the Otions-Menu
       */
      public boolean onCreateOptionsMenu(final Menu menu) 
        {
          super.onCreateOptionsMenu(menu);
          
          menu.add(0, 1, "New Player").setIcon(R.drawable.adduser);
          
          return true;
        }
      
      /**
       * This method handles clicks on items of the options-menu
       */
      public boolean onOptionsItemSelected(Menu.Item item)
        {
          // go to input form
          if(item.getId() == 1)
            {
              Intent i = new Intent(this, UserForm.class);
              startSubActivity(i, INPUTFORM_ID);
            }
          return true;
        }
      
      /**
       * This method inserts a new user.
       * It's called when a subactivity is finished
       */
      protected void onActivityResult(int requestCode, int resultCode, String data, Bundle extras) 
        {
          super.onActivityResult(requestCode, resultCode, data, extras);
          // check if name is given
          if(data != null)
          {
          if(data.length() > 0)
            {
              // check if subactivity was input-form
              if(requestCode == INPUTFORM_ID)
                {
                  SQLiteDatabase db = null;
                  
                  try
                    {
                      this.createDatabase(DB_NAME, 1, MODE_PRIVATE, null);
                      
                      // open database an give reference to "db"
                      db = this.openDatabase(DB_NAME, null);
                          
                      // create a date-object for user-id
                      Date date = new Date();
                      
                      // insert new user in usertable
                      db.execSQL("INSERT INTO "+DB_USERTABLE+" (user_id, user_name) VALUES ('"+(date.getTime() / 1000L)+"', '"+data+"')");
                      // add new item to list
                      this.results.add(data);
                      // update list
                      this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.results)); 
                    }
                  catch(FileNotFoundException e)
                    {} 
                  finally
                    {
                      if (db != null)
                        {
                          db.close();
                        }
                    }
                }
            }
          else
            {
              showAlert("Keine Eingabe", 1, "Bitte einen Namen eingeben!", "OK", false);
            }
          }
        } 
     
      /**
       * this method handles the clicks on liste-items
       */
      protected void onListItemClick(ListView l, View v, int position, long id) 
        {
          // get username of klicked listitem
          String user = this.results.get(position);
          
          // create new intent with username as an extra
          Intent next = new Intent(this, MainMenu.class);
          next.putExtra("playerName", user);
          
          super.startActivity(next);
    } 
}
