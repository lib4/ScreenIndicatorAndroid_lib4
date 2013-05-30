package com.lib4.screenindicatortypeanimation;
/**
 * @author AnasAbubacker
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class IndicatorView extends LinearLayout {

    private Bitmap indicatorBitmap;
    private Bitmap inactiveIndicatorBitmap;
    private Bitmap activeIndicatorBitmap;
    private AnimateDrawable mDrawable;
    Context context;

    private int TOTAL_SCREEN_NUMBER = 4;
    private int CURRENTSCREEN = 1;
    private int NEXT_SCREEN = 2;
    Drawable movingDot;
    public IndicatorView(Context context) {
	super(context);
	this.context = context;
	initialize(context);
	
    }
    
    
    public void setNumberofScreens(int numberOfScreens){
	TOTAL_SCREEN_NUMBER	=	numberOfScreens;
    }
    
    public void setDrawables(int movingDot,int backGround,int inactiveDots ){
	
	indicatorBitmap = BitmapFactory.decodeResource(context.getResources(),
		backGround);
	inactiveIndicatorBitmap = BitmapFactory.decodeResource(
		context.getResources(), inactiveDots);
	activeIndicatorBitmap = BitmapFactory.decodeResource(
		context.getResources(), movingDot);
	this.movingDot = context.getResources().getDrawable(movingDot);
	this.movingDot.setBounds(0, 0, activeIndicatorBitmap.getWidth(),
		activeIndicatorBitmap.getHeight());
	setFocusable(true);
	setFocusableInTouchMode(true);
	this.setBackgroundResource(backGround);
	
    }
    
    public void switchToScreen(int sourceScreenNumber,int destScreennumber){
	CURRENTSCREEN	=	sourceScreenNumber;
	NEXT_SCREEN	=	destScreennumber;
	
	int distance = indicatorBitmap.getWidth() / TOTAL_SCREEN_NUMBER;
	int travelTo = 0;
	int start;
	start = (CURRENTSCREEN * distance) - (distance / 2)
		- (activeIndicatorBitmap.getWidth() / 2);
	if((NEXT_SCREEN==1 && CURRENTSCREEN==1)||(NEXT_SCREEN==TOTAL_SCREEN_NUMBER && CURRENTSCREEN==TOTAL_SCREEN_NUMBER))
	    travelTo =	start;
	else
	    if(CURRENTSCREEN<NEXT_SCREEN)
		travelTo = start + distance;
	    else
		travelTo = start - distance;

	Animation an;
	if(CURRENTSCREEN<NEXT_SCREEN)
	 an = new TranslateAnimation(start, travelTo,
		indicatorBitmap.getHeight() / 2
			- (activeIndicatorBitmap.getHeight() / 2),
		indicatorBitmap.getHeight() / 2
			- (activeIndicatorBitmap.getHeight() / 2));
	else
	    an = new TranslateAnimation(start, travelTo,
			indicatorBitmap.getHeight() / 2
				- (activeIndicatorBitmap.getHeight() / 2),
			indicatorBitmap.getHeight() / 2
				- (activeIndicatorBitmap.getHeight() / 2));

	an.setInterpolator(new AccelerateDecelerateInterpolator());
	an.setDuration(300);
	an.setRepeatCount(0);
	an.initialize(0, 0, 100, 100);
	mDrawable = new AnimateDrawable(movingDot, an);
	an.startNow();
    }

    public IndicatorView(Context context, AttributeSet attrs) {
	super(context, attrs);
	initialize(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
	//Log.e("I m here","I m here");
	int distance = indicatorBitmap.getWidth() / TOTAL_SCREEN_NUMBER;
	
	int i =1;
	while(i<=TOTAL_SCREEN_NUMBER){
	    int start = (i * distance) - (distance / 2)
		- (activeIndicatorBitmap.getWidth() / 2);
	    
	    canvas.drawBitmap(
			inactiveIndicatorBitmap,
			start,
			indicatorBitmap.getHeight() / 2
				- (inactiveIndicatorBitmap.getHeight() / 2), null);
	    i++;
	}

	mDrawable.draw(canvas);
	invalidate();
    }

    private void initialize(Context context) {

	indicatorBitmap = BitmapFactory.decodeResource(context.getResources(),
		R.drawable.tutorial_bullets_bg);
	inactiveIndicatorBitmap = BitmapFactory.decodeResource(
		context.getResources(), R.drawable.tutorial_number_inactive);
	activeIndicatorBitmap = BitmapFactory.decodeResource(
		context.getResources(), R.drawable.tutorial_number_active);
	movingDot = context.getResources().getDrawable(
		R.drawable.tutorial_number_active);
	movingDot.setBounds(0, 0, activeIndicatorBitmap.getWidth(),
		activeIndicatorBitmap.getHeight());
	setFocusable(true);
	setFocusableInTouchMode(true);
	this.setBackgroundResource(R.drawable.tutorial_bullets_bg);
	
    }

}
