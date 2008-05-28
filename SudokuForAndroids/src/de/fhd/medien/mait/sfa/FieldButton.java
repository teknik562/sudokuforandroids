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


public class FieldButton extends Button{ 

	private int value;			//the value, which will be processed by the algorithm
	private int[] candidateValues = new int[6];  //the field's value- candidates 
	
	
	/**
	 * the constructor of a specialized Button needs three different parameters
	 * which will be set by the runtime- environment automatically
	 *
	 * the constructor itself provides the following functionalities:
	 * - setting the value to "0" (in this case nothing is shown in the field)
	 * 
	 * 
	 * @param _context s
	 * @param _attrs
	 * @param _inflateParams
	 */
	public FieldButton(Context _context, AttributeSet _attrs, Map _inflateParams)
	{
		super(_context, _attrs, _inflateParams); //the super-constructor is called
		this.value = 0;   //set the default- value to "0"
	
	} //end constructor
	
	
	/**
	 * This method sets the field-value to the passed int- value
	 * @param _newValue the new value of the field
	 */
	public void setValue(int _newValue)
	{
		this.value = _newValue;
	}
	
	/**
	 * this method returns the value stored in the field.
	 * @return the value of the field
	 */
	public int getValue()
	{
		return this.value;
	}
	
	
	
	
	
	
	
}//end class
