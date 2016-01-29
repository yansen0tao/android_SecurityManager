package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public abstract class SettingBaseActivity extends Activity {
	SharedPreferences spRecord = null;
	private GestureDetector detector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		spRecord = getSharedPreferences("config", MODE_PRIVATE);	
		
		detector = new GestureDetector(this, new SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				
				if (Math.abs(e1.getRawY() - e2.getRawY()) > 100)
					return true;
				
				if (e1.getRawX() - e2.getRawX() > 200){
					showNextPage();
					return true;
				}
				
				if (e2.getRawX() - e1.getRawX() > 200){
					showPrePage();
					return true;
				}
				
				if (velocityX < 200)
					return true;
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		
	}
	
	public void next(View view){
		showNextPage();
	}
	
	public void pre(View view){
		showPrePage();
	}
	
	protected abstract void showNextPage();
	protected abstract void showPrePage();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		
		return super.onTouchEvent(event);
	}
}
