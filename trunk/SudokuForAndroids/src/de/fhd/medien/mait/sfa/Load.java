package de.fhd.medien.mait.sfa;

import java.io.File;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Load extends ListActivity{
	
	String[] directory;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle); 
        
	File f = new File("/data/data/de.fhd.medien.mait.sfa/files");
	directory = f.list();
	
	this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.directory));
	
    }
}
