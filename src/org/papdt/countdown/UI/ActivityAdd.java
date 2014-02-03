package org.papdt.countdown.UI;

import org.papdt.countdown.R;
import org.papdt.countdown.utils.Database;
import org.papdt.countdown.utils.DatabaseHelper;
import org.papdt.countdown.utils.MyCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityAdd extends Activity{
	
	private EditText et_title;
	private Spinner mSpinner;
	private Button btn_date;
	
	private static Database db;
	private AlertDialog dialog_onlyday, dialog_everymonth, dialog_everyyear;
	
	private DatePicker cv;
	private Spinner s0, s1, s2;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_add);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("新增倒计时");
		
		db = DatabaseHelper.getDatabase(getApplicationContext());
		
		et_title = (EditText) findViewById(R.id.et_title);
		mSpinner = (Spinner) findViewById(R.id.spinner1);
		btn_date = (Button) findViewById(R.id.btn_date);
		
		String[] spinnerText = getResources().getStringArray(R.array.date_type);
		ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerText);
		mSpinner.setAdapter(mArrayAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2){
				case 0:
					btn_date.setText(MyCalendar.format("01-01", false));
					break;
				case 1:
					btn_date.setText("01-01");
					break;
				case 2:
					btn_date.setText("01");
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btn_date.setText(MyCalendar.format("01-01", false));
		btn_date.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				showSetDialog((int) mSpinner.getSelectedItemId());
			}
			
		});
	}
	
	@SuppressLint("CutPasteId")
	private void showSetDialog(int type_id){
		switch (type_id){
		case 0:
			DialogInterface.OnClickListener listener0 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                        switch (whichButton) {
                        case DialogInterface.BUTTON_POSITIVE:
                        	btn_date.setText(cv.getYear()+"-"+(cv.getMonth()+1)+"-"+cv.getDayOfMonth());
                        	break;
                        case DialogInterface.BUTTON_NEGATIVE:
                        	dialog_onlyday.cancel();
                        	break;
                        }
                }
            };
			if (dialog_onlyday == null){
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.popup_onlyday, null);
				cv = (DatePicker) layout.findViewById(R.id.datePicker1);
				dialog_onlyday = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.set_date))
                .setView(layout)
                .setPositiveButton(android.R.string.ok, listener0)
                .setNegativeButton(android.R.string.cancel, listener0)
                .show();
			} else dialog_onlyday.show();
			break;
		case 1:
			DialogInterface.OnClickListener listener1 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                        switch (whichButton) {
                        case DialogInterface.BUTTON_POSITIVE:
                        	String month = String.valueOf(s0.getSelectedItemId() + 1);
                        	String day = String.valueOf(s1.getSelectedItemId() + 1);
                        	btn_date.setText(
                        			(month.length() == 1 ? "0" + month : month)
                        			+ "-"
                        			+ (day.length() == 1 ? "0" + day : day)
                        			);
                        	break;
                        case DialogInterface.BUTTON_NEGATIVE:
                        	dialog_everyyear.cancel();
                        	break;
                        }
                }
            };
			if (dialog_everyyear == null){
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.popup_everyyear, null);
				
				s0 = (Spinner) layout.findViewById(R.id.spinner1);
				s1 = (Spinner) layout.findViewById(R.id.spinner2);
				
				String[] monthText = getResources().getStringArray(R.array.month_name);
				ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, monthText);
				s0.setAdapter(aa);
				s0.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						ArrayAdapter<String> aa = null;
						if (arg2 == 0 | arg2 == 2 | arg2 == 4 | arg2 == 6 | arg2 == 7 | arg2 == 9 | arg2 == 11){
							String[] dayNum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"
									,"19","20","21","22","23","24","25","26","27","28","29","30","31"};
							aa = new ArrayAdapter<String>(ActivityAdd.this, android.R.layout.simple_spinner_dropdown_item, dayNum);
						}
						if (arg2 == 3 | arg2 == 5 | arg2 == 8 | arg2 == 10){
							String[] dayNum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"
									,"19","20","21","22","23","24","25","26","27","28","29","30"};
							aa = new ArrayAdapter<String>(ActivityAdd.this, android.R.layout.simple_spinner_dropdown_item, dayNum);
						}
						if (arg2 == 1){
							String[] dayNum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"
									,"19","20","21","22","23","24","25","26","27","28"};
							aa = new ArrayAdapter<String>(ActivityAdd.this, android.R.layout.simple_spinner_dropdown_item, dayNum);
						}
						s1.setAdapter(aa);
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
					
				});
				String[] dayNum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"
						,"19","20","21","22","23","24","25","26","27","28","29","30","31"};
				ArrayAdapter<String> aa0 = new ArrayAdapter<String>(ActivityAdd.this, android.R.layout.simple_spinner_dropdown_item, dayNum);
				s1.setAdapter(aa0);
				
				dialog_everyyear = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.set_date))
                .setView(layout)
                .setPositiveButton(android.R.string.ok, listener1)
                .setNegativeButton(android.R.string.cancel, listener1)
                .show();
			} else dialog_everyyear.show();
			break;
		case 2:
			DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                        switch (whichButton) {
                        case DialogInterface.BUTTON_POSITIVE:
                        	String day = String.valueOf(s2.getSelectedItemId() + 1);
                        	btn_date.setText(day.length() == 1 ? "0" + day : day);
                        	break;
                        case DialogInterface.BUTTON_NEGATIVE:
                        	dialog_everymonth.cancel();
                        	break;
                        }
                }
            };
			if (dialog_everymonth == null){
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.popup_everymonth, null);
				s2 = (Spinner) layout.findViewById(R.id.spinner1);

				String[] dayNum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"
					,"19","20","21","22","23","24","25","26","27","28","29","30","31"};
				ArrayAdapter<String> aa0 = new ArrayAdapter<String>(ActivityAdd.this, android.R.layout.simple_spinner_dropdown_item, dayNum);
				s2.setAdapter(aa0);

				dialog_everymonth = new AlertDialog.Builder(this)
				.setTitle(getString(R.string.set_date))
				.setView(layout)
				.setPositiveButton(android.R.string.ok, listener2)
				.setNegativeButton(android.R.string.cancel, listener2)
				.show();
			} else dialog_everymonth.show();
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.item_finish:
			if (et_title.getText().toString().trim().length() == 0){
				Toast.makeText(getApplicationContext(), getString(R.string.hint_entertitle), Toast.LENGTH_SHORT).show();
				break;
			}
			
			db.add(et_title.getText().toString(), btn_date.getText().toString(), false);
			DatabaseHelper.setDatabase(db);
			ActivityMain.mHandler.sendEmptyMessage(3);
			Toast.makeText(getApplicationContext(), getString(R.string.toast_add), Toast.LENGTH_SHORT).show();
			
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
