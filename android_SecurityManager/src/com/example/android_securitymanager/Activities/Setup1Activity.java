package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android_securitymanager.R;

public class Setup1Activity extends SettingBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup1);
	}

	public void showNextPage(){
		Intent intent = new Intent(this, Setup2Activity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入动画和退出动画
	}

	@Override
	protected void showPrePage() {
	}
}
