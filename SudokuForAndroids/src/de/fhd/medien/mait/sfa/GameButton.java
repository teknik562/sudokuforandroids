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
import android.view.View;
import android.widget.Button;
	
public class GameButton extends Button{

	static final int stateDefault = 0;
	static final int stateFocused = 1;
	static final int statePressed = 2;

	// Define the colors
	static final int fillDefault = 0x00000000;
	static final int textDefault = 0x00000000;
	
	static final int fillPressed = 0;
	static final int textPressed = 0;
	
	static final int fillFocused = 0;
	static final int textFocused = 0;
	
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
	private int radius;
	
	private String caption;
	
	/**
	 * Creates a simple Button holding a caption
	 * @param context in best case 'this'
	 * @param _caption caption to be displayed
	 * @param _width width of the button
	 * @param _height height of the button
	 * @param _lineSize Width of the brush. '2' is best.
	 * @param _textSize Size of the text. 10 ~ 25 is best.
	 */
	public GameButton(Context context, String _caption, int _width, int _height, int _lineSize, int _textSize) {
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
	    this.drawEmptyBitmap(defaultBitmap, 0xffffffff);
	    this.drawTextInBitmap(defaultBitmap, 0xff0000ff);
	    // 2) focused
	    this.drawEmptyBitmap(focusedBitmap, 0xff97C024);
	    this.drawTextInBitmap(focusedBitmap, 0xffffffff);
	    // 3) pressed
	    this.drawEmptyBitmap(pressedBitmap, 0xff97C024);
	    this.drawTextInBitmap(pressedBitmap, 0xffffffff);
	    
	    // define OnClickListener for the Button
	    setOnClickListener(onClickListener);
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
	 */
	protected void drawTextInBitmap(Bitmap current, int color){
		// We need the Canvas object to draw on a Bitmap
		Canvas canvas = new Canvas();
		
		// First we draw on the default image
		canvas.setDevice(current);
		
	    // prepare the "brush" for the Text
	    Paint paintText = new Paint();
	    paintText.setAntiAlias(true);
	    paintText.setTextSize(textSize);
	    paintText.setTextAlign(Paint.Align.CENTER);
	    paintText.setColor(color);
	   
	    // Here we calculate the xPos of the text with the help of measureText()
	    // which gives us the width in px of a certain text
	    float textXPos = ((width-paintText.measureText(caption) ) / 2);
	    float textYPos = (paintText.descent()-paintText.ascent()) ;
	    
	    // draw Text
	    canvas.drawText(caption, textXPos, textYPos, paintText);
	}
	
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
	         
	@Override
	protected void drawableStateChanged() {
		if (isPressed()) {
			mState = statePressed;
		} else if (hasFocus()) {
			mState = stateFocused;
		} else {
			mState = stateDefault;
		}
		// force the redraw of the image
		// onDraw() will be called
		invalidate();
	}
	
	private OnClickListener onClickListener =
		new OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
	
	/**
	 * Returns the caption of the Button
	 * @return
	 */
	public String getCaption(){
		return this.caption;
	}
	
	
   } 
