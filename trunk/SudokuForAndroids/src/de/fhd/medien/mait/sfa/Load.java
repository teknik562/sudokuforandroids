package de.fhd.medien.mait.sfa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This activity displays all savegames by the user and lets him choose one to go on playing.
 * If there's no savegame by the player only a message will be shown.
 * @author thedeftone
 *
 */
public class Load extends ListActivity{

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle); 
    String[] directory;   
    try{
	File f = new File("/data/data/de.fhd.medien.mait.sfa/files");
	directory = f.list();
	
	// Only savegames by the player will be shown, others are hided
	ArrayList<String> tempDirectory = new ArrayList<String>();
	for(String element : directory){
		// Savegames to be displayed must start with "Playername "
		if(element.startsWith(Config.playerName + " "))
			tempDirectory.add(element);
	}
	String[] userDirectory = new String[tempDirectory.size()];
	tempDirectory.toArray(userDirectory);
	
	// If there are no savegames, a message will be shown
	if (userDirectory.length == 0){
		Toast.makeText(this, "No savegames yet", Toast.LENGTH_SHORT).show();
		try{
			this.wait(Toast.LENGTH_SHORT);
			finish();
		}catch(Exception e){
			finish();
		}
	// if there are savegames by this user, a list will be shown
	}else{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userDirectory);
		this.setListAdapter(adapter);
	}
    }
    catch(Exception ex){
    	Toast.makeText(this, "No savegames yet", Toast.LENGTH_SHORT).show();
    }
    }
    
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
     super.onListItemClick(l, v, position, id); 
     
     // Get the item that was clicked
     Object o = this.getListAdapter().getItem(position);
     String fileName = o.toString();
     Log.d("Chosen Savegame", fileName);
     try{
         BufferedReader br = 	new BufferedReader(
        		 				new InputStreamReader(
        		 				openFileInput(fileName)));    	 
    	 // load all liness
         String solvedFieldString = br.readLine();
    	 String originalFieldString = br.readLine();
    	 String userManipulatedFieldString = br.readLine();
    	 String candidateString = br.readLine();
    	 String nameString = br.readLine();
    	 String levelString = br.readLine();
    	 String sumTimeString = br.readLine();
    	 Config.cheatCount = Integer.parseInt(br.readLine());
    	 
    	 br.close();
    	 //Config.loadedScore = loadedObject;
    	 Intent loadGame = new Intent(Load.this, Field.class);
    	 loadGame.putExtra("starter", "LOAD");
    	 
    	 // pass the Strings to the field
    	 loadGame.putExtra("sfs", solvedFieldString);
    	 loadGame.putExtra("ofs", originalFieldString);
    	 loadGame.putExtra("umfs", userManipulatedFieldString);
    	 loadGame.putExtra("cs", candidateString);
    	 loadGame.putExtra("ns", nameString);
    	 loadGame.putExtra("ls", levelString);
    	 loadGame.putExtra("sts", sumTimeString);
    	 loadGame.putExtra("fileName", fileName);
    	 
    	 Log.d("Loading", "Loading successful");
    	 startActivity(loadGame);
    	 this.finish();
     } catch(FileNotFoundException fnf){
    	 Toast.makeText(this, "File not found, is allready deleted.\n" +
      	 		"Delete it or reload.", Toast.LENGTH_SHORT).show(); 
     } catch(Exception e){
    	 Log.d("Loading", "Loading failed");
    	 Toast.makeText(this, "There's a problem with this file. " +
    	 		"Please go back to main menu", Toast.LENGTH_SHORT).show();
     }
     
    }
    
    public void reload(){
    	String[] directory;   
    	File f = new File("/data/data/de.fhd.medien.mait.sfa/files");
    	directory = f.list();
    	
    	// Only savegames by the player will be shown, others are hided
    	ArrayList<String> tempDirectory = new ArrayList<String>();
    	for(String element : directory){
    		// Savegames to be displayed must start with "Playername "
    		if(element.startsWith(Config.playerName + " "))
    			tempDirectory.add(element);
    	}
    	String[] userDirectory = new String[tempDirectory.size()];
    	tempDirectory.toArray(userDirectory);
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userDirectory);
    	this.setListAdapter(adapter);
    }
    
    /**
     * This method creates the Otions-Menu
     */
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        menu.add(0, 1, "Delete selected savegame").setIcon(R.drawable.delete);
        
        return true;
    }
    
    /**
     * This method handles clicks on items of the options-menu
     */
    public boolean onOptionsItemSelected(Menu.Item item){
        
        if(item.getId() == 1){
        	try{
        		
        		new AlertDialog.Builder(this)
        		.setTitle("Delete")
        		.setMessage("Are you sure you want to delete that file?")
        		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int whichButton) {
        				String chosenFile = Load.this.getListView().getSelectedItem().toString();
                		if (deleteFile(chosenFile)){
                			Toast.makeText(Load.this, chosenFile + " deleted", Toast.LENGTH_SHORT).show();
                			Load.this.reload();
                		}
                		else
                			Toast.makeText(Load.this, "Can't delete that file", Toast.LENGTH_SHORT).show();
        			}
        		})
        		.setNegativeButton("No", new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int whichButton) {

        			}
        		})
        		.show();
        		
        		
        		
        	}catch(Exception e){
        		Toast.makeText(this, "You need to choose a file first", Toast.LENGTH_SHORT).show();
        	} 
        } 
        
        
        

        return true;
    }
}
