package de.fhd.medien.mait.sfa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.widget.Button;

/**
 * Creates buttons especially built for the use in menus or fields.
 * Its Caption can contain numbers and text as well. 
 */
public class GameButton extends Button{

	static final int stateDefault = 0;
	static final int stateFocused = 1;
	static final int statePressed = 2;

	// Define the colors
	static final int fillDefault = 0xefffffff;		// white
	static final int textDefault = 0xff000000;		// black
	
	static final int fillPressed = 0xa0f70B49;		// red
	static final int textPressed = 0xffffffff;		// white
	
	static final int fillFocused = 0xa0bdbbbc;		// grey
	static final int textFocused = 0xffffffff;		// white
	
	// This variable defines the status of the Button
	private int mState = stateDefault;
	
	// For each status one Bitmap
	protected Bitmap defaultBitmap;
	protected Bitmap focusedBitmap;
	protected Bitmap pressedBitmap;
	
	// Basic properties of each button
	private int height;
	private int width;
	private int lineSize;
	private int textSize;
	//private int radius;
	
	private String caption;
	
	/**
	 * Creates a simple Button with a caption
	 * @param context in best case 'this'
	 * @param _caption caption to be displayed
	 * @param _width width of the button
	 * @param _height height of the button
	 * @param _lineSize Width of the brush. '2' is best.
	 * @param _textSize Size of the text. 10 ~ 25 is best.
	 * @param _bold whether the text should be displayed bold or not
	 */
	public GameButton(Context context, String _caption, int _width, int _height, int _lineSize, int _textSize, boolean _bold) {
		super(context);
		caption = _caption;
	    width = _width;
	    height = _height;
	    lineSize = _lineSize;
	    textSize = _textSize;
	    
		setClickable(true);
		
		// Set the background to delete the original Button image
		setBackgroundColor(Color.TRANSPARENT);
		
		// We now need to reset all steps of a button
		// 1) default
		defaultBitmap = Bitmap.createBitmap(width, height, true);
		// 2) focused
		focusedBitmap = Bitmap.createBitmap(width, height, true);
		// 3) pressed
		pressedBitmap = Bitmap.createBitmap(width, height, true);
		
		// Create new Bitmaps for this Button
		// 1) default
	    this.drawEmptyBitmap(defaultBitmap, fillDefault);
	    this.drawTextInBitmap(defaultBitmap, textDefault, _bold);
	    // 2) focused
	    this.drawEmptyBitmap(focusedBitmap, fillFocused);
	    this.drawTextInBitmap(focusedBitmap, textFocused, _bold);
	    // 3) pressed
	    this.drawEmptyBitmap(pressedBitmap, fillPressed);
	    this.drawTextInBitmap(pressedBitmap, textPressed, _bold);

   	}
	
	/**
	 * Draws a Bitmap for a certain Button
	 * @param current The status of the Button that should be redrawn
	 * @param color Button is filled with this color 
	 */
	protected void drawEmptyBitmap(Bitmap current, int color){		
		// We need the Canvas object to draw on a Bitmap
		Canvas canvas = new Canvas();
		
		// First we draw on the default image
		canvas.setDevice(current);
		
		// The Paint object represents our brush
		Paint paint = new Paint();
		paint.setAntiAlias(true); 
		
		// Give the brush a certain color
		paint.setColor(color);
		// ...and a width
		paint.setStrokeWidth(lineSize);
		// ...and some style
		paint.setStyle(Style.FILL_AND_STROKE);
		
		// The Path is needed to 'guide' the brush
	    Path path = new Path(); 
	    // Now we specify a Rect/RoundRect
	    RectF r = new RectF(1, 1, width-1, height-1);
	    path.addRoundRect(r, 5, 5, Direction.CCW);

	    // draw path on Canvas with the defined "brush"
	    canvas.drawPath(path, paint);
	}
	
