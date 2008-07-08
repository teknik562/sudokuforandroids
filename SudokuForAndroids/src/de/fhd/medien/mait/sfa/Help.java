package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.os.Bundle;

/**
 * This Activity displays guidance information
 */
public class Help extends Activity{
	
	
	/** Called when the activity is first created. 
	 * Sets content to guidance information. */
    @Override
    public void onCreate(Bundle icicle) {
    	super.onCreate(icicle);
    	setContentView(R.layout.help);
    }
}