package com.example.android_securitymanager.brocastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.telephony.TelephonyManager;

//这里需要权限RECEIVE_BOOT_COMPLETED
public class bootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String simSerialNum = tm.getSimSerialNumber();
		
		SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String string = sharedPreferences.getString("simSerialNum", "");
		
		//alarm
		if (!string.equals(simSerialNum)){
			MediaPlayer media = new MediaPlayer();
		}
	}
}
