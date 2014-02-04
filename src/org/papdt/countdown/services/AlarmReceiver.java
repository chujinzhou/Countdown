package org.papdt.countdown.services;

import org.papdt.countdown.R;
import org.papdt.countdown.UI.ActivityMain;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		int type = arg1.getIntExtra("type", 0);
		boolean needSendWeibo = arg1.getBooleanExtra("needSendWeibo", false);
		String weiboText = arg1.getStringExtra("weibo");
		switch (type){
		case 0:
			break;
		case 1:
			NotificationManager mNotifyManager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notify = new Notification(R.drawable.ic_app_white, null, System.currentTimeMillis());
			notify.defaults = Notification.DEFAULT_ALL;
			notify.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
			
			Intent intent = new Intent(arg0, ActivityMain.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(arg0, 0, intent, 0);
			
			notify.setLatestEventInfo(arg0, "Test", "type:"+type, pendingIntent);
			mNotifyManager.notify(R.string.app_name, notify);
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

}
