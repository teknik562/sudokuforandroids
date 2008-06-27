package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class MainMenu extends Activity {
  /** Background color */
  private static final int fillBgMenu = 0x1007C024;
  
  // Main layout
  AbsoluteLayout absLayoutMenu;
  
  //the Android- logo- Bitmap in the upper area of the main- menue
  Bitmap picAndroidOrg;
  Bitmap picAndroidScaled;
  BitmapDrawable picAndroidDraw;
  ImageView picAndroidView;
  
  //variables for the scaling and positioning of the android- icon
  Matrix picMatrix; //the scaling- matrix
  int picWidth;   
  int picHeight;
  int upperOffset;
  int picNeededWidth, picNeededHeight;
  float picScaleFact;  //scale- factor
  
  
  // Buttons
  GameButton newGameBt;
  GameButton contGameBt;
  GameButton settingsBt;
  GameButton highscoreBt;
  GameButton helpBt;
  GameButton creditsBt;
  
  //variable for the cheat- mode
  boolean cheatModeActive = false;
  
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        // get the info from the display and calculate layout
        calculateProperties();
        
        //load the icon- picture from the res- directory and store it in a Bitmap
        picAndroidOrg = BitmapFactory.decodeResource(getResources(),
        R.drawable.icondroid);
        
        
     
        //calculate the size and position for the logo depending on Config
        calculatePicProperties();
        
        // initiate layout
        absLayoutMenu = new AbsoluteLayout(this);
        absLayoutMenu.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
        absLayoutMenu.setBackgroundColor(fillBgMenu);

        // create & place Buttons & icon on layout
        placePicture();
        createButtons();
        placeButtons();
     
        
        // set the onClickListeners
        helpBt.setOnClickListener(helpClick);
        creditsBt.setOnClickListener(creditsClick);
        newGameBt.setOnClickListener(newGameClick);
        settingsBt.setOnClickListener(settingsClick);
        highscoreBt.setOnClickListener(highscoreClick);
        contGameBt.setOnClickListener(loadClick);
        setContentView(absLayoutMenu);

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
    
    
    /**
     * this method creates a new imageView and puts the android- icon onto it.
     * The icon is scaled and positioned relatively to the display- sizes
     */
    private void placePicture()
    {
      picAndroidView = new ImageView(this);
      picAndroidView.setImageDrawable(picAndroidDraw);
      picAndroidView.setScaleType(ScaleType.CENTER);
      
      absLayoutMenu.addView(picAndroidView,
          new AbsoluteLayout.LayoutParams(picAndroidScaled.getWidth(),
              picAndroidScaled.getHeight(), Config.displayWidth/2 - (int)(picAndroidOrg.getWidth()* picScaleFact)/2, upperOffset));
    }
    
    
    /**
     * this method calculates the properties of the picture. E.g. what size it needs to be depending on 
     * the display- resolution. The method distinguishes between HVGA and QVGA and sets own size- information
     * for both resolutions
     */
    private void calculatePicProperties()
    {
      picHeight = picAndroidOrg.getHeight(); //the height and Width of the original bitmap is stored in variables
      picWidth = picAndroidOrg.getWidth();
      
      if(Config.displayHeight > 340)   //is the display HVGA....
        picNeededHeight = (Config.displayHeight - 6*Config.optMenuBtHeight - Config.displayHeight/20 - Config.displayHeight/7) ;
                      
      else              //...or smaller?
      picNeededHeight = (Config.displayHeight - 6*Config.optMenuBtHeight - Config.displayHeight/20) ;
      
      //the scaling- factor is calculated
      picScaleFact = (float) picNeededHeight / picHeight; 
      
      //a new scaling- matrix is created and is scaled with the scaling- factor
      picMatrix = new Matrix();
      picMatrix.postScale(picScaleFact, picScaleFact);
      
      
      
      //with the help of the matrix a new and scaled Bitmap is created
      picAndroidScaled = Bitmap.createBitmap(picAndroidOrg,
                          0,  //x- position
                          0,      // y- position
                          picAndroidOrg.getWidth(), //the width of the pic
                          picAndroidOrg.getHeight(), // the height
                          picMatrix,  //the matrix which scales the pic
                          true);  //filter
      
      //in order to be able to put it onto an image- view, a new drawable bitmap is created depending on the scaled bitmap
      picAndroidDraw = new BitmapDrawable(picAndroidScaled); 
      
      //the offset between the upper screen border and the icon
      upperOffset = (Config.displayHeight - (picAndroidScaled.getHeight() + 6*Config.optMenuBtHeight))/4;
      
      
    }
    
    /**
     * this onClickListener starts the Keypad on testing- reasons
     */
    OnClickListener dummyNewGameClick = new OnClickListener()
    {

    //@Override
    public void onClick(View arg0) {
      
        Intent showField = new Intent(MainMenu.this, Field.class);
        startActivity(showField);
    }
      
    };
    
    DialogInterface.OnClickListener newGame = new DialogInterface.OnClickListener(){
      public void onClick(DialogInterface dialoginterface, int i) {
    	  // Save the chosen dificulty
    	  Config.difficulty = ++i;
        Intent showField = new Intent(MainMenu.this, Field.class);
        showField.putExtra("starter", "NORMAL");
      startActivity(showField);
    }
    };
    
    private void openNewGameDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.newGame)
            .setItems(R.array.difficulty, newGame).show();
    }
    
    /** When clicked on Help a new View is opened where the user is shown how to use this app */
    OnClickListener loadClick = new OnClickListener(){
      public void onClick(View v) {
        Intent loadGame = new Intent(MainMenu.this, Load.class);
        startActivity(loadGame);
      }
    };
    
    /** When clicked on Help a new View is opened where the user is shown how to use this app */
    OnClickListener newGameClick = new OnClickListener(){
      public void onClick(View v) {
        openNewGameDialog();
      }
    };
   
   
    /**
     * this onClickListener launches the settings- screen
     */
    OnClickListener settingsClick = new OnClickListener(){

    public void onClick(View arg0) {
      Intent showSettings = new Intent(MainMenu.this, Settings.class);
      startActivity(showSettings);  
    }
      
    };

    /** When clicked on Help a new View is opened where the user is shown how to use this app */
    OnClickListener highscoreClick = new OnClickListener(){
      public void onClick(View v) {
        Intent in_highscore = new Intent(MainMenu.this, Highscore.class);
        startActivity(in_highscore);
      }
    };

    /** When clicked on Credits a new View is opened where the participants are shown */
    OnClickListener creditsClick = new OnClickListener(){
        //@Override
          public void onClick(View v) {
            Intent showCredits = new Intent(MainMenu.this, Credits.class);
        startActivity(showCredits);
          } 
    };
    
    /** When clicked on Help a new View is opened where the user is shown how to use this app */
    OnClickListener helpClick = new OnClickListener(){
        //@Override
          public void onClick(View v) {
            Intent showHelp = new Intent(MainMenu.this, Help.class);
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
        
        
        String playerName;
        // get PlayerName if there is an fitting Intent-Extra
        if(getIntent().getStringExtra("playerName") != null)
          playerName = getIntent().getStringExtra("playerName");
        // get Player Name if there is no Intent-Extra (from config)
        else
          playerName = Config.playerName;
        
        
        Config.initiate(v.getWidth(),     // displayWidth
            v.getHeight() - 50,       // displayHeight
            min,              // minScreenAttr
            min / 10,           // optFieldBtSize 
            min / 40,           // optFieldPadding
            min / 120,            // optFieldLine
            9 * (min / 10) + 2 * (min / 40),// fieldLength
            (v.getWidth() - (9 * (min / 10) + 2 * (min / 40)) ) / 2,  // fieldStartXPos
            ((v.getHeight() - 50) - (9 * (min / 10) + 2 * (min / 40)) ) / 2,  // fieldStartYPos
            FBfontSize,           // FontSize
            min / 8,            // optMenuBtHeight
            min / 2,            // optMenuBtWidth
            (v.getWidth() / 2) - (min / 4),  // menutStartXPos
            playerName
            );
        
       
    }
    
}