package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class SudokuMain extends Activity {
	/** Background color */
	private static final int fillBgMenu = 0x1007C024;
	private static final String TAG = "SudokuMain: ";
	
	// Main layout
	AbsoluteLayout absLayoutMenu;
	
	//the Android- logo- Bitmap in the upper area of the main- menue
	Bitmap picAndroidOrg;
	Bitmap picAndroidScaled;
	BitmapDrawable picAndroidDraw;
	ImageView picAndroidView;
	
	//The scale- matrix which scales the android- logo
	Matrix picMatrix; 
	int picWidth;
	int picHeight;
	int upperOffset;
	
	int picNeededWidth, picNeededHeight;
	float picScaleFact;
	
	
	// Buttons
	GameButton newGameBt;
	GameButton contGameBt;
	GameButton settingsBt;
	GameButton highscoreBt;
	GameButton helpBt;
	GameButton creditsBt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        
        // get the info from the display and calculate layout
        calculateProperties();
        
        picAndroidOrg = BitmapFactory.decodeResource(getResources(),
				R.drawable.icondroid);
        
        
     
        //calculate the size and position for the logo depending on Config
        calculatePicProperties();
        
        // initiate layout
        absLayoutMenu = new AbsoluteLayout(this);
        absLayoutMenu.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
        absLayoutMenu.setBackgroundColor(fillBgMenu);

        // create & place Buttons on layout
        placePicture();
        createButtons();
        placeButtons();
     
        
        // set the onClickListeners
        helpBt.setOnClickListener(helpClick);
        creditsBt.setOnClickListener(creditsClick);
        newGameBt.setOnClickListener(dummyNewGameClick);
        settingsBt.setOnClickListener(settingsClick);
        setContentView(absLayoutMenu);
        
        
        
        /*
        Intent next = new Intent(this, CreateUser.class);
        next.putExtra("USERNAME", "Hans");

        
        this.startSubActivity(next, 0);
        
        
        
        setContentView(R.layout.main);
        */
    }
    
    /**
     * Creates Buttons for the main menu
     */
    private void createButtons(){
    	newGameBt = new GameButton(this, getString(R.string.newGame), Config.optMenuBtWidth,
        		Config.optMenuBtHeight, Config.lineSize, Config.FontSize, false);
        
        
        contGameBt = new GameButton(this, getString(R.string.contGame), Config.optMenuBtWidth,
        		Config.optMenuBtHeight, Config.lineSize, Config.FontSize, false);
        
        
        settingsBt = new GameButton(this, getString(R.string.settings), Config.optMenuBtWidth,
        		Config.optMenuBtHeight, Config.lineSize, Config.FontSize, false);
        
        
        highscoreBt = new GameButton(this, getString(R.string.highscore), Config.optMenuBtWidth,
        		Config.optMenuBtHeight, Config.lineSize, Config.FontSize, false);
        
        
        helpBt = new GameButton(this, getString(R.string.help), Config.optMenuBtWidth,
        		Config.optMenuBtHeight, Config.lineSize, Config.FontSize, false);
        
        
        creditsBt = new GameButton(this, getString(R.string.credits), Config.optMenuBtWidth,
        		Config.optMenuBtHeight, Config.lineSize, Config.FontSize, false);
        
    }
    
    /**
     * Places buttons from main manu on the main layout
     */
    private void placeButtons(){
    absLayoutMenu.addView(newGameBt, 
			new AbsoluteLayout.LayoutParams(Config.optMenuBtWidth,
					Config.optMenuBtHeight, Config.menutStartXPos,  picAndroidScaled.getHeight() + upperOffset));
    
    absLayoutMenu.addView(contGameBt, 
			new AbsoluteLayout.LayoutParams(Config.optMenuBtWidth,
					Config.optMenuBtHeight, Config.menutStartXPos,  1 * Config.optMenuBtHeight + picAndroidScaled.getHeight() + upperOffset));
    
    absLayoutMenu.addView(settingsBt, 
			new AbsoluteLayout.LayoutParams(Config.optMenuBtWidth,
					Config.optMenuBtHeight, Config.menutStartXPos,  2 * Config.optMenuBtHeight + picAndroidScaled.getHeight()+ upperOffset));
    
    absLayoutMenu.addView(highscoreBt, 
			new AbsoluteLayout.LayoutParams(Config.optMenuBtWidth,
					Config.optMenuBtHeight, Config.menutStartXPos,  3 * Config.optMenuBtHeight + picAndroidScaled.getHeight()+ upperOffset));
    
    absLayoutMenu.addView(helpBt, 
			new AbsoluteLayout.LayoutParams(Config.optMenuBtWidth,
					Config.optMenuBtHeight, Config.menutStartXPos,  4 * Config.optMenuBtHeight + picAndroidScaled.getHeight()+ upperOffset));
    
    absLayoutMenu.addView(creditsBt, 
			new AbsoluteLayout.LayoutParams(Config.optMenuBtWidth,
					Config.optMenuBtHeight, Config.menutStartXPos, 5 * Config.optMenuBtHeight + picAndroidScaled.getHeight()+ upperOffset));
    }
    
    
    private void placePicture()
    {
    	picAndroidView = new ImageView(this);
    	picAndroidView.setImageDrawable(picAndroidDraw);
    	picAndroidView.setScaleType(ScaleType.CENTER);
    	
    	absLayoutMenu.addView(picAndroidView,
    			new AbsoluteLayout.LayoutParams(picAndroidScaled.getWidth(),
    					picAndroidScaled.getHeight(), Config.displayWidth/2 - (int)(picAndroidOrg.getWidth()* picScaleFact)/2, upperOffset));
    }
    
    private void calculatePicProperties()
    {
    	
    	
    	picHeight = picAndroidOrg.getHeight();
    	picWidth = picAndroidOrg.getWidth();
    	
    	if(Config.displayHeight > 340)
    		picNeededHeight = (Config.displayHeight - 6*Config.optMenuBtHeight - Config.displayHeight/20 - Config.displayHeight/7) ;
    		
    	else
    	picNeededHeight = (Config.displayHeight - 6*Config.optMenuBtHeight - Config.displayHeight/20) ;
    	
    	
    	picScaleFact = (float) picNeededHeight / picHeight;
    	
    	
    	picMatrix = new Matrix();
    	picMatrix.postScale(picScaleFact, picScaleFact);
    	
    	
    	
    	//a new and scaled Bitmap is created
    	picAndroidScaled = Bitmap.createBitmap(picAndroidOrg,
    											0,  //x- position
    											0,			// y- position
    											picAndroidOrg.getWidth(), //the width of the pic
    											picAndroidOrg.getHeight(), // the height
    											picMatrix,  //the matrix which scales the pic
    											true);  //filter
    	Log.v(TAG, "picAndroidScaled erstellt");
    	
    	picAndroidDraw = new BitmapDrawable(picAndroidScaled); 
    	
    	//the offset between the upper screen border and the icon
    	upperOffset = (Config.displayHeight - (picAndroidScaled.getHeight() + 6*Config.optMenuBtHeight))/4;
    	
    	
    }
    
    
    OnClickListener dummyNewGameClick = new OnClickListener()
    {

		//@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
				Intent showKeyPad = new Intent(SudokuMain.this, KeyPad.class);
				startActivity(showKeyPad);
		}
    	
    };
    
   
    OnClickListener settingsClick = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Intent showSettings = new Intent(SudokuMain.this, Settings.class);
			startActivity(showSettings);
			
		}
    	
    };
    
    /** When clicked on Credits a new View is opened where the participants are shown */
    OnClickListener creditsClick = new OnClickListener(){
        //@Override
        	public void onClick(View v) {
        		Intent showCredits = new Intent(SudokuMain.this, Credits.class);
				startActivity(showCredits);
        	}	
    };
    
    /** When clicked on Help a new View is opened where the user is shown how to use this app */
    OnClickListener helpClick = new OnClickListener(){
        //@Override
        	public void onClick(View v) {
        		Intent showHelp = new Intent(SudokuMain.this, Help.class);
				startActivity(showHelp);
        	}	
    };
    
    
    /** 
     *  Calculates layout properties and stores them in the config.
     */ 
    private void calculateProperties(){
    	WindowManager a = this.getWindowManager();
        Display v = a.getDefaultDisplay();
        
        // calculates the minimumscreenAttr
        int min = Math.min(v.getHeight(),v.getWidth());
        if (min == v.getHeight())
        	min = min - 50;
        
        // calculates the Font Size
        // if the minimum ScreenAttr is less 250 it will be 12
        // otherwise it'll be 16
        int FBfontSize;
        if (min < 250)
        	FBfontSize = 12;
        else
        	FBfontSize = 16;
        
        Config.initiate(v.getWidth(),			// displayWidth
        		v.getHeight() - 50,				// displayHeight
        		min,							// minScreenAttr
        		min / 10,						// optFieldBtSize 
        		min / 40,						// optFieldPadding
        		min / 120,						// optFieldLine
        		9 * (min / 10) + 2 * (min / 40),// fieldLength
        		(v.getWidth() - (9 * (min / 10) + 2 * (min / 40)) ) / 2,	// fieldStartXPos
        		((v.getHeight() - 50) - (9 * (min / 10) + 2 * (min / 40)) ) / 2,	// fieldStartYPos
        		FBfontSize,						// FontSize
        		min / 8,						// optMenuBtHeight
        		min / 2,						// optMenuBtWidth
        		(v.getWidth() / 2) - (min / 4)	// menutStartXPos						
        		);
        
       
    }
    
}