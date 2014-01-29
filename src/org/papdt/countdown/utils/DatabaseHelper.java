package org.papdt.countdown.utils;

import java.io.IOException;

import android.content.Context;

public class DatabaseHelper {
	
	private static Context mContext;
	
	public static Database getDatabase(){
		return new Database(mContext);
	}
	
	public static Database getDatabase(Context context){
		mContext = context;
		return new Database(context);
	}

	public static void setDatabase(Database targetdb){
		try {
			targetdb.saveToData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
