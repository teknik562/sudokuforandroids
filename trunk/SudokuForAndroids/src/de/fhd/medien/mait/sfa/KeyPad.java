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


public class KeyPad extends Activity {

	//this is some kind of constructor within android. This Method is called when the activity is launched
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.keypad); //the layout- xml file is chosen
		
	//the Button- objects are being instanciated in the following lines
	Button cmd_1 = (Button)this.findViewById(R.id.cmd_1);
	Button cmd_2 = (Button)this.findViewById(R.id.cmd_2);
	Button cmd_3 = (Button)this.findViewById(R.id.cmd_3);
	Button cmd_4 = (Button)this.findViewById(R.id.cmd_4);
	Button cmd_5 = (Button)this.findViewById(R.id.cmd_5);
	Button cmd_6 = (Button)this.findViewById(R.id.cmd_6);
	Button cmd_7 = (Button)this.findViewById(R.id.cmd_7);
	Button cmd_8 = (Button)this.findViewById(R.id.cmd_8);
	Button cmd_9 = (Button)this.findViewById(R.id.cmd_9);
	
	Button candidate_1 = (Button)this.findViewById(R.id.candidate_1);
	Button candidate_2 = (Button)this.findViewById(R.id.candidate_2);
	Button candidate_3 = (Button)this.findViewById(R.id.candidate_3);
	Button candidate_4 = (Button)this.findViewById(R.id.candidate_4);
	Button candidate_5 = (Button)this.findViewById(R.id.candidate_5);
	Button candidate_6 = (Button)this.findViewById(R.id.candidate_6);
	
	Button cmdAbort = (Button)this.findViewById(R.id.cmdAbort);
	Button cmdClear = (Button)this.findViewById(R.id.cmdClear);
	
	
	
	
	
	} //end onCreate
	
}//end class
