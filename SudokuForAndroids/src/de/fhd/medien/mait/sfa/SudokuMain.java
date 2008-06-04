package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SudokuMain extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        Intent next = new Intent(this, CreateUser.class);
        next.putExtra("USERNAME", "Hans");
        
        this.startSubActivity(next, 0);
        
        
        setContentView(R.layout.main);
    }
}