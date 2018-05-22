/**
 * 
 */
package com.accountingmanager.Sys.Utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 */
public final class XCUtils {
	public static <T> T parseObject(Object data, String keyPath, Class<T> modelClass) {
		Object ad = XCJsonUtil.JValue(data, keyPath);
		T modelData = null;
		try {
			modelData = (T) XCJsonHelper.parseObject((JSONObject) XCJsonUtil.object2Json(ad), modelClass);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return modelData;
	}
	
	public static <T> ArrayList<T> parseList(Object data, String keyPath, Class<T> modelClass) {
		Object ad = XCJsonUtil.JValue(data, keyPath);
		ArrayList<T> modelData = null;
		try {
			modelData = XCJsonHelper.parseArray((JSONArray) XCJsonUtil.object2Json(ad), modelClass);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return modelData;
	}
}
