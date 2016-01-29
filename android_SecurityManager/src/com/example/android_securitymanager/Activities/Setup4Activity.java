package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.android_securitymanager.R;

public class Setup4Activity extends SettingBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setup4);
	}

	public void showNextPage() {
		Intent intent = new Intent(this, SetupFinishActivity.class);
		startActivity(intent);
		finish();
		
		SharedPreferences.Editor editor = spRecord.edit();
		editor.putBoolean("setting_guided", true);
		editor.commit();
		
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	public void showPrePage() {
		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}
}
