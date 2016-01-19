package com.example.android_securitymanager.Activities;

import java.util.HashMap;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_securitymanager.R;

public class HomeActivity extends Activity {
	private GridView gridViewId;
	
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
			
			break;
		}
		
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		
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
