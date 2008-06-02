/**
 * This class represents a candidate- value of a field. This Button
 * is used within the Keypad. Therefore a Button is needed to change the 
 * candidate-values. This specific Button- type can store a value (similar
 * to the FieldButton) and has also information weather it is active or not.
 */

package de.fhd.medien.mait.sfa;

import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class candidateButton  extends Button{
	
	private int value;

	public candidateButton(Context _context, AttributeSet _attrs, Map _inflateParams)
	{
		super(_context, _attrs, _inflateParams); //the super-constructor is called
		this.value = 0;   //set the default- value to "0"
	
	} //end constructor
	
	
}
