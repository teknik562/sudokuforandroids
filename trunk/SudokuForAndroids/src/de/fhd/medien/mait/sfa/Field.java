package de.fhd.medien.mait.sfa;


import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * This is where the magic will happen ;)
 * Here the Field will be displayed, a Sudoku puzzle will be requested...
 */
public class Field extends Activity{
	
	//the code to identify the Keypad, when it returns to this activity
	private static final int KEYPAD_REQUEST_CODE = 0;
	
	// Shows wether the game was loaded or not
	private boolean gameWasLoaded = false; 
	// File Name of the loaded File
	private String fileNameloaded = "";
	
	// Layout for the field
	private AbsoluteLayout fieldLayout = new AbsoluteLayout(this);
	private static final int length = 9;
	private static final int nrCandidates = 6;
	private FieldButton[][] buttonField = new FieldButton[length][length];
	/** 
	 * represents the Field with non changeable values
	 * Comes from the Algo as maskedField() 
	 */
	int[][] originalField = new int[length][length];
	/** 
	 * represents the Field with the right solution 
	 */
	private int[][] solvedField = new int[length][length];
	/** 
	 * represents the Field the user has manipulated.
	 * Is only set when the game was loaded. For the purpose of
	 * checking if the game was solved correctly it has to be
	 * calculated.
	 * If this field is equal to solvedField, the game is over.
	 */
	private int[][] userManipulatedField = new int[length][length];
	
	
	
	ProgressDialog pd = null; 
	
	Thread normal = new Thread()
    {
           public void run()
           {
        	   // request a puzzle basing on the set difficulty
        	   Algo normal = new Algo();
        	   normal.Algo(Config.difficulty);
        	   userManipulatedField = normal.getMaskedField();
        	   originalField = normal.getMaskedField();
        	   solvedField = normal.getFilledField();
        	   // create the field
        	   
               createField();
               for(int x = 0; x < buttonField.length;  x++)
               	for(int y = 0; y < buttonField[x].length; y++)
               		if(buttonField[x][y].changeable == true){
               			buttonField[x][y].setOnClickListener(fieldClick);
               		}
               
               //when done
        	   pd.dismiss(); 
           }

    }; 
    
