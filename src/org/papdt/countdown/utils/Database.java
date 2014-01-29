package org.papdt.countdown.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
public class Database {
	
	private ArrayList<String> name, date;
	private ArrayList<Boolean> isLunar;
	private Context context;
	
	private final String TAG = "DatabaseHelper";
	
	public Database(Context context){
		this.context = context;
		readFromData();
	}
	
	public int getSize(){
		return name.size();
	}
	
	public String getName(int index){
		return name.get(index);
	}
	
	public String getDate(int index){
		return date.get(index);
	}
	
	public boolean getIsLunar(int index){
		return isLunar.get(index);
	}
	
	public void add(String name, String date, boolean isLunar){
		this.name.add(name);
		this.date.add(date);
		this.isLunar.add(isLunar);
	}
	
	public void add(int position, String name, String date, boolean isLunar){
		this.name.add(position, name);
		this.date.add(position, date);
		this.isLunar.add(position, isLunar);
	}
	
	public void setName(int position, String name){
		this.name.set(position, name);
	}
	
	public void setDate(int position, String date){
		this.date.set(position, date);
	}
	
	public void setIsLunar(int position, boolean isLunar){
		this.isLunar.set(position, isLunar);
	}
	
	public void delete(int position){
		this.name.remove(position);
		this.date.remove(position);
		this.isLunar.remove(position);
	}
	
	public void deleteAll(){
		name = new ArrayList<String>();
		date = new ArrayList<String>();
		isLunar = new ArrayList<Boolean>();
	}
	
	public void readFromData() {
		String jsonData;
		try {
			jsonData = readFile(context, "data.json");
		} catch (IOException e) {
			jsonData = "{\"data\":[]}";
			Log.i(TAG, "文件不存在,初始化新的文件.");
			e.printStackTrace();
		}
		Log.i(TAG, "读入json数据结果:");
		Log.i(TAG, jsonData);
		JSONObject jsonObj = null;
		JSONArray jsonArray = null;
		
		try {
			jsonObj = new JSONObject(jsonData);
		} catch (JSONException e) {
			Log.e(TAG, "无法解析json");
			e.printStackTrace();
			return ;
		}
		
		try {
			jsonArray = jsonObj.getJSONArray("data");
		} catch (JSONException e) {
			Log.e(TAG, "数据格式丢失, 缺少 data 数组");
			e.printStackTrace();
			return ;
		}
		
		deleteAll();
		
		for (int i = 0;i<jsonArray.length();i++){
			try {
				name.add(jsonArray.getJSONObject(i).getString("name"));
				date.add(jsonArray.getJSONObject(i).getString("date"));
				isLunar.add(jsonArray.getJSONObject(i).getBoolean("isLunar"));
			} catch (JSONException e) {
				Log.e(TAG, "第"+i+"组数据格式出现错误");
				e.printStackTrace();
			}
		}
	}
	
	public void saveToData() throws IOException{
		StringBuffer str = new StringBuffer();
		str.append("{\"data\":[");
		for (int i = 0;i<name.size();i++){
			str.append("{\"name\":\"");
			str.append(name.get(i));
			str.append("\",");
			
			str.append("\"date\":\"");
			str.append(date.get(i));
			str.append("\",");
			
			str.append("\"isLunar\":");
			str.append(isLunar.get(i));
			str.append("}");
			if (i<name.size() -1) str.append(",");
		}
		str.append("]}");
		saveFile(context, "data.json", str.toString());
	}
	
	@SuppressWarnings("deprecation")
	private void saveFile(Context context, String name, String text) throws IOException{
		FileOutputStream fos = context.openFileOutput(name, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		fos.write(text.getBytes());
		fos.close();
	}
	
	private String readFile(Context context, String name) throws IOException{
		File file = context.getFileStreamPath(name);
		InputStream is = new FileInputStream(file);
		
		byte b[] = new byte[(int) file.length()];
		
		is.read(b);
		is.close();
		
		String string = new String(b);
		
		return string;
	}

}
