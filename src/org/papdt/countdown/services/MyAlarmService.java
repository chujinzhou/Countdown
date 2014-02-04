package org.papdt.countdown.services;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyAlarmService extends Service{

	private Alarm mAlarm = null;
	
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
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startid){
		if (intent == null) return;
		
		if (intent.hasExtra("Alarm")){
			mAlarm = (Alarm) intent.getSerializableExtra("alarm");
		}
		setAlarm();
		
		super.onStart(intent, startid);
	}
	
	private void setAlarm(){
		if (mAlarm == null) return;
		
		Calendar cal = mAlarm.getDate();
		
		Intent intent = new Intent(MyAlarmService.this, AlarmReceiver.class);
		intent.putExtra("type", mAlarm.getType());
		
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
	}
	
}
