package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.android_securitymanager.R;

public class Setup4Activity extends Activity {
	private SharedPreferences sPre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setup4);
	}

	public void next(View view) {
		Intent intent = new Intent(this, SetupFinishActivity.class);
		startActivity(intent);
		finish();
		
		sPre = getSharedPreferences("config", MODE_PRIVATE);	
		
		SharedPreferences.Editor editor = sPre.edit();
		editor.putBoolean("setting_guided", true);
		editor.commit();
		
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入动画和退出动画
	}

	public void pre(View view) {
		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}
}
