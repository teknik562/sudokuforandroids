package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * this is the class where sudoku is launched. It starts the User List
 * @author Dominik
 *
 */
public class SudokuMain extends Activity 
  {
    public void onCreate(Bundle icicle) 
      {
        super.onCreate(icicle);
        
        Intent next = new Intent(this, UserList.class);
        this.startActivity(next);
        this.finish();
      }
  }