package de.fhd.medien.mait.sfa;

import android.content.Context;

public class FieldButton extends GameButton{
	
	static final long serialVersionUID = 1;
	
	static final int maxNrCandidates = 6;
	
	//this boolean- variable marks the fieldButton as the Button,
	//which was clicked, after the Keypad- activity returns to the field
	private boolean clicked = false; 
	
	public boolean changeable = true;
	
	
	
	private int value = 0;
	private int[] candidateValues = new int[maxNrCandidates];
	
	// colors that are only used by Candidate Buttons
	static final int fillDefaultCandidate = 0xa097C024; 
	static final int textDefaultCandidate = 0xffffffff; 
	
	static final int fillFocusedCandidate = 0xa0bdde5f; 
	static final int textFocusedCandidate = 0xffffffff;
	
	static final int fillPressedCandidate = 0xa0f70B49; 
	static final int textPressedCandidate = 0xffffffff;
	
	// colors that are only used by Not Changeable Buttons
	static final int fillDefaultNoChange = 0xa03463d7; 
	static final int textDefaultNoChange = 0xffffffff; 
	
	static final int fillFocusedNoChange = 0xa00534a9; 
	static final int textFocusedNoChange = 0xffffffff;
	
	static final int fillPressedNoChange = 0xa00534a9; 
	static final int textPressedNoChange = 0xffffffff;
	
	/**
	 * Creates a more complex Button which is used for the Game
	 * @param context in best case 'this'
	 * @param _value Has to be ONE CHARACTER and NUMERIC only
	 * @param _size size of the button (square)
	 * @param _lineSize Width of the brush. '2' is best.
	 * @param _textSize Size of the text. 10 ~ 25 is best.
	 * @param _bold whether the text should be displayed bold or not
	 */
	public FieldButton(Context context, String _value, int _size, int _lineSize, int _textSize, boolean _bold) {
			super(context, _value, _size, _size, _lineSize, _textSize, _bold);
			
			for(int i = 0; i < candidateValues.length; i++)
				candidateValues[i] = 0;
				
			
	}
	
	public void setCandidates(int[] newCandidates){
		this.candidateValues = newCandidates;
	}
	
	public void deleteCandidates()
	{
		for(int i = 0; i < candidateValues.length; i++)
			candidateValues[i] = 0;
	}
	
	
	public void setAsNoCandidate()
	{
		redraw(false);
	}
	
	/**
	 * Makes the Button look like a Button having at least one candidate
	 */
	public void setAsCandidate(){
		deleteCaption();
		redraw(fillDefaultCandidate, textDefaultCandidate, fillFocusedCandidate, textFocusedCandidate, fillPressedCandidate, textPressedCandidate, false);
	}
	
	/**
	 * this method sets the "clicked"- variable to true
	 */
	public void setClicked()
	{
		this.clicked = true;
	}
	
	/**
	 * this method "deactivates" the button/ sets the "clicked"- variable to false again
	 */
	public void setNotClicked()
	{
		this.clicked = false;
	}
	
	/**
	 * 
	 * @return was the field clicked before?
	 */
	public boolean isClicked()
	{
		return this.clicked;
	}
	
	/**
	 * 
	 */
	public void setAsNoChange()
	{
		redraw(fillDefaultNoChange, textDefaultNoChange, fillFocusedNoChange, textFocusedNoChange, fillPressedNoChange, textPressedNoChange, false);
		this.changeable = false;
	}
	
	/**
	 * 
	 * @param index index of the requested Candidate
	 * @return The candidate at the specified index
	 */
	public int getCandidate(int index){
		return candidateValues[index];
	}
	
	/**
	 * 
	 * @return An array with the cadidates
	 */
	public int[] getCandidates(){
		return candidateValues;
	}
	
	/**
	 * Returns the value of this button
	 * @return value of the Button
	 */
	public int getValue(){
		return this.value;
	}
	/**
     * this method checks the field's candidate- values, if somethin is written in them
     * @return is at least  one candidate != 0
     */
    public boolean checkCValues()
    {
    	for(int i: candidateValues)
    	{
    		if( i != 0)
    			return true;
    	}
    	
    	return false;
    }
	
	/**
	 * this method sets the value of the field. But first the method distinguishes
	 * between coded values like 0 or 10 or "real" values
	 * @param _newValue the Value which will be written in the field
	 */
	public void setValue(int _newValue)
	{
		if(_newValue == 10); //in this case: do nothing!
		
		else if(_newValue == 0 && this.changeable == true)
		{
			this.value = 0;  //in the case of "10" the Field is cleared
			this.setCaption("", false);
		}
		
		//in any other cases the value can be stored directly
		else
		{
			if(this.changeable == true)
			{
				this.value = _newValue;
				this.setCaption(Integer.toString(value), false);
			}
			
		}
			
	} // end method
	
	public void setCandidateValue(int _index, int _value)
	{
		this.candidateValues[_index] = _value;
	}
	
	
}//end class
