package org.papdt.countdown.UI;

import java.text.DateFormat;
import java.sql.Date;

import org.papdt.countdown.R;
import org.papdt.countdown.utils.MyCalendar;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class CountdownCard extends Card {
		
	private String date;
	private boolean isLunar;
	
	public CountdownCard(String title, String date, boolean isLunar){
		super(title);
		this.date = date;
		this.isLunar = isLunar;
		this.setOnCardSwipedListener(new OnCardSwiped(){

			@Override
			public void onCardSwiped(Card card, View layout) {
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("name", ((TextView) layout.findViewById(R.id.tv_festival)).getText().toString());
				data.putString("date", ((TextView) layout.findViewById(R.id.tv_date)).getTag().toString());
				msg.setData(data);
				msg.what = 2;
				
				ActivityMain.mHandler.handleMessage(msg);
			}
			
		});
	}
	
	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_countdown, null);

		Date d = Date.valueOf(MyCalendar.format(date, isLunar));
		
		TextView tv_title = (TextView) view.findViewById(R.id.tv_festival);
		tv_title.setText(title);
		
		TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
		DateFormat format = DateFormat.getDateInstance();
		tv_date.setText(format.format(d));
		tv_date.setTag(date);
		
		TextView tv_countdown = (TextView) view.findViewById(R.id.tv_countdown);
		tv_countdown.setText(String.valueOf(Math.abs(MyCalendar.countdown(d, MyCalendar.Field.DAY)))+"天");
		
		TextView tv01 = (TextView) view.findViewById(R.id.TextView01);
		if (MyCalendar.countdown(d, MyCalendar.Field.DAY) < 0){
			tv01.setText("已经过去");
		}
		if (MyCalendar.countdown(d, MyCalendar.Field.DAY) == 0){
			tv_countdown.setText("");
			tv01.setText("就在今天~");
		}
		
		return view;
	}
	
}
