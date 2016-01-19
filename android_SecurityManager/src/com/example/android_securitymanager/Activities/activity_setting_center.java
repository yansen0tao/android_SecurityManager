package com.example.android_securitymanager.Activities;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.android_securitymanager.R;
import com.example.android_securitymanager.Views.view_setting_center_items_update;

public class activity_setting_center extends Activity {
	view_setting_center_items_update view_update = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting_center);
		
		view_update = (view_setting_center_items_update) findViewById(R.id.view_setting_center_update);
		view_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (view_update.is_checked() == true){
					view_update.setChecked(false);
				}
				else{
					view_update.setChecked(true);
				}
			}
		});
	}
	
}
