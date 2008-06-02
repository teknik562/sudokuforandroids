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
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;


public class KeyPad extends Activity {

	valueButton[] value = new valueButton[9];
	valueButton[] candidate = new valueButton[6];
	Button cmdBack, cmdClear;
	
	//this is some kind of constructor within android. This Method is called when the activity is launched
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.keypad); //the layout- xml file is chosen
		
		//the Buttons of the keypads are being assigned and initialized
		initializeButtons();
		
	} //end onCreate
	
	private void initializeButtons()
	{
		//the Button- objects are being instanciated in the following lines
		value[0] = (valueButton)this.findViewById(R.id.cmd_1);
		value[1] = (valueButton)this.findViewById(R.id.cmd_2);
		value[2] = (valueButton)this.findViewById(R.id.cmd_3);
		value[3] = (valueButton)this.findViewById(R.id.cmd_4);
		value[4] = (valueButton)this.findViewById(R.id.cmd_5);
		value[5] = (valueButton)this.findViewById(R.id.cmd_6);
		value[6] = (valueButton)this.findViewById(R.id.cmd_7);
		value[7] = (valueButton)this.findViewById(R.id.cmd_8);
		value[8] = (valueButton)this.findViewById(R.id.cmd_9);
		
		candidate[0] = (valueButton)this.findViewById(R.id.candidate_1);
		candidate[1] = (valueButton)this.findViewById(R.id.candidate_2);
		candidate[2] = (valueButton)this.findViewById(R.id.candidate_3);
		candidate[3] = (valueButton)this.findViewById(R.id.candidate_4);
		candidate[4] = (valueButton)this.findViewById(R.id.candidate_5);
		candidate[5] = (valueButton)this.findViewById(R.id.candidate_6);
		
		cmdBack = (Button)this.findViewById(R.id.cmdBack);
		cmdClear = (Button)this.findViewById(R.id.cmdClear);
		

		
		for(int i = 0; i < value.length; i++)
		{
			value[i].setOnClickListener(cmdListener);
			value[i].setValue(i+1);	
		}
		
		


		
		for(valueButton v: candidate)
		{
			v.setBackground(android.R.drawable.btn_default_small);
			v.setOnClickListener(candidateListener);
		}
			
		
		cmdBack.setBackground(android.R.drawable.btn_default_small);
		cmdClear.setBackground(android.R.drawable.btn_default_small);

	}
	
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
