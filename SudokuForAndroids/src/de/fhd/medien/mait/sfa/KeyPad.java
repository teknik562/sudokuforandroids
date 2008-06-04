/**
 * this class is an input- device in order to write values into a sudoku- field.
 * this keypad is called by pressing a field- button. within the keypad
 * there are 9 buttons to choose a value from 1 to 9, six buttons for candidate-
 * values, an abort- and a "clear field" - Button.
 * The keypad's layout is defined within the keypad.xml- file and is displayed
 * in the dialog- theme.
 * By using this keypad, the user is able to change the field's value, an candidate's
 * value and to clear the field.
 */

package de.fhd.medien.mait.sfa;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;


public class KeyPad extends Activity {

	
	valueButton[] value = new valueButton[9];
	valueButton[] candidate = new valueButton[6];
	GameButton cmdBack, cmdClear;
	AbsoluteLayout absLayout = new AbsoluteLayout(this);
	int digitButtonSize = 40;
	int candidateButtonHeight = 40;
	int candidateButtonWidth = (digitButtonSize*3)/6;
	int cButtonTSize = candidateButtonWidth/2;
	int digitButtonTextSize = digitButtonSize/2;
	int cmdBtnWidth = (digitButtonSize * 3 )/2;
	int cmdBtnHeight = 30;
	int cmdBtnTsize = cmdBtnHeight /2;
	
	
	
	//this is some kind of constructor within android. This Method is called when the activity is launched
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(absLayout); //the layout- xml file is chosen
		
		absLayout.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
		
		//the Buttons of the keypads are being assigned and initialized
		initializeButtons();
		
	} //end onCreate
	
	private void initializeButtons()
	{

		//the digit- buttons are being created
		for(int i = 0; i < value.length; i++)
		{
			value[i] = new valueButton(this, i+1, Integer.toString(i+1), this.digitButtonSize, this.digitButtonSize, 3, this.digitButtonTextSize );
		}
		
		absLayout.addView(value[0],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*0 , this.candidateButtonHeight));
		absLayout.addView(value[1],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*1 , this.candidateButtonHeight));
		absLayout.addView(value[2],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*2 , this.candidateButtonHeight));
		absLayout.addView(value[3],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*0 , this.candidateButtonHeight + this.digitButtonSize ));
		absLayout.addView(value[4],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*1 , this.candidateButtonHeight + this.digitButtonSize));
		absLayout.addView(value[5],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*2 , this.candidateButtonHeight + this.digitButtonSize));
		absLayout.addView(value[6],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*0 , this.candidateButtonHeight + 2*this.digitButtonSize));
		absLayout.addView(value[7],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*1 , this.candidateButtonHeight + 2*this.digitButtonSize));
		absLayout.addView(value[8],new AbsoluteLayout.LayoutParams(this.digitButtonSize, this.digitButtonSize, this.digitButtonSize*2 , this.candidateButtonHeight + 2*this.digitButtonSize));
		
		
		for(int i= 0; i <candidate.length; i++)
		{
			candidate[i] = new valueButton(this, 0, "C" + Integer.toString(i+1), this.candidateButtonWidth, this.candidateButtonHeight, 2, this.cButtonTSize);
			absLayout.addView(candidate[i], new AbsoluteLayout.LayoutParams(this.candidateButtonWidth, this.candidateButtonHeight, i* this.candidateButtonWidth , 0));
			candidate[i].setOnClickListener(candidateListener);
		}
		
		cmdBack = new GameButton(this, "back", this.cmdBtnWidth, this.cmdBtnHeight, 2, this.cButtonTSize );
		absLayout.addView(cmdBack, new AbsoluteLayout.LayoutParams(this.cmdBtnWidth, this.cmdBtnHeight, 0, this.candidateButtonHeight + 3* this.digitButtonSize));
		
		cmdClear = new GameButton(this, "clear field",this.cmdBtnWidth, this.cmdBtnHeight, 2, this.cButtonTSize );
		absLayout.addView(cmdClear, new AbsoluteLayout.LayoutParams(this.cmdBtnWidth, this.cmdBtnHeight, this.cmdBtnWidth, this.candidateButtonHeight + 3* this.digitButtonSize));
		
		
		
		//sets the onclicklistener and the values for the digit- buttons
		for(int i = 0; i < value.length; i++)
		{
			value[i].setOnClickListener(cmdListener);
			
		}
		
	} //end initialize Buttons
	
	OnClickListener candidateListener = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			valueButton clickedCandidate = (valueButton)v;
			
			if(!clickedCandidate.isActive())
				clickedCandidate.activate();
			
			else
				clickedCandidate.deactivate();
			
		}
		
	};
	
	
	
	/**
	 * this onclicklistener returns the value of the pressed Button back to the
	 * calling activity.
	 * If a candidate- value is pressed before the value is written to this candidate 
	 * instead.
	 * 
	 * In the first case the keypad is closed automatically.
	 */
	OnClickListener cmdListener = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			valueButton clickedButton = (valueButton)v;
			
			//if there is no active candidate
			if(!anyActiveCandidate()) 
			{
				KeyPad.this.setResult(RESULT_OK, Integer.toString(clickedButton.value()));
				KeyPad.this.finish();
			}
			
			else
			{
				valueButton activeCandidate = findActiveCandidate();
				activeCandidate.setValue(clickedButton.value());
				activeCandidate.deactivate();
			}
						
		}
		
	};
	
	private valueButton findActiveCandidate()
	{
		
		for(valueButton v: candidate)
			if(v.isActive())
				return v;
		
		return null;
	}
	
	
	private boolean anyActiveCandidate()
	{
		boolean returnValue = false;
		
		for(valueButton v: candidate)
			if(v.isActive())
				returnValue = false;
			else
				returnValue = true;
		
		return returnValue;
	}
	
}//end class
