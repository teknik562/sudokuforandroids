package de.fhd.medien.mait.sfa;

import android.content.Context;

public class FieldButton extends GameButton{
	
	static final int maxNrCandidates = 6;
	
	private int[] candidateValues = new int[maxNrCandidates];
	
	// colors that are only used by a FieldButton
	static final int fillNoChange = 0;	// background color of Buttons with non-changeable content
	static final int textNoChange = 0;  // text color of Buttons with non-changeable content
	
	static final int fillCandidate = 0; // background color of Buttons with candidate(s)
	static final int textCandidate = 0; // text color of Buttons with candidate(s)
	
	/**
	 * Creates a more complex Button which is used for the Game
	 * @param context in best case 'this'
	 * @param _value Has to be ONE CHARACTER and NUMERIC only
	 * @param _size size of the button (square)
	 * @param _lineSize Width of the brush. '2' is best.
	 * @param _textSize Size of the text. 10 ~ 25 is best.
	 */
	public FieldButton(Context context, String _value, int _size, int _lineSize, int _textSize) {
			super(context, _value, _size, _size, _lineSize, _textSize);
	}
	
	/**
	 * Makes the Button identify itself as a Button having at least one candidate
	 */
	public void setHasCandidate(){
		// Create new Bitmaps for this Button
		// 1) default
	    this.drawEmptyBitmap(defaultBitmap, fillCandidate);
	    this.drawTextInBitmap(defaultBitmap, textCandidate);
	    // 2) focused
	    this.drawEmptyBitmap(focusedBitmap, fillCandidate);
	    this.drawTextInBitmap(focusedBitmap, textCandidate);
	    // 3) pressed
	    this.drawEmptyBitmap(pressedBitmap, fillCandidate);
	    this.drawTextInBitmap(pressedBitmap, textCandidate);
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
	 * 
	 * @return Value of the Button
	 */
	public int getValue(){
		return Integer.parseInt(getCaption());
	}
	
	
	
}