	/**
	 * Draws a text into a certain Button
	 * @param current The status of the Button that should be redrawn
	 * @param color Button is filled with this color 
	 * @param _bold whether the text should be displayed bold or not
	 */
	protected void drawTextInBitmap(Bitmap current, int color, boolean _bold){
		// We need the Canvas object to draw on a Bitmap
		Canvas canvas = new Canvas();
		
		// First we draw on the default image
		canvas.setDevice(current);
		
	    // prepare the "brush" for the Text
	    Paint paintText = new Paint();
	    paintText.setAntiAlias(true);
	    paintText.setTextSize(textSize);
	    paintText.setFakeBoldText(_bold);
	    paintText.setColor(color);
	   
	    // Here we calculate the xPos of the text with the help of measureText()
	    // which gives us the width in px of a certain text
	    // Help measuring the text height: http://java.sun.com/docs/books/tutorial/figures/2d/font-metrics.GIF
	    float textXPos = ((width-paintText.measureText(caption) ) / 2);
	    float textYPos = (height - (paintText.ascent() + paintText.descent()) ) / 2;
	    
	    // draw Text
	    canvas.drawText(caption, textXPos, textYPos, paintText);
	}
	
	/**
	 * Changes bitmaps on user action
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		switch (mState) {
		case stateDefault:
			canvas.drawBitmap(defaultBitmap, 0, 0, null);
			break;
		case stateFocused:
			canvas.drawBitmap(focusedBitmap, 0, 0, null);
			break;
		case statePressed:
			canvas.drawBitmap(pressedBitmap, 0, 0, null);
			break;
		}
	}

	/**
	 * Changes state on user action
	 */
	@Override
	protected void drawableStateChanged() {
		if (isPressed()) {
			mState = statePressed;
		} else if (hasFocus()) {
			mState = stateFocused;
		} else {
			mState = stateDefault;
		}
		invalidate();
	}
	
	/**
	 * Redraws the Button as a normal Button with 3 states
	 */
	public void redraw(boolean _bold){
		redraw(fillDefault,textDefault,fillFocused,textFocused,fillPressed,textPressed, _bold);
	}
	
	/**
	 * Redraws the Button with the given attributes
	 * @param _fillDefault
	 * @param _textDefault
	 * @param _fillFocused
	 * @param _textFocused
	 * @param _fillPressed
	 * @param _textPressed
	 * @param _bold whether the text should be displayed bold or not
	 */
	public void redraw(int _fillDefault, int _textDefault, int _fillFocused, int _textFocused, int _fillPressed, int _textPressed, boolean _bold){
		// We now need to reset all steps of a button
		defaultBitmap = null;
		focusedBitmap = null;
		pressedBitmap = null;
		// 1) default
		defaultBitmap = Bitmap.createBitmap(width, height, true);
		// 2) focused
		focusedBitmap = Bitmap.createBitmap(width, height, true);
		// 3) pressed
		pressedBitmap = Bitmap.createBitmap(width, height, true);
		
		// Create new Bitmaps for this Button
		// 1) default
	    this.drawEmptyBitmap(defaultBitmap, _fillDefault);
	    this.drawTextInBitmap(defaultBitmap, _textDefault, _bold);
	    // 2) focused
	    this.drawEmptyBitmap(focusedBitmap, _fillFocused);
	    this.drawTextInBitmap(focusedBitmap, _textFocused, _bold);
	    // 3) pressed
	    this.drawEmptyBitmap(pressedBitmap, _fillPressed);
	    this.drawTextInBitmap(pressedBitmap, _textPressed, _bold);
	}
	
	/**
	 * Returns the caption of the Button
	 * @return
	 */
	public String getCaption(){
		return this.caption;
	}
	
	/**
	 * Sets the caption of this Button and automatically redraws it.
	 * @param newCapt new Caption for this button
	 * @param _bold whether the text should be displayed bold or not
	 */
	public void setCaption(String newCapt, boolean _bold){
		caption = newCapt;
		redraw(_bold);
	}
	
	/**
	 * Clears the caption of this button and automatically redraws it
	 * @param _bold whether the text should be displayed bold or not
	 */
	public void clearCaption(boolean _bold){
		setCaption("", _bold);
	}
	
	/**
	 * Removes the caption info of this button but does not redraw it.
	 * If you want to automatically redraw this button you may use clearCaption().
	 */
	public void deleteCaption(){
		caption = "";
	}

	
   } 
