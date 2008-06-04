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

public class valueButton  extends GameButton{
	
	private int value;
	private boolean isActive;

	public valueButton(Context context, int _value, String _caption, int _width, int _height, int _lineSize, int _textSize)
	{
		super(context, _caption, _width, _height, _lineSize, _textSize);
		this.value = _value;
	}
	
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
		super.setText(Integer.toString(this.value));
		
	}
	
	public int value()
	{
		return this.value;
	}
	
	
}
