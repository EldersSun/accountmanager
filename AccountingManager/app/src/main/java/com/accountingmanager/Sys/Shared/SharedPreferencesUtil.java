package com.accountingmanager.Sys.Shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.accountingmanager.Sys.Utils.StringUtils;


/**
 * SharedPreferences工具类
 */
public class SharedPreferencesUtil {
	private static Editor editor;
	public static final String PROPERTY_NAME = "property_name";

	public static final String KEY_SIZE = "key_size";

	public static final String FileName = "thisAppShared";

	public static void putBoolean(Context context, String name, String key,
								  boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(Context context, String name, String key) {
		return getBoolean(context, name, key, false);
	}

	public static boolean getBoolean(Context context, String name, String key,
									 boolean defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	public static void putBoolean(Context context, String key, boolean value) {
		putBoolean(context, FileName, key, value);
	}

	public static boolean getBoolean(Context context, String key) {
		return getBoolean(context, FileName, key);
	}

	public static void putInt(Context context, String name, String key,
							  int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getInt(Context context, String key) {
		return getInt(context, FileName, key, -1);
	}

	public static int getInt(Context context, String name, String key) {
		return getInt(context, name, key, -1);
	}

	public static int getInt(Context context, String name, String key,
							 int defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static void putInt(Context context, String key, int value) {
		putInt(context, FileName, key, value);
	}

	public static int getInt(Context context, String key, int defaultValue) {
		return getInt(context, FileName, key, defaultValue);
	}

	public static float getFloat(Context context, String name, String key,
								 float defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		return sharedPreferences.getFloat(key, defaultValue);
	}

	public static void putFloat(Context context, String name, String key,
								float value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static void putFloat(Context context, String key, float value) {
		putFloat(context, FileName, key, value);
	}

	public static float getFloat(Context context, String key, float defaultValue) {
		return getFloat(context, FileName, key, defaultValue);
	}

	public static void putString(Context context, String name, String key,
								 String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getString(Context context, String name, String key,
								   String defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defValue);
	}

	public static void putString(Context context, String key, String value) {
		putString(context, FileName, key, value);
	}

	public static String getString(Context context, String key, String defValue) {
		return getString(context, FileName, key, defValue);
	}

	public static void putLong(Context context, String name, String key,
							   long value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static long getLong(Context context, String name, String key,
							   long defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(key, defValue);
	}

	public static void putLong(Context context, String key, long value) {
		putLong(context, FileName, key, value);
	}

	public static long getLong(Context context, String key, long defValue) {
		return getLong(context, FileName, key, defValue);
	}

	public static void putBitmap(Context context, String key, Bitmap bitmap) {
		// 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream);
		// 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String imageString = new String(Base64.encodeToString(byteArray,
				Base64.DEFAULT));
		// 第三步:将String保存至SharedPreferences
		putString(context, key, imageString);
	}

	public static Bitmap getBitmap(Context context, String key, Bitmap defValue) {
		// 第一步:取出字符串形式的Bitmap
		String imageString = getString(context, key, "");
		if (StringUtils.isBlank(imageString)) {
			return defValue;
		}
		// 第二步:利用Base64将字符串转换为ByteArrayInputStream
		byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				byteArray);
		// 第三步:利用ByteArrayInputStream生成Bitmap
		Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
		return bitmap;
	}

	public static void putStringList(Context context, String key,
									 List<String> list) {
		putInt(context, key + "size", list.size());
		for (int i = 0; i < list.size(); i++) {
			removeKey(context, key + i);
			putString(context, key + i, list.get(i));
		}
	}

	public static List<String> getStringList(Context context, String key) {
		List<String> list = new ArrayList<String>();
		int size = getInt(context, key + "size", 0);
		for (int i = 0; i < size; i++) {
			list.add(getString(context, key + i, ""));
		}
		return list;
	}

	public static void putIntList(Context context, String key,
								  List<Integer> list) {
		putInt(context, key + "size", list.size());
		for (int i = 0; i < list.size(); i++) {
			removeKey(context, key + i);
			putInt(context, key + i, list.get(i));
		}
	}

	public static List<Integer> getIntList(Context context, String key) {
		List<Integer> list = new ArrayList<Integer>();
		int size = getInt(context, key + "size", 0);
		for (int i = 0; i < size; i++) {
			list.add(getInt(context, key + i, -1));
		}
		return list;
	}

	public static void putBitmapList(Context context, String key,
									 List<Bitmap> list) {
		putInt(context, key + "size", list.size());
		for (int i = 0; i < list.size(); i++) {
			removeKey(context, key + i);
			putBitmap(context, key + i, list.get(i));
		}
	}

	public static List<Bitmap> getBitmapList(Context context, String key) {
		List<Bitmap> list = new ArrayList<Bitmap>();
		int size = getInt(context, key + "size", 0);
		for (int i = 0; i < size; i++) {
			list.add(getBitmap(context, key + i, null));
		}
		return list;
	}

	public static void removeList(Context context, String key) {
		int size = getInt(context, key + "size", 0);
		for (int i = 0; i < size; i++) {
			removeKey(context, key + i);
		}
	}

	public static void removeKey(Context context, String name, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public static void removeKey(Context context, String key) {
		removeKey(context, FileName, key);
	}

}
