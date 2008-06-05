package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class SudokuMain extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        // get the info from the display and calculate layout
        calculateProperties();
        
        Intent next = new Intent(this, CreateUser.class);
        next.putExtra("USERNAME", "Hans");
        
        this.startSubActivity(next, 0);
        
        
        
        setContentView(R.layout.main);
    }
    
    /** 
     *  Calculates layout properties and stores them in the config.
     */ 
    private void calculateProperties(){
    	WindowManager a = this.getWindowManager();
        Display v = a.getDefaultDisplay();
        
        // calculates the minimumscreenAttr
        int min = Math.min(v.getHeight(),v.getWidth());
        if (min == v.getHeight())
        	min = min - 50;
        
        // calculates the Font Size for Fieldbuttons
        // if the minimum ScreenAttr is less 250 it will be 12
        // otherwise it'll be 16
        int FBfontSize;
        if (min < 250)
        	FBfontSize = 12;
        else
        	FBfontSize = 16;
        
        Config.initiate(v.getWidth(),			// displayWidth
        		v.getHeight() - 50,				// displayHeight
        		min,							// minScreenAttr
        		min / 10,						// optFieldBtSize 
        		min / 40,						// optFieldPadding
        		min / 120,						// optFieldLine
        		9 * (min / 10) + 2 * (min / 40),// fieldLength
        		(v.getWidth() - (9 * (min / 10) + 2 * (min / 40)) ) / 2,	// fieldStartXPos
        		((v.getHeight() - 50) - (9 * (min / 10) + 2 * (min / 40)) ) / 2,	// fieldStartYPos
        		FBfontSize						// optFieldBtFontSize
        		);
    }
    
}