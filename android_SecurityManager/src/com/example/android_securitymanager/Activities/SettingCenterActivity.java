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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting_center);
		
		final SharedPreferences sPre = getSharedPreferences("config", MODE_PRIVATE);	
		boolean preIschecked = sPre.getBoolean("auto_update", false);
		
		view_update = (View_title_desc_style) findViewById(R.id.view_setting_center_update);
		view_update.setChecked(preIschecked);
		view_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (view_update.is_checked() == true){
					view_update.setChecked(false);
				}
				else{
					view_update.setChecked(true);
				}
				
				SharedPreferences.Editor editor = sPre.edit();
				editor.putBoolean("auto_update", view_update.is_checked());
				editor.commit();
			}
		});
	}
}
