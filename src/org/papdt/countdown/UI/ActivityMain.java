package org.papdt.countdown.UI;

import java.io.Serializable;
import java.util.Calendar;

import org.papdt.countdown.R;
import org.papdt.countdown.services.Alarm;
import org.papdt.countdown.services.MyAlarmService;
import org.papdt.countdown.utils.Database;
import org.papdt.countdown.utils.DatabaseHelper;
import org.papdt.countdown.utils.MyCalendar;
import org.papdt.countdown.utils.ScreenShot;

import com.fima.cardsui.views.CardUI;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class ActivityMain extends Activity {

	private static TextView tv_date, tv_date_chinese;
	private static CardUI cardui;
	private static String deleteName, deleteDate;

	private static Database db;
	
	private AlertDialog dialogAbout;
	
	public static Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what){
			case 0:
				CountdownCard cc = new CountdownCard(
						msg.getData().getString("name"),
						msg.getData().getString("date"),
						msg.getData().getBoolean("isLunar")
						);
				cardui.addCard(cc, true);
				break;
			case 1:
				cardui.clearCards();
				break;
			case 2:
				deleteName = msg.getData().getString("name");
				deleteDate = msg.getData().getString("date");
				Handler handler = new Handler();
				handler.post(deleteThread);
				break;
			case 3:
				Handler handler0 = new Handler();
				cardui.clearCards();
				handler0.post(refreshThread);
				break;
			}
		}
	};
	
	private static Runnable deleteThread = new Runnable(){

		@Override
		public void run() {
			for (int i=0;i<db.getSize();i++){
				if (db.getName(i) == deleteName & db.getDate(i) == deleteDate){
					Log.i("mHandler", "deleted no."+i+" data.");
					db.delete(i);
					break;
				}
			}
		}
		
	};
	
	private static Runnable refreshThread = new Runnable(){
		@Override
		public void run(){
			Message msg;
			Bundle bundle;

			db = DatabaseHelper.getDatabase();
			
			for (int i=0;i<db.getSize();i++){
				msg = new Message();
				bundle = new Bundle();
				
				bundle.putString("name", db.getName(i));
				bundle.putString("date", db.getDate(i));
				bundle.putBoolean("isLunar", db.getIsLunar(i));
				
				msg.setData(bundle);
				msg.what = 0;
				
				mHandler.handleMessage(msg);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		/*db.add("元旦", "01-01", false);
		db.add("国庆节", "10-01", false);
		db.add("我的生日", "10-04", false);
		db.add("北京奥运会", "2008-08-08", false);
		db.add("话费月结", "24", false);*/

		db = DatabaseHelper.getDatabase(getApplicationContext());
		
		getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_app_white));
		
		initDate();
		initCard();
		
		/*int i, j;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, (i = cal.get(Calendar.SECOND)) != 59 ? 0 : i + 1);
		cal.set(Calendar.MINUTE, (j = cal.get(Calendar.MINUTE)) != 59 ? 0 : j + 1);
		
		Alarm alarm = new Alarm(cal, 1);
		
		Intent intent = new Intent(this, MyAlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("alarm", (Parcelable) alarm);
        startService(intent);*/
		
		mHandler.post(refreshThread);
	}
	
	@Override
	public void onDestroy(){
		DatabaseHelper.setDatabase(db);
		super.onDestroy();
	}
	
	private void initDate(){
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_date_chinese = (TextView) findViewById(R.id.tv_date_chinese);
		
		tv_date.setText(MyCalendar.getDateString(getResources().getConfiguration().locale));
		tv_date_chinese.setText("农历" + MyCalendar.getLunarString().substring(MyCalendar.getLunarString().indexOf("年")+1));
	}
	
	private void initCard(){
		cardui = (CardUI) findViewById(R.id.cardlist);
		cardui.setSwipeable(true);
		
		SharedPreferences preferences = getApplicationContext().getSharedPreferences("default", Context.MODE_PRIVATE);
		//if (!preferences.getBoolean("hasFirstStarted", false)) {
			String[] tipArray = getResources().getStringArray(R.array.tips);
			TextCard tip0 = new TextCard(tipArray[0]);
			TextCard tip1 = new TextCard(tipArray[1]);
			TextCard tip2 = new TextCard(tipArray[2]);
			
			cardui.addCard(tip0, true);
			cardui.addCard(tip1, true);
			cardui.addCard(tip2, true);
			
			preferences.edit().putBoolean("hasFirstStarted", true).commit();
		//}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_add:
			DatabaseHelper.setDatabase(db);
			
			Intent intent = new Intent(this, ActivityAdd.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			startActivity(intent);
			break;
		case R.id.item_share:
			ScreenShot.shootToFile(ActivityMain.this, Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/screenshot.png");
			
			Intent intent0 = new Intent(Intent.ACTION_SEND);
			intent0.setType("image/*");
			intent0.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharetext));
			intent0.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"
			+ Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/screenshot.png")
			);
			intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			try {
				startActivity(Intent.createChooser(intent0, getString(R.string.menu_share) + "...."));
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
		case R.id.item_about:
			DialogInterface.OnClickListener listener0 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                        switch (whichButton) {
                        case DialogInterface.BUTTON_POSITIVE:
                        	dialogAbout.cancel();
                        	break;
                        }
                }
            };
			if (dialogAbout == null){
				dialogAbout = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.menu_about))
                .setMessage(getString(R.string.abouttext))
                .setPositiveButton(android.R.string.ok, listener0)
                .show();
			} else dialogAbout.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
