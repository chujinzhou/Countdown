package org.papdt.countdown.UI;

import java.sql.Date;

import org.papdt.countdown.R;
import org.papdt.countdown.utils.MyCalendar;

import com.fima.cardsui.views.CardUI;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class ActivityMain extends Activity {

	private static TextView tv_date, tv_date_chinese;
	private static CardUI cardui;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_date_chinese = (TextView) findViewById(R.id.tv_date_chinese);
		cardui = (CardUI) findViewById(R.id.cardlist);
		cardui.setSwipeable(true);
		
		tv_date.setText(MyCalendar.getDateString(getResources().getConfiguration().locale));
		tv_date_chinese.setText("农历" + MyCalendar.getLunarString().substring(MyCalendar.getLunarString().indexOf("年")+1));
		
		TextCard card0 = new TextCard("TEST", "Hello, world!");
		
		cardui.addCard(card0);
		
		CountdownCard card1 = new CountdownCard("元旦", Date.valueOf("2014-01-01"));
	
		cardui.addCard(card1);
		
		CountdownCard card2 = new CountdownCard("期末考试", Date.valueOf("2014-01-13"));
		
		cardui.addCard(card2);
		
		cardui.refresh();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		}
		return super.onOptionsItemSelected(item);
	}

}
