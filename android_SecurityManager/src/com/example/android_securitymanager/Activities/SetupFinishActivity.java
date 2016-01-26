package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.android_securitymanager.R;

public class SetupFinishActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup_finish);
	}
	
	public void enterSettingGuide(View view){
		Intent intent = new Intent(this, Setup1Activity.class);
		startActivity(intent);
		
		finish();
		
		SharedPreferences sPre = getSharedPreferences("config", MODE_PRIVATE);	
		
		SharedPreferences.Editor editor = sPre.edit();
		editor.putBoolean("setting_guided", false);
		editor.commit();
	}
}
