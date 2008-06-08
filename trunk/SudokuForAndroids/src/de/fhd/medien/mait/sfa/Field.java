package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * This is where the magic will happen ;)
 * Here the Field will be displayed, a Sudoku puzzle will be requested...
 */
public class Field extends Activity{
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
        
        // make the field visible
        setContentView(fieldLayout);
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
        		if(testarray[i][j] != 0)
        			caption = Integer.toString(testarray[i][j]);
        		buttonField[i][j] = new FieldButton(this, caption, Config.optFieldBtSize, 2, Config.FontSize);
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

}
