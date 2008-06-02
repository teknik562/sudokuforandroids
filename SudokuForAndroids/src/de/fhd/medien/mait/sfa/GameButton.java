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

	private int mState = StateDefault;
	
	private Bitmap defaultBitmap;
	private Bitmap focusedBitmap;
	private Bitmap pressedBitmap;
	
	private int size;
	private int lineSize;
	private int textSize;
	private int[] candidateValues = new int[6];
	private String value;

	
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
	    RectF r = new RectF(1, 1, size-1, size-1);
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
	   	
	    int normalizedSize = size - lineSize;
	   
	    // Here we calculate the xPos of the text with the help of getTextWidths()
	    // which gives us the width in px of a certain text
	    float textXPos = (size-paintText.getTextWidths(value, new float[1]) ) / 2;
	    float textYPos = (paintText.descent()-paintText.ascent()) ;
	    
	    // draw Text
	    canvas.drawText(value, textXPos, textYPos, paintText);
	}
	
	/**
	 * 
	 * @param context Ist im besten Fall immer 'this'
	 * @param _value Inhalt der nachher angezeigt wird
	 * @param _size Da es ein Quadrat ist, die Seitenlänge in px
	 * @param _lineSize Breite des Pinsels. 2 hat sich als normal erwiesen
	 * @param _textSize Textgröße. 10 ~ 25
	 */
	public GameButton(Context context, String _value, int _size, int _lineSize, int _textSize) {
		super(context);
		value = _value;
	    size = _size;
	    lineSize = _lineSize;
	    textSize = _textSize;
	    
		setClickable(true);
		
		// Set the background to delete the original Button image
		setBackgroundColor(Color.TRANSPARENT);
		
		// We now need to reset all steps of a button
		// 1) default
		defaultBitmap = Bitmap.createBitmap(size, size, true);
		// 2) fucused
		focusedBitmap = Bitmap.createBitmap(size, size, true);
		// 3) pressed
		pressedBitmap = Bitmap.createBitmap(size, size, true);

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
	
	public int getValue(){
		return Integer.parseInt(this.value);
	}
	
	public int getCandidate(int index){
		return candidateValues[index];
	}
	
	public int[] getCandidates(){
		return candidateValues;
	}
	
   } 


