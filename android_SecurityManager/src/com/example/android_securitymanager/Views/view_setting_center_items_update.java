package com.example.android_securitymanager.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android_securitymanager.R;

public class view_setting_center_items_update extends RelativeLayout {

	private TextView title;
	private TextView desc;
	private CheckBox checkStatus;
	private String descOn;
	private String descOff;
	private String stringTitle;

	public view_setting_center_items_update(Context context) {
		super(context);
		
		init_view(context);
	}
	
	public view_setting_center_items_update(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		init_view(context);
	}

	public view_setting_center_items_update(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		stringTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.android_securitymanager", "title");
		descOn = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.android_securitymanager", "desc_on");
		descOff = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.android_securitymanager", "desc_off");

		init_view(context);
	}
	
	private void init_view(Context context){
		View view = View.inflate(context, R.layout.activity_setting_center_items, this);
	
		title = (TextView) findViewById(R.id.setting_items_tv_title);
		desc = (TextView) findViewById(R.id.setting_items_tv_desc);
	  	checkStatus = (CheckBox) findViewById(R.id.setting_items_tv_checkBox);
	  	
	  	set_title(stringTitle);
	}
	
	public void set_title(String titleString){
		title.setText(titleString);
	}
	
	public boolean is_checked(){
		return checkStatus.isChecked();
	}
	
	public void setChecked(boolean checked){
		checkStatus.setChecked(checked);
		
		if (checked == true){
			desc.setTextColor(Color.RED);
				desc.setText(descOn);
		}
		else{
			desc.setTextColor(Color.BLACK);
				desc.setText(descOff);
		}
	}
}
