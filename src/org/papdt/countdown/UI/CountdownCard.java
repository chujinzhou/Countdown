package org.papdt.countdown.UI;

import java.text.DateFormat;
import java.util.Date;

import org.papdt.countdown.R;
import org.papdt.countdown.utils.MyCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class CountdownCard extends Card {
		
	private Date date;
	
	public CountdownCard(String title, Date date){
		super(title);
		this.date = date;
	}
	
	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_countdown, null);
			
		TextView tv_title = (TextView) view.findViewById(R.id.tv_festival);
		tv_title.setText(title);
		
		TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
		DateFormat format = DateFormat.getDateInstance();
		tv_date.setText(format.format(date));
		
		TextView tv_countdown = (TextView) view.findViewById(R.id.tv_countdown);
		tv_countdown.setText(String.valueOf(MyCalendar.countdown(date, MyCalendar.Field.DAY))+"天");
		
		return view;
	}
	
}
