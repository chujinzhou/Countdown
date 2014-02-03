package org.papdt.countdown.UI;

import org.papdt.countdown.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class TextCard extends Card {
		
	private String desc;
	private boolean showDesc;
	
	public TextCard(String title){
		super(title);
	}
	
	public TextCard(String title, String desc){
		super(title);
		this.desc = desc;
		showDesc = true;
	}
	
	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_simple, null);
			
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		
		if (showDesc){
			TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
			tv_desc.setText(desc);
		} else {
			view.findViewById(R.id.line).setVisibility(View.INVISIBLE);
			view.findViewById(R.id.desc).setVisibility(View.INVISIBLE);
		}
		
		return view;
	}
	
}
