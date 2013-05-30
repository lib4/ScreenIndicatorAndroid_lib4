package com.lib4.screenindicatortypeanimation;
/**
 * @author AnasAbubacker
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LauncherActivity extends Activity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    RelativeLayout mRelativeLayout;
    IndicatorView mIndicatorView;
    private int CURRENT_SCREEN;
    private int TOTAL_NUM_SCREENS	=	5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_launcher);
	mRelativeLayout = (RelativeLayout) findViewById(R.id.parentview);
	mIndicatorView = new IndicatorView(this);
	mRelativeLayout.addView(mIndicatorView);
	mIndicatorView.setDrawables(R.drawable.tutorial_number_active,
		R.drawable.tutorial_bullets_bg, R.drawable.tutorial_number_inactive);
	CURRENT_SCREEN	=	1;
	mIndicatorView.setNumberofScreens(TOTAL_NUM_SCREENS);
	mIndicatorView.switchToScreen(CURRENT_SCREEN, CURRENT_SCREEN);
	gestureDetector = new GestureDetector(this, new CustomGestureDetector());
	gestureListener = new View.OnTouchListener() {
	    public boolean onTouch(View v, MotionEvent event) {

		return gestureDetector.onTouchEvent(event);
	    }
	};

	mRelativeLayout.setOnTouchListener(gestureListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.launcher, menu);
	return true;
    }

    /**
     * 
     * 
     * @author Anas Abubacker
     *         Right Left Gesture Detection
     * 
     */
    class CustomGestureDetector extends SimpleOnGestureListener {
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {
	    try {

		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
		    return false;
		// right to left swipe
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
			&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		    Toast.makeText(LauncherActivity.this, "Left Swipe",
			    Toast.LENGTH_SHORT).show();
		    mIndicatorView.switchToScreen(CURRENT_SCREEN,
			    getScreenNumberIncrement());
		} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
			&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		    Toast.makeText(LauncherActivity.this, "Right Swipe",
			    Toast.LENGTH_SHORT).show();
		    mIndicatorView.switchToScreen(CURRENT_SCREEN,
			    getScreenNumberDecrement());

		}
	    } catch (Exception e) {
		// nothing
	    }
	    return false;
	}

	@Override
	public boolean onDown(MotionEvent event) {

	    return true;
	}

    }
    
    private int getScreenNumberIncrement(){
	if(CURRENT_SCREEN==TOTAL_NUM_SCREENS){
	    CURRENT_SCREEN	=	TOTAL_NUM_SCREENS;
	    return CURRENT_SCREEN;
	}
	if(CURRENT_SCREEN<TOTAL_NUM_SCREENS)
	    CURRENT_SCREEN++;
	
	return CURRENT_SCREEN;
	
    }
    
    private int getScreenNumberDecrement(){
	
	if(CURRENT_SCREEN==1){
	    CURRENT_SCREEN	=	1;
	    return CURRENT_SCREEN;
	}
	if(CURRENT_SCREEN>1)
	    CURRENT_SCREEN--;
	
	return CURRENT_SCREEN;
	
    }

}
