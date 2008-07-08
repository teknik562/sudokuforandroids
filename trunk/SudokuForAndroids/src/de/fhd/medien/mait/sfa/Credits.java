package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.os.Bundle;

/**
 * This Activity displays the text for the Credits.
 * 
 */
public class Credits extends Activity{
	
	
	/** Called when the activity is first created.
	 * Displays the text for the credits. */
    @Override
    public void onCreate(Bundle icicle) {
    	super.onCreate(icicle);
    	setContentView(R.layout.credits);
    }
}
