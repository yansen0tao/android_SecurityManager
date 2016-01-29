package com.example.android_securitymanager.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_securitymanager.R;
import com.example.android_securitymanager.Utils.MD5Utils;

public class HomeActivity extends Activity {
	private GridView gridViewId;
	private SharedPreferences spRecord = null;
	
	String[] name = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", 
								"手机杀毒", "缓存清理", "高级工具", "设置中心"};
	int[] icon_id = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps
							  , R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan
							  , R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};

	private boolean enterActivity(int item){

		if (item < 0 || item > 9)
			return false;
		
		switch (item){
		case 0:
			show_dialog();
			break;
		case 8:
			Intent intent = new Intent(this, SettingCenterActivity.class);
			startActivity(intent);
			break;
		}
		
		return true;
	}
	
	private void show_dialog(){
		String record = spRecord.getString("passwd", null);
		
		if (TextUtils.isEmpty(record))
			showPasswdSetDialog();
		else
			showPasswdEnterDialog();
	}
	
	private void enter_quick_setup(){
		boolean is_guided = spRecord.getBoolean("setting_guided", false);
		Class which;
		
		if (is_guided == true)
			which = SetupFinishActivity.class;
		else
			which = Setup1Activity.class;
		
		Intent intent = new Intent(this, which);
		startActivity(intent);
	}
	
	private void showPasswdEnterDialog(){
		AlertDialog.Builder builder = new Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.passwd_enter_dialog, null);
		
		dialog.setView(view);
		dialog.show();
		
		final EditText passwd = (EditText) view.findViewById(R.id.passwd_enter_et1);
		
		Button btn_ok = (Button) view.findViewById(R.id.passwd_passwd_enter_btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String passwdStr = passwd.getText().toString();
				String record = spRecord.getString("passwd", null);
				String md5 = MD5Utils.encode(passwd.getText().toString());
				
				if (md5.equals(record) == true){
					dialog.dismiss();
					enter_quick_setup();
				}
				else
					Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
			}
		});
		
		Button btn_cancel = (Button) view.findViewById(R.id.passwd_enter_btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	private void showPasswdSetDialog(){
		AlertDialog.Builder builder = new Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.passwd_set_dialog, null);
		
		dialog.setView(view);
		dialog.show();
		
		final EditText passwd = (EditText) view.findViewById(R.id.passwd_et1);
		final EditText passwd_re = (EditText) view.findViewById(R.id.passwd_et2);
		
		Button btn_ok = (Button) view.findViewById(R.id.passwd_passwd_set_btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String passwdStr = passwd.getText().toString();
				String passwdStrRe = passwd_re.getText().toString();
				if (!TextUtils.isEmpty(passwdStr) && !TextUtils.isEmpty(passwdStr)){
					if (passwdStr.equals(passwdStrRe)){
						Editor edit = spRecord.edit();
						String md5 = MD5Utils.encode(passwd.getText().toString());
						edit.putString("passwd", md5);
						edit.commit();
						
						dialog.dismiss();
						
						enter_quick_setup();
					}else
						Toast.makeText(HomeActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			}
		});
		
		Button btn_cancel = (Button) view.findViewById(R.id.passwd_set_btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		
		spRecord = getSharedPreferences("config", MODE_PRIVATE);	
		gridViewId = (GridView) findViewById(R.id.gv_home);

		gridViewId.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					
					enterActivity(position);
				}
		});
		
		gridViewId.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				if (convertView == null){
					View view = View.inflate(HomeActivity.this, R.layout.grid_view_items, null);
					ImageView image = (ImageView) view.findViewById(R.id.gv_item_iv);
					TextView tv = (TextView) view.findViewById(R.id.gv_item_tv);
					
					image.setImageResource(icon_id[position]);
					tv.setText(name[position]);
					return view;
				}
				else
					return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return name[position];
			}
			
			@Override
			public int getCount() {
				return icon_id.length;
			}
		});
	}
	
}
