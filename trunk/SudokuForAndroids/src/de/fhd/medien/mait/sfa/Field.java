package de.fhd.medien.mait.sfa;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * This is where the magic will happen ;)
 * Here the Field will be displayed, a Sudoku puzzle will be requested...
 */
public class Field extends Activity{
	
	//the code to identify the Keypad, when it returns to this activity
	private static final int KEYPAD_REQUEST_CODE = 0;
	
	// Layout for the field
	private AbsoluteLayout fieldLayout = new AbsoluteLayout(this);
	private static final int length = 9;
	private FieldButton[][] buttonField = new FieldButton[length][length];
	int[][] testarray = {
			{0, 0, 0, 4, 0, 0, 8, 3, 9},
			{5, 0, 3, 4, 0, 0, 8, 0, 9},
			{0, 0, 3, 4, 1, 7, 0, 3, 9},
			{0, 0, 3, 0, 1, 0, 8, 0, 9},
			{2, 0, 3, 4, 1, 7, 8, 3, 0},
			{0, 0, 3, 4, 0, 7, 0, 3, 9},
			{0, 0, 0, 0, 1, 7, 8, 3, 9},
			{0, 0, 3, 4, 1, 7, 8, 0, 9},
			{0, 0, 0, 4, 1, 7, 8, 3, 9}
	};
	ProgressDialog pd = null; 
	
	Thread t = new Thread()
    {
           public void run()
           {
        	   // request a puzzle basing on the set difficulty
        	   // create the field
               createField();
               for(int x = 0; x < buttonField.length;  x++)
               	for(int y = 0; y < buttonField[x].length; y++)
               		if(buttonField[x][y].changeable == true)
               			buttonField[x][y].setOnClickListener(fieldClick);
               
               //when done
        	   pd.dismiss(); 
           }

    }; 
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle); 
        
        // initiate the Layout
        fieldLayout.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
        pd = ProgressDialog.show(Field.this,     
                "Please wait...", "Generating Sudoku", true);
        Handler handler = new Handler(); 
        handler.post(t);
        Log.d("Chosen difficulty", Integer.toString(Config.difficulty));
        	
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
        		if(testarray[i][j] != 0)
        			caption = Integer.toString(testarray[i][j]);
        		buttonField[i][j] = new FieldButton(this, caption, Config.optFieldBtSize, 2, Config.FontSize, false);
        		// when there's a ressource from the algo here will be a 
        		// decision whether the button is a normal button or a noChangeButton
        		if(testarray[i][j] != 0)
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

    /**
     * This method creates the Otions-Menu
     */
    public boolean onCreateOptionsMenu(final Menu menu) 
      {
        super.onCreateOptionsMenu(menu);
        
        menu.add(0, 1, "Spiel speichern");
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
          }
        if(item.getId() == 2)
          {
            Intent next = new Intent(this, Highscore.class);
            next.putExtra("time", 22);
            startSubActivity(next, 4646);
          }
        return true;
      }
}

//end class


