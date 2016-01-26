package com.example.android_securitymanager.Activities;

import com.example.android_securitymanager.R;
import com.example.android_securitymanager.Views.View_title_desc_style;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Setup2Activity extends Activity {
	View_title_desc_style view_bind_sim = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup2);
		
		final SharedPreferences sPre = getSharedPreferences("config", MODE_PRIVATE);	
		boolean preIschecked = sPre.getBoolean("bind_sim", false);
		
		view_bind_sim = (View_title_desc_style) findViewById(R.id.view_setup2_bind_sim);
		view_bind_sim.setChecked(preIschecked);
		view_bind_sim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (view_bind_sim.is_checked() == true){
					view_bind_sim.setChecked(false);
				}
				else{
					view_bind_sim.setChecked(true);
				}
				
				SharedPreferences.Editor editor = sPre.edit();
				editor.putBoolean("auto_update", view_bind_sim.is_checked());
				editor.commit();
			}
		});
	}
	
	public void next(View view){
		Intent intent = new Intent(this, Setup3Activity.class);
		
		startActivity(intent);
		
		finish();
		
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入动画和退出动画
	}
	
	public void pre(View view){
		Intent intent = new Intent(this, Setup1Activity.class);
		
		startActivity(intent);
		
		finish();
		
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}
}