    Thread loaded = new Thread()
    {
           public void run()
           {
        	   Config.playerName = getIntent().getStringExtra("ns");
        	   Config.difficulty = Integer.parseInt(getIntent().getStringExtra("ls"));
        	   Config.neededTime = Long.parseLong(getIntent().getStringExtra("sts"));
        	   String solvedFieldString = getIntent().getStringExtra("sfs");
        	   String originalFieldString = getIntent().getStringExtra("ofs");
        	   String userManipulatedFieldString = getIntent().getStringExtra("umfs");

        	   Log.d("OFS", originalFieldString);
        	   int counter = 0;

        	   for(int i = 0; i < length; i++)
        		   for(int j = 0; j < length; j++){
        			   solvedField[i][j] = Character.getNumericValue(solvedFieldString.charAt(counter));
        			   originalField[i][j] = Character.getNumericValue(originalFieldString.charAt(counter));
        			   userManipulatedField[i][j] = Character.getNumericValue(userManipulatedFieldString.charAt(counter));
        			   counter++;
        		   }
        	   
        			   
        	   // request a puzzle basing on the set difficulty
        	   // create the field
               createField();
               
        	   String candidateString = getIntent().getStringExtra("cs");
        	   int candidateCounter = 0;
        	   boolean hasCandidates = false;
               
               for(int x = 0; x < buttonField.length;  x++)
               	for(int y = 0; y < buttonField[x].length; y++){
               		buttonField[x][y].setValue(userManipulatedField[x][y]);
               		if(buttonField[x][y].changeable == true){
               			buttonField[x][y].setOnClickListener(fieldClick);
               		}
               		hasCandidates = false;
               		for(int c = 0; c < nrCandidates; c++){
               			int actCandidate = Character.getNumericValue(candidateString.charAt(candidateCounter));
               			buttonField[x][y].setCandidateValue(c, actCandidate);
               			if(actCandidate != 0)
               				hasCandidates = true;
               			candidateCounter++;
               		}
               		if(hasCandidates)
               			buttonField[x][y].setAsCandidate();
               	}
               
               //when done
        	   pd.dismiss(); 
           }

    }; 
    
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle); 
        
        // set the start time
        Config.startTime = new Date().getTime();
        
        String starter = "NORMAL";
        try{
        	starter = this.getIntent().getStringExtra("starter");
        } catch(Exception e){
        	Toast.makeText(this, e.getClass().toString(), Toast.LENGTH_SHORT).show();
        }

        pd = ProgressDialog.show(Field.this,     
                "Please wait...", "Generating Sudoku", true);    
    
        // initiate the Layout
        fieldLayout.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
        
        Handler handler = new Handler(); 
        
        if(starter.equals("NORMAL"))
        	handler.post(normal);
        else{
        	fileNameloaded = getIntent().getStringExtra("fileName");
        	gameWasLoaded = true;
        	handler.post(loaded);
        }
        
        Log.d("Chosen difficulty", Integer.toString(Config.difficulty));
        Log.d("Mode", starter + " Mode");	
        // request a puzzle basing on the set difficulty

        
        

        // make the field visible
        setContentView(fieldLayout);
        
    }
    
   
    
    /**
     * this is the onClickListener for each field. The values of the candidates are 
     * put into a bundle 
     */
    OnClickListener fieldClick = new OnClickListener()
    {

		//@Override
		public void onClick(View v) {
			FieldButton clickedField = (FieldButton) v;
			
			if(clickedField.changeable == true)
			{
				clickedField.setClicked();
				
				Bundle b = new Bundle();
				
				b.putInt("C1", clickedField.getCandidate(0));
				b.putInt("C2", clickedField.getCandidate(1));
				b.putInt("C3", clickedField.getCandidate(2));
				b.putInt("C4", clickedField.getCandidate(3));
				b.putInt("C5", clickedField.getCandidate(4));
				b.putInt("C6", clickedField.getCandidate(5));
				
				
				
				Intent startKeyPad = new Intent(Field.this, KeyPad.class);
				startKeyPad.putExtras(b);
				
				startSubActivity(startKeyPad, KEYPAD_REQUEST_CODE);
				
			}
				
		}
    	
    };
    
    /**
     * this method reacts on Keyevents.
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		super.onKeyDown(keyCode, event);
		
		if(isAnyFieldFocused())
		{
			FieldButton focusedField = focusedField();
			
			switch( keyCode)
			{
				case KeyEvent.KEYCODE_0:
					focusedField.setValue(0);
					focusedField.deleteCandidates();
					focusedField.refreshDrawableState();
					
					break;
				
				case KeyEvent.KEYCODE_1:
					focusedField.setValue(1);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_2:
					focusedField.setValue(2);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_3:
					focusedField.setValue(3);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_4:
					focusedField.setValue(4);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_5:
					focusedField.setValue(5);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_6:
					focusedField.setValue(6);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_7:
					focusedField.setValue(7);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_8:
					focusedField.setValue(8);
					
					focusedField.refreshDrawableState();
					break;
					
				case KeyEvent.KEYCODE_9:
					focusedField.setValue(9);
					
					focusedField.refreshDrawableState();
					break;
			}// end switch
		}//end method
		
		
		return false;
	}
    
    
    /**
     * Creates a Field to play on!
     * At the moment there's only a dummy field but when the first example puzzles
     * are there, this method will build up a working field! 
     */
    private void createField(){
    	// counts how often vertical padding is set (each 3 lines)
        int countouter = 0;
        for (int i = 0; i < length; i++ ){
        	// counts how often horizontal padding is set (each 3 lines)
        	int countinner = 0;
        	// vertical padding is set
        	if(i % 3 == 0 && i != 0)
        		countouter++;
        	for (int j = 0; j < length; j++){
        		// a new button is created at the given location
        		// testarray will be deleted soon
        		String caption = "";
        		if(originalField[i][j] != 0)
        			caption = Integer.toString(originalField[i][j]);
        		buttonField[i][j] = new FieldButton(this, caption, Config.optFieldBtSize, 2, Config.FontSize, false);
        		// when there's a ressource from the algo here will be a 
        		// decision whether the button is a normal button or a noChangeButton
        		if(originalField[i][j] != 0)
        			buttonField[i][j].setAsNoChange();
        		// horizontal padding is set
        		if(j%3 == 0 && j != 0)
        			countinner++;
        		// adds the buttons to the main game panel layout 
        		// and dynamically moves them to their right position
        		fieldLayout.addView(buttonField[i][j], 
        				new AbsoluteLayout.LayoutParams(
        						Config.optFieldBtSize,
        						Config.optFieldBtSize, 
        						Config.fieldStartXPos + countinner * Config.optFieldPadding + j * Config.optFieldBtSize,
        						Config.fieldStartYPos + countouter * Config.optFieldPadding + i * Config.optFieldBtSize));
        	}
        }
    }
    
    
    /**
     * this method searches for a clicked field an returns ist back
     * @return the clicked field
     */
    private FieldButton getClickedField()
    {
    	//the 2- dimensional array of fieldButtons is searched for an active one
    	for(int x = 0; x < buttonField.length; x++)
    		for(int y = 0; y < buttonField[x].length; y++)
    			if(buttonField[x][y].isClicked())
    				return buttonField[x][y];
    	
    				//null is returned, if there is no clicked field
    				return null;
    }
    
    /**
     * this method is called when a subactivity is closed
     */
    protected void onActivityResult(int requestCode,int resultCode, String data, Bundle canB)
    {
    	FieldButton clickedField = getClickedField();
    	
    	clickedField.setCandidateValue(0, canB.getInt("C1"));
    	clickedField.setCandidateValue(1, canB.getInt("C2"));
    	clickedField.setCandidateValue(2, canB.getInt("C3"));
    	clickedField.setCandidateValue(3, canB.getInt("C4"));
    	clickedField.setCandidateValue(4, canB.getInt("C5"));
    	clickedField.setCandidateValue(5, canB.getInt("C6"));
    
    	if(clickedField.checkCValues()==true)
    		clickedField.setAsCandidate();
    	
    	else
    		clickedField.setAsNoCandidate();
    	
    	//the value is being extracted from the string.
		int value = Integer.parseInt(data);
		
		if(requestCode == KEYPAD_REQUEST_CODE)
		{
			if(resultCode == RESULT_OK)
			{
					clickedField.setValue(value);
					
					if(value != 10 && value != 0)
						clickedField.deleteCandidates();
					
					clickedField.setNotClicked();
				
			} //end if inner
		}//end if outer
    	    		
	}//end method

    private void saveGame(){
    	int[][][] candidates = getCandidates();

    	String solvedFieldString = "";
    	String userManipulatedField = "";
    	String candidateString = "";
    	for (int i = 0; i < length; i++){
    		for (int j = 0; j < length; j++){
    			userManipulatedField += buttonField[i][j].getValue();
    			solvedFieldString += Integer.toString(solvedField[i][j]);
    			for(int k = 0; k < nrCandidates; k++)
    				if (candidates[i][j][k] != 0)
    					candidateString += candidates[i][j][k];
    				else
    					candidateString += "0";
    		}
    	}
    	
    	String originalField = "";
    	for (int i = 0; i < length; i++){
    		for (int j = 0; j < length; j++){
    			if (buttonField[i][j].getCaption() == "" ||  
    				Integer.toString(buttonField[i][j].getValue()).equals(buttonField[i][j].getCaption()))
    				originalField += "0";
    			else
    				originalField += buttonField[i][j].getCaption();
    		}
    	}

    	try{
        	Date d = new Date();
        	// Date looks like: 12.28.08
        	String date = DateFormat.format("MM.dd.yy",d.getTime()).toString();
        	String fileName = Config.playerName + "   " + date;
        	
        	if(gameWasLoaded){
        		fileName = fileNameloaded;
        		Log.d("File", "no need to rename, it was loaded");
        	}
        	else{
        	int i = 1;
        	boolean fileExists = false;
        	File g = new File("/data/data/de.fhd.medien.mait.sfa/files");
        	String[] directory = g.list();
        	for(String s : directory){
        		if(s.equals(fileName)){
        			Log.d("First check on File", "exists already: " + fileName);
        			fileExists = true;
        		}
        		else{
        			Log.d("First check on File", "Doesn't exist: " + fileName);
        		}
        	}
        	String fileEnding = "";
        	while(fileExists){
        		i++;
        		fileEnding = "(" + Integer.toString(i) + ")";
        		Log.d("File", "Changed fileName to: ..." + fileEnding);
        		boolean found = false;
        		for(String s : directory){
            		if(s.endsWith(fileEnding)){
            			Log.d("File", "exists already: ..." + fileEnding);
            			fileExists = true;
            			found = true;
            		}
            		else{
            			if(found == false){
            				Log.d("File", "Doesn't exist: " + fileEnding);
            				fileExists = false;
            			}
            		}
            	}
        		
        	}
        	fileName += fileEnding;
        	}
    		OutputStreamWriter fw = new OutputStreamWriter(
					openFileOutput(fileName, MODE_WORLD_READABLE));
    		// write solved field
    		fw.write(solvedFieldString);
    		// separate lines
    		fw.append((char)Character.LINE_SEPARATOR);
    		// write original field
    		fw.append(originalField);
    		// separate lines
    		fw.append((char)Character.LINE_SEPARATOR);
    		// write user maipulated field
    		fw.append(userManipulatedField);
    		// separate lines
    		fw.append((char)Character.LINE_SEPARATOR);
    		// write candidates
    		fw.append(candidateString);
    		// separate lines
    		fw.append((char)Character.LINE_SEPARATOR);
    		// write player name
    		fw.append(Config.playerName);
    		// separate lines
    		fw.append((char)Character.LINE_SEPARATOR);
    		// write level
    		fw.append(Integer.toString(Config.difficulty));
    		// separate lines
    		fw.append((char)Character.LINE_SEPARATOR);
    		// write time needed until now
    		long now = new Date().getTime();
    		long needed = now - Config.startTime;
    		fw.append(Long.toString(needed));
    		fw.flush();
    		fw.close();
    	
    	Log.d("Saving...", "Passed");
    	Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show();
    	} catch(Exception ex){
    		Log.d("Saving...", "Failed" + ex.fillInStackTrace());
    		Toast.makeText(this, "Game not saved, try again", Toast.LENGTH_SHORT).show();
    	}
    }
    
    private int[][][] getCandidates(){
    	int[][][] candidates = new int[length][length][nrCandidates];
    	for(int i = 0; i < length; i++){
    		for(int j = 0; j < length; j++){
    			int[] temp = buttonField[i][j].getCandidates();
    			for(int k = 0; k < nrCandidates; k++){
    				candidates[i][j][k] = temp[k];
    			}
    		}
    	}
    	return candidates;
    }
    
    /**
     * This method creates the Otions-Menu
     */
    public boolean onCreateOptionsMenu(final Menu menu) 
      {
        super.onCreateOptionsMenu(menu);
        
        menu.add(0, 1, "Save Game");
        menu.add(0, 2, "Spiel fertig");
        
        return true;
      }

    /**
     * This method handles clicks on items of the options-menu
     */
    public boolean onOptionsItemSelected(Menu.Item item)
      {
        // go to input form
        if(item.getId() == 1)
          {
        	saveGame();
          }
        if(item.getId() == 2)
          {
            Intent next = new Intent(this, Highscore.class);
            next.putExtra("time", 10);
            startSubActivity(next, 4646);
          }
        return true;
      }
   
//    protected void onDestroy(){
//    	saveGame();
//    	super.onDestroy();
//    }
    
 /**
  * this method gives information, if there is at least one field, on which the 
  * focus lays at the moment
  * @return is there any focused field
  */
 private boolean isAnyFieldFocused()
 {
	 for(int x = 0; x < buttonField.length; x++)
		 for(int y = 0; y < buttonField[x].length; y++)
			 if(buttonField[x][y].hasFocus())
				 return true;
	 
	 return false;
 }
 
 /**
  * this method gives back the field, which has focus explicitly
  * @return the field with focus
  */
 private FieldButton focusedField()
 {
	 for(int x = 0; x < buttonField.length; x++)
		 for(int y = 0; y < buttonField[x].length; y++)
			 if(buttonField[x][y].hasFocus())
				 return buttonField[x][y];
	 
	 return null;
 }
    
}//end class


