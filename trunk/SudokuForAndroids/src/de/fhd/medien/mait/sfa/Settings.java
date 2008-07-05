/**
 * This class represents the settings- screen of sudoku for android(s), in which the cheat-mode can
 * be activated 
 */

package de.fhd.medien.mait.sfa;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;



public class Settings extends Activity {

	
	int upperOffset;
	int leftOffset;
	
	CheckBox cheatCheckBox; //the check-box to activate/ deactivate the cheat- mode
	TextView cheatText; //the description of the cheat- mode
	GameButton cmdBack;  
	
	int btnWidth;
	int btnHeight;
	int assCheckBoxHeight = 40;
	int assTextHeight = Config.displayHeight/4;
	int assTextWidth =   Config.displayWidth/2;
	AbsoluteLayout absLayoutSettings;
	
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		btnWidth = Config.displayWidth/3;
		btnHeight = Config.displayHeight/10;
		
		
		upperOffset = Config.displayHeight/90;
		leftOffset = Config.displayWidth/15;
		
		 absLayoutSettings = new AbsoluteLayout(this);
	     absLayoutSettings.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
	      
	     
	     
	     //instantiate the widgets
	     cheatCheckBox = new CheckBox(this);
	     cheatText = new TextView(this);
	     cheatText.setText(R.string.cheatText);
	     
	     cheatText.setLayoutParams(new AbsoluteLayout.LayoutParams(assTextWidth, assTextHeight, leftOffset ,assCheckBoxHeight + upperOffset));
	     
	     if(Config.displayHeight <= 340)
	     {
	    	 
	    	 cheatText.setTextSize(10);
	     }
	     
	     if(Config.displayHeight <= 240)
	    	 cheatText.setTextSize(8);
	    	 
	     
	     
	     
	     cmdBack = new GameButton(this, "back", btnWidth, btnHeight, 3, btnHeight/2, false );
	     
	     cheatCheckBox.setText("Cheat- mode active");
	     

	     
	     
	     absLayoutSettings.addView(cheatCheckBox,new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, leftOffset , upperOffset));
	     absLayoutSettings.addView(cheatText, new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT , leftOffset, assCheckBoxHeight + upperOffset));
	     absLayoutSettings.addView(cmdBack, new AbsoluteLayout.LayoutParams(btnWidth, btnHeight, leftOffset ,assCheckBoxHeight + upperOffset + assTextHeight));
	     cmdBack.setOnClickListener(backClick);
	     
	     setContentView(absLayoutSettings);
	     
	     
	     cheatCheckBox.setChecked(Config.cheatModeActive);
	     
	}
	
	OnClickListener backClick = new OnClickListener()
	{

		//@Override
		public void onClick(View arg0) {
		
				Config.cheatModeActive = cheatCheckBox.isChecked();
		
				Settings.this.setResult(RESULT_OK, "true");
				Settings.this.finish();
				
		
	};
	
	
};
}//end class

