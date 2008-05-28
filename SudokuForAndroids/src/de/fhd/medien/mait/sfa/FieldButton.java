/**
 * This Class represents a field on the sudoku- Game-Panel. The field is hereby
 * realized by a button - which is the reason why the class extends the button- class.
 * In addition to the standard- functionality of a button, this specific kind of button is able
 * to store Values and candidate- values.
 * 			
 */


package de.fhd.medien.mait.sfa;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


//constructor must be implemented before removing the following comment- marks
public class FieldButton extends Button{ 

	
	/**
	 * the constructor of a specialized Button needs three different parameters
	 * which will be set by the runtime- environment automatically
	 * @param _context s
	 * @param _attrs
	 * @param _inflateParams
	 */
	 public FieldButton(Context _context, AttributeSet _attrs, Map _inflateParams)
	{
		super(_context, _attrs, _inflateParams);
		
	}
	
	
	private int value;			//the value, which will be processed by the algorithm
	private int[] candidateValues = new int[6];  //the field's value- candidates 
	
	
	
}
