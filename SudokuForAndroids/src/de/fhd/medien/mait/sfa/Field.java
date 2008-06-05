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
	private AbsoluteLayout fieldLayout = new AbsoluteLayout(this);
	private static final int length = 9;
	private FieldButton[][] buttonField = new FieldButton[length][length];
	
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
        		buttonField[i][j] = new FieldButton(this, "2", Config.optFieldBtSize, 2, Config.optFieldBtFontSize);
        		// when there's a ressource from the algo here will be a 
        		// decision whether the button is a normal button or a noChangeButton
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
