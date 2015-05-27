package com.location.util;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.widget.Toast;

public class ActivityUtil {
	/**
	 * 日期格式化器
	 */
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 显示Toast弹出消息框
	 * @param context
	 * @param text
	 */
	public static void showMessageByToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
