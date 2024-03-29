

package de.fhd.medien.mait.sfa;

import android.content.Context;
import android.graphics.Color;

/**
 * This class represents a candidate- value of a field. This Button
 * is used within the Keypad. Therefore a Button is needed to change the 
 * candidate-values. This specific Button- type can store a value (similar
 * to the FieldButton) and has also information weather it is active or not.
 */
public class valueButton  extends GameButton{
	
	//color for focused button
	static final int activeButtonBckgrnd = Color.GREEN;
	
	// colors for activated Button
	static final int fillDefaultActivated = 0xafF87698;
	static final int textDefaultActivated = 0xff000000;
	
	static final int fillFocusedActivated = 0xa0f70B49;
	static final int textFocusedActivated = 0xffffffff;
	
	static final int fillPressedActivated = 0x60f70B49;
	static final int textPressedActivated = 0xffffffff;
	
	private int value;
	private boolean isActive = false;
	private String label;
	
	/**
	 * 
	 * @param context immer "this"
	 * @param _value value of the button
	 * @param _caption the initial text of the Button
	 * @param _width the width of the button
	 * @param _height the height of the button
	 * @param _lineSize the linesize
	 * @param _textSize the textsize
	 * @param _bold whether the text should be displayed bold or not
	 */
	public valueButton(Context context, int _value, String _caption, int _width, int _height, int _lineSize, int _textSize, boolean _bold)
	{
		super(context, _caption, _width, _height, _lineSize, _textSize, _bold);
		this.label = _caption;
		this.value = _value;
	}
	
	public void activate()
	{
		this.isActive = true;
		this.setBackgroundColor(activeButtonBckgrnd);
		
	}
	
	public void deactivate()
	{
		this.isActive = false;
		this.setBackgroundColor(Color.TRANSPARENT);
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}
	
	/**
	 * this method changes the value of the button and the shown text.
	 * @param _newValue the new Value of the button
	 */
	public void setValue(int _newValue, boolean _bold)
	{
		
		this.value = _newValue;
		
		if(this.value == 0)
			this.setCaption(this.label, false);
		
		else
			this.setCaption(Integer.toString(_newValue), _bold);
			
		
	}
	
	/**
	 * Sets the Button active
	 */
	public void setAsActivated(){
		redraw(fillDefaultActivated, textDefaultActivated, fillFocusedActivated, textFocusedActivated, fillPressedActivated, textPressedActivated, false);
	}
	
	public void setAsDefault()
	{
		redraw(false);
	}
	
	public int value()
	{
		return this.value;
	}
	
	
}
