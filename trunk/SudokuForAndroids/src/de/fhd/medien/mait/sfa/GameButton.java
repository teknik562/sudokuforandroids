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

	static final int StateDefault = 0;
	static final int StateFocused = 1;
	static final int StatePressed = 2;

	// Define the colors
	static final int fillDefault = 0x00000000;
	static final int textDefault = 0x00000000;
	
	static final int fillPressed = 0;
	static final int textPressed = 0;
	
	static final int fillFocused = 0;
	static final int textFocused = 0;
	
	static final int fillOriginal = 0;
	static final int textOriginal = 0;
	
	static final int fillCandidate = 0;
	
	private int mState = StateDefault;
	
	private Bitmap defaultBitmap;
	private Bitmap focusedBitmap;
	private Bitmap pressedBitmap;
	
	private int height;
	private int width;
	private int lineSize;
	private int textSize;

	private String caption;

	
	private void drawEmptyBitmap(Bitmap current, int color){
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
	
	private void drawTextInBitmap(Bitmap current, int color){
		// We need the Canvas object to draw on a Bitmap
		Canvas canvas = new Canvas();
		
		// First we draw on the default image
		canvas.setDevice(current);
		
	    // prepare the "brush" for the Text
	    Paint paintText = new Paint();
	    paintText.setAntiAlias(true);
	    paintText.setTextSize(textSize);
	    paintText.setTextAlign(Paint.Align.CENTER);
	    paintText.setColor(color);  // white
	   
	    // Here we calculate the xPos of the text with the help of getTextWidths()
	    // which gives us the width in px of a certain text
	    // We need to add 1 and lineSize because of FILL_AND_STROKE
	    float textXPos = lineSize + 1 + ((width-paintText.getTextWidths(caption, new float[caption.length()]) ) / 2);
	    float textYPos = (paintText.descent()-paintText.ascent()) ;
	    
	    // draw Text
	    canvas.drawText(caption, textXPos, textYPos, paintText);
	}
	
	/**
	 * 
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
		// 2) fucused
		focusedBitmap = Bitmap.createBitmap(width, height, true);
		// 3) pressed
		pressedBitmap = Bitmap.createBitmap(width, height, true);

	    this.drawEmptyBitmap(defaultBitmap, 0xffffffff);
	    this.drawTextInBitmap(defaultBitmap, 0xff0000ff);
	    
	    this.drawEmptyBitmap(focusedBitmap, 0xff97C024);
	    this.drawTextInBitmap(focusedBitmap, 0xffffffff);
	    
	    this.drawEmptyBitmap(pressedBitmap, 0xff97C024);
	    this.drawTextInBitmap(pressedBitmap, 0xffffffff);
	    
	    // define OnClickListener for the Button
	    setOnClickListener(onClickListener);
   	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		switch (mState) {
		case StateDefault:
			canvas.drawBitmap(defaultBitmap, 0, 0, null);
//	                   mDebug.append(mCaption + ":default\n");
			break;
		case StateFocused:
			canvas.drawBitmap(focusedBitmap, 0, 0, null);
//	                   mDebug.append(mCaption + ":focused\n");
			break;
		case StatePressed:
			canvas.drawBitmap(pressedBitmap, 0, 0, null);
//	                   mDebug.append(mCaption + ":pressed\n");
			break;
		}
	}
	         
	@Override
	protected void drawableStateChanged() {
		if (isPressed()) {
			mState = StatePressed;
		} else if (hasFocus()) {
			mState = StateFocused;
		} else {
			mState = StateDefault;
		}
		// force the redraw of the Image
		// onDraw will be called!
		invalidate();
	}
	
	private OnClickListener onClickListener =
		new OnClickListener() {
		@Override
		public void onClick(View arg0) {
//	                   mDebug.append(mCaption + ":click\n");
		}
	};
	
	public String getCaption(){
		return this.caption;
	}
	
	
   } 
