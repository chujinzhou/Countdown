package org.papdt.countdown.services;

import java.util.Calendar;

public class Alarm {
	
	private Calendar cal;
	private int type_id;

	private boolean needSendWeibo;
	
	public Alarm(Calendar cal, int type_id){
		this.setDate(cal);
		this.setType(type_id);
		needSendWeibo = false;
	}
	
	public Alarm(Calendar cal, int type_id, boolean needSendWeibo){
		this.setDate(cal);
		this.setType(type_id);
		this.needSendWeibo = needSendWeibo;
	}
	
	public Calendar getDate() {
		return cal;
	}

	public void setDate(Calendar date) {
		this.cal = date;
	}

	public int getType() {
		return type_id;
	}

	public void setType(int type_id) {
		this.type_id = type_id;
	}

	public void setNeedSendWeibo(boolean b){
		needSendWeibo = b;
	}
	
	public boolean needSendWeibo(){
		return needSendWeibo;
	}
	
	public final class TYPE {
		public final int none = 0, onlyNotify = 1, onlyAlarm = 2;
	}
}
