/**
 * This class represents a candidate- value of a field. This Button
 * is used within the Keypad. Therefore a Button is needed to change the 
 * candidate-values. This specific Button- type can store a value (similar
 * to the FieldButton) and has also information weather it is active or not.
 */

package de.fhd.medien.mait.sfa;

import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

public class valueButton  extends Button{
	
	private int value;
	private boolean isActive;

	public valueButton(Context _context)
	{
		super(_context);
		this.value = 0;
		this.isActive = false; //this variable is used by the candidate- Buttons
	}
	
	public valueButton(Context _context, AttributeSet _attrs, Map _inflateParams)
	{
		super(_context, _attrs, _inflateParams); //the super-constructor is called
		this.value = 0;   //set the default- value to "0"
	
	} //end constructor
	
	public void activate()
	{
		this.isActive = true;
		this.setBackgroundColor(Color.GREEN);
	}
	
	public void deactivate()
	{
		this.isActive = false;
		this.setBackground(android.R.drawable.btn_default_small);
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}
	
	/**
	 * this method changes the value of the button and the shown text.
	 * @param _newValue the new Value of the button
	 */
	public void setValue(int _newValue)
	{
		this.value = _newValue;
		this.setText(Integer.toString(this.value));
	}
	
	public int value()
	{
		return this.value;
	}
	
	
}
