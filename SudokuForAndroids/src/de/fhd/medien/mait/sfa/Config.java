package de.fhd.medien.mait.sfa;

/**
 * This class provides several information calculated for layout use 
 * depending on the display's properties. 
 * 
 * One can access information like this:
 * 
 * Config.displayWidth <- Gives the display's width
 * 
 */
public class Config {
	/** Width of the display */
	public static int displayWidth = 0;
	/** Height of the display (minus the 50px of the Android panel) */
	public static int displayHeight = 1;
	/** The minimum screen attribute which could be 
	 * either the height (minus the 50 px) or the width */
	public static int minScreenAttr = 2;
	/** Calulated size of the FieldButtons */
	public static int optFieldBtSize = 3;
	/** Calculated padding between each 3 buttons (horizontally or vertically) */
	public static int optFieldPadding = 4;
	/** Calculated width of a spacing line between each block of 3*3 */
	public static int optFieldLine = 5;
	/** Length of the GamePanel */
	public static int fieldLength = 6;
	/** Calculated x start position for the field */
	public static int fieldStartXPos = 7;
	/** Calculated y start position for the field */
	public static int fieldStartYPos = 8;
	/** Calculated best font size for the FieldButtons */
	public static int optFieldBtFontSize = 9;
	
	
	public static void initiate(int _displayWidth, int _displayHeight, int _minScreenAttr,
			int _optFieldBtSize, int _optFieldPadding, int _optFieldLine, int _fieldLength,
			int _fieldStartXPos, int _fieldStartYPos , int _optFieldBtFontSize){
		displayWidth = _displayWidth;
		displayHeight = _displayHeight;
		minScreenAttr = _minScreenAttr;
		optFieldBtSize = _optFieldBtSize;
		optFieldPadding = _optFieldPadding;
		optFieldLine = _optFieldLine;
		fieldLength = _fieldLength;
		fieldStartXPos = _fieldStartXPos;
		fieldStartYPos = _fieldStartYPos;
		optFieldBtFontSize = _optFieldBtFontSize;
	}
	
}
