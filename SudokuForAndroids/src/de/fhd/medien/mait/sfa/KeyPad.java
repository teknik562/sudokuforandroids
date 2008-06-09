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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsoluteLayout;
import android.widget.Toast;
import android.widget.AbsoluteLayout.LayoutParams;


public class KeyPad extends Activity {

	
	valueButton[] value = new valueButton[9];
	valueButton[] candidate = new valueButton[6];
	valueButton cmdBack, cmdClear;
	AbsoluteLayout absLayout = new AbsoluteLayout(this);
	//the code- arrays 
	String codeV[] = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "010" };
	String codeC[] = {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "110" };
	 
	private static final String TAG = "KeyPad ";
	
	int digitButtonWidth;
	int digitButtonHeight;
	int candidateButtonHeight;
	int candidateButtonWidth;
	int cButtonTSize;
	int digitButtonTextSize;
	int cmdBtnWidth;
	int cmdBtnHeight;
	int cmdBtnTsize;
	int offsetVert;
	
	
	
	//this is some kind of constructor within android. This Method is called when the activity is launched
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		initializeProperties();
		setContentView(absLayout); //the layout- xml file is chosen
		
		absLayout.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
		
		//the Buttons of the keypads are being assigned and initialized
		initializeButtons();
		
		
	} //end onCreate
	
	/**
	 * this method assigns the sizes of the buttons and text relative to the size of the Display. The Mehtod
	 * distinguishes between a Landscape and a Portrait- Display
	 */
	private void initializeProperties()
	{
		//is the Display Portrait?
		if(Config.displayHeight > Config.displayWidth)
		{
			digitButtonWidth = Config.displayWidth/6;
			digitButtonHeight = Config.displayHeight/8;
			candidateButtonHeight = Config.displayHeight/13;
			candidateButtonWidth = (digitButtonWidth*3)/6;
			cButtonTSize = candidateButtonWidth/2;
			digitButtonTextSize = digitButtonHeight/2;
			cmdBtnWidth = (digitButtonWidth * 3 )/2;
			cmdBtnHeight = Config.displayHeight/11;
			cmdBtnTsize = cmdBtnHeight /2;
			offsetVert = Config.displayHeight/120;
		}
		
		else
			
		{
			digitButtonWidth = Config.displayWidth/6;
			digitButtonHeight = Config.displayHeight/6;
			candidateButtonHeight = Config.displayHeight/9;
			candidateButtonWidth = (digitButtonWidth*3)/6;
			cButtonTSize = candidateButtonWidth/2;
			digitButtonTextSize = digitButtonHeight/2;
			cmdBtnWidth = (digitButtonWidth * 3 )/2;
			cmdBtnHeight = Config.displayHeight/9;
			cmdBtnTsize = cmdBtnHeight /2;
			offsetVert = Config.displayHeight/70;
		}
	}
	
	private void initializeValueButtons()
	{
		//the digit- buttons are being created
		for(int i = 0; i < value.length; i++)
		{
			value[i] = new valueButton(this, i+1, Integer.toString(i+1), this.digitButtonWidth, this.digitButtonHeight, 3, this.digitButtonTextSize, false );
		}
		
		absLayout.addView(value[0],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*0 , this.candidateButtonHeight + offsetVert));
		absLayout.addView(value[1],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*1 , this.candidateButtonHeight+ offsetVert));
		absLayout.addView(value[2],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*2 , this.candidateButtonHeight+ offsetVert));
		absLayout.addView(value[3],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*0 , this.candidateButtonHeight + this.digitButtonHeight+ offsetVert ));
		absLayout.addView(value[4],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*1 , this.candidateButtonHeight + this.digitButtonHeight+ offsetVert));
		absLayout.addView(value[5],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*2 , this.candidateButtonHeight + this.digitButtonHeight+ offsetVert));
		absLayout.addView(value[6],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*0 , this.candidateButtonHeight + 2*this.digitButtonHeight+ offsetVert));
		absLayout.addView(value[7],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*1 , this.candidateButtonHeight + 2*this.digitButtonHeight+ offsetVert));
		absLayout.addView(value[8],new AbsoluteLayout.LayoutParams(this.digitButtonWidth, this.digitButtonHeight, this.digitButtonWidth*2 , this.candidateButtonHeight + 2*this.digitButtonHeight+ offsetVert));
		
		//sets the onclicklistener and the values for the digit- buttons
		for(int i = 0; i < value.length; i++)
		{
			value[i].setOnClickListener(cmdListener);
			
		}
	}
	
	
	private void initializeButtons()
	{

		initializeValueButtons();
		
		
		for(int i= 0; i <candidate.length; i++)
		{
			candidate[i] = new valueButton(this, 0, "C" + Integer.toString(i+1), this.candidateButtonWidth, this.candidateButtonHeight, 2, this.cButtonTSize, false);
			absLayout.addView(candidate[i], new AbsoluteLayout.LayoutParams(this.candidateButtonWidth, this.candidateButtonHeight, i* this.candidateButtonWidth , 0));
			candidate[i].setOnClickListener(candidateListener);
		}
		
		cmdBack = new valueButton(this, 0, "back", this.cmdBtnWidth, this.cmdBtnHeight, 2, this.cButtonTSize, false );
		absLayout.addView(cmdBack, new AbsoluteLayout.LayoutParams(this.cmdBtnWidth, this.cmdBtnHeight, 0, this.candidateButtonHeight + 3* this.digitButtonHeight+ 2*offsetVert));
		cmdBack.setOnClickListener(backListener);
		
		cmdClear = new valueButton(this,0, "clear field",this.cmdBtnWidth, this.cmdBtnHeight, 2, this.cButtonTSize, false );
		absLayout.addView(cmdClear, new AbsoluteLayout.LayoutParams(this.cmdBtnWidth, this.cmdBtnHeight, this.cmdBtnWidth, this.candidateButtonHeight + 3* this.digitButtonHeight+ 2*offsetVert));
		cmdClear.setOnClickListener(clearListener);
			
	} //end initialize Buttons
	
	
	/**
	 * this onclicklistener mangages clicks on the back- button
	 */
	OnClickListener backListener = new OnClickListener()
	{
		//@Override
		public void onClick(View v) {
			//if there are any active candidates, deactivate all candidates and deactivate all digit- buttons
			if(anyActiveCandidate())
			{
				deactivateCandidates();
				deactivateValueButtons();
				cmdClear.deactivate();
			}
			
			else
			{
				// the value " 10 " is the code for "abort"
				KeyPad.this.setResult(RESULT_OK, Integer.toString(10));
				KeyPad.this.finish();
			}
				
			
		}//end on Click()
		
	};//end onClickListener
	
	
	
	/**
	 * this is the onClickListener for the clear- button
	 */
	OnClickListener clearListener = new OnClickListener()
	{

		//@Override
		public void onClick(View v) {
			//decide, if a candidate is active
			if(anyActiveCandidate())
			{
				valueButton actCandidate = findActiveCandidate();
				actCandidate.setValue(0, true);
				deactivateCandidates();
				deactivateValueButtons();
				cmdClear.deactivate();
			}
			
			else
			{
				//the value "0" is the code for "clear field"
				KeyPad.this.setResult(RESULT_OK, Integer.toString(0));
				KeyPad.this.finish();
			}
				
			
		}
		
		
	};
	
	
	
	/**
	 * this is the OnClickListener for the candidate- Buttons in the top of the Keypad
	 * 
	 */
	OnClickListener candidateListener = new OnClickListener()
	{
		//@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//initializeValueButtons();
			valueButton clickedCandidate = (valueButton)v;
			
			//check, if the clicked candidate is not active yet.
			if(clickedCandidate.isActive() == false)
			{
				clickedCandidate.activate();  //in this case: activate it!
				deactivateOtherCandidates(clickedCandidate); //deactivate other active candidates
				//ink all value buttons, to show, that the value of the candidate is changed
				activateValueButtons();
				cmdClear.activate();
				
			}
				
			//if the clicked candidate is already active
			else if(clickedCandidate.isActive() == true)
			{
				//deactivate it
				clickedCandidate.deactivate();
				//deactivate the digit buttons
				deactivateValueButtons();
				cmdClear.deactivate();
			} //end else if
			
		} //end onClick()
		
	}; //end OnClickListener
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		super.onKeyDown(keyCode, event);
		switch( keyCode)
		{
			case KeyEvent.KEYCODE_1:
				if(anyActiveCandidate()  && !doesCandidateExist(1))
				{
					findActiveCandidate().setValue(1, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate()) 
				{
					KeyPad.this.setResult(RESULT_OK, codeV[1]);
					KeyPad.this.finish();
				}
				break;
				
			case KeyEvent.KEYCODE_2:
				if(anyActiveCandidate() && !doesCandidateExist(2))
				{
					findActiveCandidate().setValue(2, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[2]);
					KeyPad.this.finish();
				}
				break;
				
			case KeyEvent.KEYCODE_3:
				if(anyActiveCandidate() && !doesCandidateExist(3))
				{
					findActiveCandidate().setValue(3, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[3]);
					KeyPad.this.finish();
				}
				break;
				
			case KeyEvent.KEYCODE_4:
				if(anyActiveCandidate() && !doesCandidateExist(4))
				{
					findActiveCandidate().setValue(4, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[4]);
					KeyPad.this.finish();
				}
				break;
				
			case KeyEvent.KEYCODE_5:
				if(anyActiveCandidate() && !doesCandidateExist(5))
				{
					findActiveCandidate().setValue(5, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[5]);
					KeyPad.this.finish();
				}
				
				break;
				
			case KeyEvent.KEYCODE_6:
				if(anyActiveCandidate() && !doesCandidateExist(6))
				{
					findActiveCandidate().setValue(6, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[6]);
					KeyPad.this.finish();
				}
				
				break;
				
			case KeyEvent.KEYCODE_7:
				if(anyActiveCandidate() && !doesCandidateExist(7))
				{
					findActiveCandidate().setValue(7, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[7]);
					KeyPad.this.finish();
				}
				
				break;
				
			case KeyEvent.KEYCODE_8:
				if(anyActiveCandidate()  && !doesCandidateExist(8) )
				{
					findActiveCandidate().setValue(8, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[8]);
					KeyPad.this.finish();
				}
				
				break;
				
			case KeyEvent.KEYCODE_9:
				if(anyActiveCandidate() && !doesCandidateExist(9))
				{
					findActiveCandidate().setValue(9, true);
					deactivateCandidates();
					deactivateValueButtons();
					cmdClear.deactivate();
				}
				
				else if (!anyActiveCandidate())
				{
					KeyPad.this.setResult(RESULT_OK, codeV[9]);
					KeyPad.this.finish();
				}
				
				break;
				
		}
		
		return true;
		
	}
	
	
	
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
		
		//@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			valueButton clickedButton = (valueButton)v;
			
			//if there is no active candidate
			if(anyActiveCandidate() == false) 
			{
				KeyPad.this.setResult(RESULT_OK, Integer.toString(clickedButton.value()));
				KeyPad.this.finish();
			}
			
			else if(anyActiveCandidate() == true)
			{
				//find the active Candidate, in which the value should be written
				valueButton activeCandidate = findActiveCandidate();
				
				//check, if the candidate with this value is already stored
				if(doesCandidateExist(clickedButton.value()) == false)
				{
					activeCandidate.setValue(clickedButton.value(), true);
					activeCandidate.deactivate();
					//set all value- buttons to the default look
					deactivateValueButtons();
					cmdClear.deactivate();
				}//end if
					
			}//end else if
						
		}//end onclick
		
	};//end cmdListener
	
	private valueButton findActiveCandidate()
	{
		valueButton returnButton = candidate[0];
		
			for(int i = 0; i < candidate.length; i++)
			{
				if(candidate[i].isActive())
					returnButton = candidate[i];
			}
				
		return returnButton;
	}
	
	OnKeyListener digitKeyListener = new OnKeyListener()
	{

		//@Override
		public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
			
			return false;
		}
		
	};
	
	
	
	
	private boolean anyActiveCandidate()
	{
		boolean returnValue = false;
		
		for(int i = 0; i < candidate.length; i++)
		{
			if(candidate[i].isActive())
				returnValue = true;
		}
					
		return returnValue;
	}
	
	/**
	 * this Method checks, if other candidates are active than the given one
	 * @param _activeButton the Button, which shall remain active
	 */
	private void deactivateOtherCandidates(valueButton _activeButton)
	{
		for(valueButton v: candidate)
		{
			if(v != _activeButton && v.isActive())
				v.deactivate();
		}
	}
	
	/**
	 * this method checks, if the given value is already written in one of the other
	 * candidates
	 * @param _value value to be written
	 * @return true for exists / false for exists not yet
	 */
	private boolean doesCandidateExist(int _value)
	{
		for(valueButton v: candidate)
			if(v.value() == _value)
			{
				Toast.makeText(this, "candidate " + Integer.toString(_value)+ " already exists!",
																				Toast.LENGTH_SHORT).show();
				return true;
			}
		
		return false;
	}
	
	
	private void activateValueButtons()
	{
		for(valueButton b : value)
			b.activate();
	}
	
	private void deactivateValueButtons()
	{
		for(valueButton b : value)
			b.deactivate();
	}
	
	private void deactivateCandidates()
	{
		for(valueButton b: candidate)
			b.deactivate();
	}
}//end class
