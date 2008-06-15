package de.fhd.medien.mait.sfa;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        
    	
         
        
        // initiate the Layout
        fieldLayout.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
        
        
        
        	
        // request a puzzle basing on the set difficulty
        
        // create the field
        createField();
        
        for(int x = 0; x < buttonField.length;  x++)
        	for(int y = 0; y < buttonField[x].length; y++)
        		if(buttonField[x][y].changable == true)
        			buttonField[x][y].setOnClickListener(fieldClick);
        
        // make the field visible
        setContentView(fieldLayout);
    }
    
   
    
    OnClickListener fieldClick = new OnClickListener()
    {

		//@Override
		public void onClick(View v) {
			FieldButton clickedField = (FieldButton) v;
			clickedField.setClicked();
			
			Intent startKeyPad = new Intent(Field.this, KeyPad.class);
			startSubActivity(startKeyPad, KEYPAD_REQUEST_CODE);
			
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
    protected void onActivityResult(int requestCode,int resultCode, String data, Bundle extras)
    {
		//the value is being extracted from the string.
		int value = Integer.parseInt(data.substring(1));
		
		if(requestCode == KEYPAD_REQUEST_CODE)
		{
			if(resultCode == RESULT_OK)
			{
				if(data.startsWith("0"))
				{
					FieldButton clickedField = getClickedField();
					clickedField.setValue(value);
					clickedField.setNotClicked();
				}
			} //end if inner
		}//end if outer
    	    		
	}//end method
}//end class


