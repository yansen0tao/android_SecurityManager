package com.example.android_securitymanager.Activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_securitymanager.R;
import com.example.android_securitymanager.javabean.versionObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SplashActivity extends Activity {
	private TextView version;

	private static final int MSG_HTTP_CONNECT_SUCCESS = 0;
	private static final int MSG_HTTP_CONNECT_FAILURE = 1;
	private static final int MSG_FOUND_NEW_VERSION = 2;
	private static final int MSG_NO_FOUND_NEW_VERSION = 3;
	private static final int MSG_INNER_ERRORS = -1;
	private long MAX_CHECK_HOLD_DURATION = 2000;// millis
	private int local_code = 0;
	private versionObject vobj = new versionObject();
	private Handler handler;

	final private String List_Addr = "http://10.18.19.219:8080/version_list.json";// 必须加上http://,浏览器上可以不加，是因为浏览器帮我们加了

	private void setLocalVersion() {
		PackageManager pm = getPackageManager();
		PackageInfo pinfo = null;

		try {
			pinfo = pm.getPackageInfo(getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		local_code = pinfo.versionCode;

		version = (TextView) findViewById(R.id.tv_splash_version);
		version.setText(version.getText() + pinfo.versionName);
	}

	private void enterHome() {

	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void download() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			String packageUrl = vobj.getUrl();
			String packageName = File.separator
					+ packageUrl.substring(packageUrl.lastIndexOf("/") + 1);

			HttpUtils http = new HttpUtils();
			HttpHandler hander = http.download(vobj.getUrl(), Environment
					.getExternalStorageDirectory().toString() + packageName,
					true, true, new RequestCallBack<File>() {

						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							Toast.makeText(SplashActivity.this,
									"Download success", Toast.LENGTH_SHORT)
									.show();
							
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setDataAndType(Uri.fromFile(arg0.result), "application/vnd.android.package-archive");
							intent.addCategory(Intent.CATEGORY_DEFAULT);
							
							startActivityForResult(intent, 0);
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(SplashActivity.this,
									"Download failure", Toast.LENGTH_SHORT)
									.show();
							System.out.println(arg1);
						}
					});

		} else {
			Toast.makeText(this, "不存在SD卡", Toast.LENGTH_SHORT).show();
			enterHome();
		}
	}

	private void install_new_version() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("确定更新到最新版本吗？");
		dialog.setPositiveButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});

		dialog.setNegativeButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				download();
			}
		});

		dialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		setLocalVersion();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_HTTP_CONNECT_FAILURE:
					Toast.makeText(SplashActivity.this,
							"MSG_HTTP_CONNECT_FAILURE", Toast.LENGTH_SHORT)
							.show();
					break;
				case MSG_HTTP_CONNECT_SUCCESS:
					Toast.makeText(SplashActivity.this,
							"MSG_HTTP_CONNECT_SUCCESS", Toast.LENGTH_SHORT)
							.show();
					break;
				case MSG_FOUND_NEW_VERSION:
					install_new_version();
					Toast.makeText(SplashActivity.this,
							"MSG_FOUND_NEW_VERSION", Toast.LENGTH_SHORT).show();
					break;
				case MSG_NO_FOUND_NEW_VERSION:
					Toast.makeText(SplashActivity.this,
							"MSG_NO_FOUND_NEW_VERSION", Toast.LENGTH_SHORT)
							.show();
					break;
				case MSG_INNER_ERRORS:
					finish();
					break;
				default:
					System.out.println("default");
					break;
				}
			}
		};

		check_Update();
	}

	private void check_Update() {
		new Thread() {
			private HttpURLConnection conn = null;
			private URL url = null;
			private long start_time = System.currentTimeMillis();
			private int msg_what = MSG_HTTP_CONNECT_FAILURE;

			@Override
			public void run() {
				try {
					url = new URL(List_Addr);

					conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(15000);
					conn.setRequestMethod("GET");
					conn.connect();

					if (200 == conn.getResponseCode()) {
						InputStream is = conn.getInputStream();
						ByteArrayOutputStream os = new ByteArrayOutputStream();

						byte[] buff = new byte[1024];
						int len = -1;
						if ((len = is.read(buff)) != -1) {
							os.write(buff, 0, len);
						}

						JSONObject object = (JSONObject) new JSONTokener(
								os.toString()).nextValue();
						int version_code = object.getInt("version_code");
						String update_addr = object.getString("url");

						vobj.setUrl(update_addr);
						vobj.setVersion_code(version_code);

						if (local_code < version_code) {
							msg_what = MSG_FOUND_NEW_VERSION;
						} else {
							msg_what = MSG_NO_FOUND_NEW_VERSION;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					msg_what = MSG_INNER_ERRORS;
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
					msg_what = MSG_INNER_ERRORS;
				} catch (IOException e) {
					msg_what = MSG_HTTP_CONNECT_FAILURE;
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}

					long end_time = System.currentTimeMillis();
					long hold_times = end_time - start_time;

					if (hold_times < MAX_CHECK_HOLD_DURATION) {
						try {
							Thread.sleep(MAX_CHECK_HOLD_DURATION - hold_times);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					handler.sendEmptyMessage(msg_what);
				}
			}

		}.start();
	}
}
