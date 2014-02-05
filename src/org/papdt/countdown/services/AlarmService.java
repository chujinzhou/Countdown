package org.papdt.countdown.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service{

	private long time;
	private int type;
	private boolean needWeibo;
	private String weiboText;
	
	private final String TAG = "AlarmService";
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate(){
		super.onCreate();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startid){
		if (intent == null) return -1;
		
		time = Long.parseLong(intent.getStringExtra("time"));
		type = intent.getIntExtra("type", 0);
		needWeibo = intent.getBooleanExtra("needSendWeibo", false);
		
		if (intent.hasExtra("weibotext")){
			weiboText = intent.getStringExtra("weibotext");
		}
		
		Log.i(TAG, "Service接收到数据!");
		Log.i(TAG, "time:" + time + ", type:" + type + ", needSendWeibo:" + needWeibo + ", weibotext:" + weiboText);
		setAlarm();
		
		return super.onStartCommand(intent, flags, startid);
	}
	
	private void setAlarm(){
		Intent intent = new Intent(AlarmService.this, AlarmReceiver.class);
		intent.putExtra("type", type);
		intent.putExtra("needSendWeibo", needWeibo);
		if (weiboText != null){
			intent.putExtra("weibo", weiboText);
		}
		
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, time, pi);
	}
	
}
