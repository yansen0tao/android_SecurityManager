package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.android_securitymanager.R;
import com.example.android_securitymanager.Views.View_title_desc_style;

public class SettingCenterActivity extends Activity {
	View_title_desc_style view_update = null;
	SharedPreferences spRecord = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting_center);
		
		spRecord = getSharedPreferences("config", MODE_PRIVATE);	
		boolean preIschecked = spRecord.getBoolean("auto_update", false);
		
		view_update = (View_title_desc_style) findViewById(R.id.view_setting_center_update);
		view_update.setChecked(preIschecked);
		view_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				view_update.setChecked(!view_update.is_checked());
				
				SharedPreferences.Editor editor = spRecord.edit();
				editor.putBoolean("auto_update", view_update.is_checked());
				editor.commit();
			}
		});
	}
}
