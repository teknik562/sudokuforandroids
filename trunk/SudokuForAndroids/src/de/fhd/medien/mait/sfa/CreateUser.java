package de.fhd.medien.mait.sfa;

import java.io.FileNotFoundException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class CreateUser extends Activity
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
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_USERTABLE +
                        "( user_id INT PRIMARY KEY, " +
                        "user_name VARCHAR)");

            Date date = new Date();
            
            // create table for userdatas if not already exists
            db.execSQL("INSERT INTO "+DB_USERTABLE+" (user_id, user_name) VALUES ('"+(date.getTime() / 1000L)+"', '"+this.getIntent().getExtra("USERNAME")+"')");
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
        
        setContentView(R.layout.main);
        startSubActivity(new Intent(this, UserList.class), 0);
}
  }
