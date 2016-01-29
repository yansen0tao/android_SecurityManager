package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android_securitymanager.R;

public class Setup3Activity extends SettingBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup3);
	}
	
	public void showNextPage(){
		Intent intent = new Intent(this, Setup4Activity.class);
		startActivity(intent);
		
		finish();
		
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入动画和退出动画
	}
	
	public void showPrePage(){
		Intent intent = new Intent(this, Setup2Activity.class);
		startActivity(intent);	
		
		finish();
		
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}
}
