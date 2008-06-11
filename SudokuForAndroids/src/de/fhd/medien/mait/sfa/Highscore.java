package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.DialogInterface.OnClickListener; 
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class Highscore extends ListActivity
  {
    // Name of the database
    private final String DB_NAME = "sudokuForAndroids";
    // Name of the user-table
    private final String DB_HIGHSCORETABLE = "sfa_highscore";
    private int points = 0;
    ArrayList<String> results = new ArrayList<String>();
    
    public void onCreate(Bundle icicle)
      {
        super.onCreate(icicle);
        
        // create score 
        this.points = this.score();
        
        // Create empty database-object
        SQLiteDatabase db = null;
        String userName = getIntent().getStringExtra("userName");
        
        try
          {
            this.createDatabase(DB_NAME, 1, MODE_PRIVATE, null);
            
            // open database an give reference to "db"
            db = this.openDatabase(DB_NAME, null);
            
            // create table for userdatas if not already exists
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_HIGHSCORETABLE +
                        "(hs_id INT PRIMARY KEY, " +
                        "hs_user_name VARCHAR, " +
                        "hs_score)");
            
            // create a date-object for user-id
            Date date = new Date();

            if(userName != null)
              db.execSQL("INSERT INTO " + DB_HIGHSCORETABLE +
                         "(hs_id, hs_user_name, hs_score) VALUES" +
                         "('"+(date.getTime() / 1000L)+"', '"+userName+"', '"+this.points+"')");
            
            // Create a Cursor with all user-names from the database
            Cursor c = db.rawQuery("SELECT hs_user_name, hs_score " +
            		                   " FROM "+DB_HIGHSCORETABLE+
            		                   " ORDER BY hs_score DESC, hs_user_name ASC ", null);
            
            // get the column-index of the user-name-column
            int nameColumnIndex = c.getColumnIndex("hs_user_name");
            // get the column-index of the score-column
            int scoreColumnIndex = c.getColumnIndex("hs_score");

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
                        // Concatinate username and score to string
                        String user_name = c.getString(nameColumnIndex)+": "+c.getString(scoreColumnIndex); 
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
        // write highscore to view
        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.results)); 
      }

    /**
     * this method creates the Option-Menu 
     */
    public boolean onCreateOptionsMenu(final Menu menu) 
      {
        super.onCreateOptionsMenu(menu);
        
        menu.add(0, 1, "Highscore abschicken");
        menu.add(0, 2, "Liste löschen");
        menu.add(0, 3, "zurück zum Hauptmenü");
        
        return true;
      }
    
    /**
     * This method handles cliks on items of the options-menu
     */
    public boolean onOptionsItemSelected(Menu.Item item)
      {
        // send highscore to onlinedatabase
        if(item.getId() == 1)
          {
            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< DUMMY!!!! >>>>>>>>>>>>>>>>>>>>>>>
            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            
            this.showAlert("Gesendet", 1, "Highscore wurde versendet", "OK", false);
          }
        
        // delete complete list
        if(item.getId() == 2)
          {
            SQLiteDatabase db = null;
            
            try
              {
                // open database an give reference to "db"
                db = this.openDatabase(DB_NAME, null);
                
                db.execSQL("DELETE FROM " + DB_HIGHSCORETABLE);
                
                this.results.clear();
                this.showAlert("Gelöscht", 1, "Highscore wurde gelöscht", "OK", false);
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
            // write highscore to view
            this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.results));
            
//            Intent in_mainmenu = new Intent(this, UserList.class);
//            this.startActivity(in_mainmenu);
//            this.finish();
          }

        // got to mainmenu
        if(item.getId() == 3)
          {
            Intent in_mainmenu = new Intent(this, UserList.class);
            this.startActivity(in_mainmenu);
            this.finish();
          }
        return true;
      }

    /**
     * This method calculates the final score depending on
     * - level (easy [1] => 500; medium [2] => 1000; hard [3] => 1500)
     * - number of used cheats (-20pts for each time a cheat has been used)
     * - time that has been taken (-10pts for each minute)
     * 
     * @return number of total points (integer)
     */
    private int score()
      {
        // Initial points = 0
        int points = 0;

        // get all neccasary datas
        int level = getIntent().getIntExtra("level", 2);
        int cheats = getIntent().getIntExtra("cheats", 0);
        int time = getIntent().getIntExtra("time", 100);
        
        // played games on level "easy"
        if(level == 1)
          {
            points = 500;
          }
        
        // played games on level "medium"
        if(level == 2)
          {
            points = 1000;
          }
        
        // played games on level "hard"
        if(level == 3)
          {
            points = 1500;
          }
        
        // subtract time
        points -= time*10;
        
        // subtract cheats
        points -= 20*cheats;
        
        // return final number of points
        return(points);
    }
  }
