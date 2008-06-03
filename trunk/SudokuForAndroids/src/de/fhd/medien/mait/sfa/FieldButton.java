package de.fhd.medien.mait.sfa;

import android.content.Context;

public class FieldButton extends GameButton{
	
	private int[] candidateValues = new int[6];
	
	/**
	 * 
	 * @param context
	 * @param _value Has to be ONE character only
	 * @param _size size of the square
	 * @param _lineSize
	 * @param _textSize
	 */
	public FieldButton(Context context, String _value, int _size, int _lineSize, int _textSize) {
		super(context, _value, _size, _size, _lineSize, _textSize);
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
	
	
	
}
