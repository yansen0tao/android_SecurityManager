package com.example.android_securitymanager.Activities;

import com.example.android_securitymanager.R;
import com.example.android_securitymanager.Views.View_title_desc_style;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class Setup2Activity extends SettingBaseActivity {
	View_title_desc_style view_bind_sim = null;
	private String simSerialNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setup2);

		view_bind_sim = (View_title_desc_style) findViewById(R.id.view_setup2_bind_sim);
		simSerialNum = spRecord.getString("simSerialNum", null);
		view_bind_sim.setChecked(!TextUtils.isEmpty(simSerialNum));
		view_bind_sim.setOnClickListener(new OnClickListener() {
			private String getSimSerialNum() {
				String ret = null;
				// �����ȡ����ϵͳ���񣬼�֧��ϵͳ��һЩ���񣬱���绰���ŵȣ������ǲ���Ҫ�رյ�
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				ret = tm.getSimSerialNumber();

				return ret;
			}

			// ����ѵ�ǰ�ֻ���sim�����кű�����sharedPreference�У��´ο����ֻ�ʱ��
			// ���ǵĳ������ѡ�����һ��������ɵĹ㲥��������㲥�У�����ͬ���ķ�����ȡ
			// һ��SIM�������кţ�ʹ������ﱣ���ֵ�Աȣ������һ�µĻ���˵��SIM�������
			// ������
			private void saveSimSerial() {
				simSerialNum = getSimSerialNum();

				SharedPreferences.Editor editor = spRecord.edit();
				editor.putString("simSerialNum", simSerialNum);
				editor.commit();
			}

			@Override
			public void onClick(View v) {

				view_bind_sim.setChecked(!view_bind_sim.is_checked());
				saveSimSerial();
			}
		});
	}

	public void showNextPage() {
		Intent intent = new Intent(this, Setup3Activity.class);

		startActivity(intent);

		finish();

		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	public void showPrePage() {
		Intent intent = new Intent(this, Setup1Activity.class);

		startActivity(intent);

		finish();

		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}
}
