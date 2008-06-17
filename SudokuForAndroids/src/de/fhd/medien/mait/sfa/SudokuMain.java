package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SudokuMain extends Activity 
  {
    public void onCreate(Bundle icicle) 
      {
        super.onCreate(icicle);
        
        Intent next = new Intent(this, UserList.class);
        this.startActivity(next);
      }
  }