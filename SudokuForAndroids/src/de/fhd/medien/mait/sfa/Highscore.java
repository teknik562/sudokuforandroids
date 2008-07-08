package de.fhd.medien.mait.sfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
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
        String userName = Config.playerName;
        
        try
          {
            this.createDatabase(DB_NAME, 1, MODE_PRIVATE, null);
            
            // open database an give reference to "db"
            db = this.openDatabase(DB_NAME, null);
            
            // create table for userdatas if not already exists
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_HIGHSCORETABLE +
                        "(hs_id INTEGER PRIMARY KEY, " +
                        "hs_user_name VARCHAR, " +
                        "hs_score)");
            
            // create a date-object for user-id
            Date date = new Date();

            
            if(userName != null && getIntent().getIntExtra("fromMenu", 0) != 1)
              db.execSQL("INSERT INTO " + DB_HIGHSCORETABLE +
                         "(hs_id, hs_user_name, hs_score) VALUES" +
                         "('"+(date.getTime() / 1000L)+"', '"+userName+"', '"+this.points+"')");
            
            // Create a Cursor with all user-names and scores from the database
            Cursor c = db.query(DB_HIGHSCORETABLE, new String[] {"hs_id", "hs_user_name", "hs_score"},null, null, null, null, "hs_score DESC");

            // get the column-index of the user-name-column
            int nameColumnIndex = c.getColumnIndex("hs_user_name");
            // get the column-index of the score-column
            int scoreColumnIndex = c.getColumnIndex("hs_score");

            // check if the database-cursor references not to "null"
            if(c != null)
              {
                String[][] highscoreList = new String[c.count()][2];
                
                // check if the database-cursor is not empty
                if(c.first())
                  {
                    int i = 0;

                    // write user-names and score to the array
                    do
                      {
                        highscoreList[i][0] = c.getString(nameColumnIndex);
                        highscoreList[i][1] = c.getString(scoreColumnIndex);
                        
                        i++;
                        
                      }while(c.next());

                    // Sort Highscore by Score (SQLite can't sort by Integer...)
                    for(int k = 0; k < highscoreList.length; k++)
                      for(int j = 0; j < highscoreList.length-1; j++)
                        {
                          if(Integer.parseInt(highscoreList[j][1]) < Integer.parseInt(highscoreList[j+1][1]))
                            {
                              String tempName = highscoreList[j+1][0];
                              String tempScore = highscoreList[j+1][1];
                              
                              highscoreList[j+1][0] = highscoreList[j][0]; 
                              highscoreList[j+1][1] = highscoreList[j][1];
                              
                              highscoreList[j][0] = tempName; 
                              highscoreList[j][1] = tempScore; 
                            }
                        }
                    
                    // Concatinate username and score to string
                    for(int k = 0; k < highscoreList.length; k++)
                      {
                        String user_name = highscoreList[k][0]+": "+highscoreList[k][1]; 
                        this.results.add(user_name);
                      }
                    
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
        
        menu.add(0, 1, "Upload highscore").setIcon(R.drawable.online);
        menu.add(0, 2, "Delete highscore").setIcon(R.drawable.delete);
        menu.add(0, 3, "Main menu").setIcon(R.drawable.mainmenu);
        
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
            SQLiteDatabase db = null;
            try
              {
                db = this.openDatabase(DB_NAME, null);
              }
            catch(FileNotFoundException e)
              {}

            String highscoreString = "";
            
            // Create a Cursor with all user-names and scores from the database
            Cursor c = db.query(DB_HIGHSCORETABLE, new String[] {"hs_id", "hs_user_name", "hs_score"},null, null, null, null, "hs_score DESC");

            // get the column-index of the id-column
            int idColumnIndex = c.getColumnIndex("hs_id");
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
                        highscoreString += c.getString(nameColumnIndex)+":"+c.getString(scoreColumnIndex)+";"; 
                      }while(c.next());
                  }
              }
            
            try
              {
                String data = URLEncoder.encode("data", "UTF-8") + "=" + 
                URLEncoder.encode(highscoreString, "UTF-8"); 

                URL url = new URL("http://dac-xp.com/sfa_highscore/highscore.php"); 
                URLConnection conn = url.openConnection(); 
                conn.setDoOutput(true); 
                conn.setRequestProperty("METHOD", "POST"); 
                OutputStreamWriter wr = new 
                OutputStreamWriter(conn.getOutputStream()); 
                wr.write(data); 
                wr.flush(); 

                // Get the response 
                BufferedReader rd = new BufferedReader(new 
                InputStreamReader(conn.getInputStream())); 
                String line; 
                while ((line = rd.readLine()) != null) 
                  {}

                wr.close(); 

                Intent in_onlineHighscore = new Intent(this, OnlineHighscore.class);
                this.startActivity(in_onlineHighscore);
              }
            catch(Exception e)
              {
                this.showAlert("Submitted", 1, e.getMessage(), "OK", false);
              }
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
                this.showAlert("Deleted", 1, "Highscore has been deleted", "OK", false);
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
            Intent in_mainmenu = new Intent(this, MainMenu.class);
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
    public static int score()
      {
        // Initial points = 0
        int points = 0;

        // get all neccasary datas
        int level = Config.difficulty;
        int cheats = Config.cheatCount;
        int time = Config.time;
        
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
        points -= (time*10) + Config.neededTime;
        
        // subtract cheats
        points -= 20*cheats;
        
        // return final number of points
        return(points);
    }
  }

