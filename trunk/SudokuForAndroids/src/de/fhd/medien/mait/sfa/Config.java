package de.fhd.medien.mait.sfa;


/**
 * This class provides several information calculated for layout use 
 * depending on the display's properties and values needed for Activities. 
 * 
 * One can access information like this:
 * 
 * Config.displayWidth <- Gives the display's width
 * 
 */
public class Config {
	/** Width of the display */
	static int displayWidth = 0;
	/** Height of the display (minus the 50px of the Android panel) */
	static int displayHeight = 1;
	/** The minimum screen attribute which could be 
	 * either the height (minus the 50 px) or the width */
	static int minScreenAttr = 2;
	/** Calulated size of the FieldButtons */
	static int optFieldBtSize = 3;
	/** Calculated padding between each 3 buttons (horizontally or vertically) */
	static int optFieldPadding = 4;
	/** Calculated width of a spacing line between each block of 3*3 */
	static int optFieldLine = 5;
	/** Length of the GamePanel */
	static int fieldLength = 6;
	/** Calculated x start position for the field */
	static int fieldStartXPos = 7;
	/** Calculated y start position for the field */
	static int fieldStartYPos = 8;
	/** Calculated best font size */
	static int FontSize = 9;
	/** Calculated height of menu buttons */
	static int optMenuBtHeight = 10;
	/** Calculated width of menu buttons */
	static int optMenuBtWidth = 11;
	/** Calculated start x of the menu button */
	static int menutStartXPos = 12;
	/** saves the name of the player */
	static String playerName = "";
	/** Saves the chosen difficulty */
	static int difficulty;
	/** stores the time the game was started */
	static int startTime = 0;
	/** stores the time needed until now*/
	static int neededTime = 0;
	/** Time the actual session has taken */
	static int time = 0;
	/** Best linesize to draw buttons */
	static final int lineSize = 2;
	
	/** Represents the number of cheats of the actual game */
	static int cheatCount = 0;
	
	/** Is cheat mode activated? */
	static boolean cheatModeActive = false;
	
	
	/** Sets a lot of basic values needed for layout calculation */
	static void initiate(int _displayWidth, int _displayHeight, int _minScreenAttr,
			int _optFieldBtSize, int _optFieldPadding, int _optFieldLine, int _fieldLength,
			int _fieldStartXPos, int _fieldStartYPos , int _FontSize, int _optMenuBtHeight,
			int _optMenuBtWidth, int _menutStartXPos, String _playerName){
		displayWidth = _displayWidth;
		displayHeight = _displayHeight;
		minScreenAttr = _minScreenAttr;
		optFieldBtSize = _optFieldBtSize;
		optFieldPadding = _optFieldPadding;
		optFieldLine = _optFieldLine;
		fieldLength = _fieldLength;
		fieldStartXPos = _fieldStartXPos;
		fieldStartYPos = _fieldStartYPos;
		FontSize = _FontSize;
		optMenuBtHeight = _optMenuBtHeight;
		optMenuBtWidth = _optMenuBtWidth;
		menutStartXPos = _menutStartXPos;
		playerName = _playerName;
	}
	
}
