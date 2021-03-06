package org.papdt.countdown.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

public class ScreenShot {
	@SuppressWarnings("deprecation")
	private static Bitmap takeScreenShot(Activity activity){
		View view = activity.getWindow().getDecorView();
    	view.setDrawingCacheEnabled(true);
    	view.buildDrawingCache();
    	Bitmap b1 = view.getDrawingCache();
    	
    	Rect frame = new Rect();  
    	activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
    	int statusBarHeight = frame.top;  
    	System.out.println(statusBarHeight);
    	
    	int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
    	Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
    	view.destroyDrawingCache();
    	return b;
	}
	
	private static void savePic(Bitmap b,String strFileName){
    	FileOutputStream fos = null;
    	try {
			fos = new FileOutputStream(strFileName);
			if (null != fos)
			{
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void shootToFile(Activity a, String path){
		ScreenShot.savePic(ScreenShot.takeScreenShot(a), path);
	}
	
	public static Bitmap shootToBitmap(Activity a){
		return takeScreenShot(a);
	}
}