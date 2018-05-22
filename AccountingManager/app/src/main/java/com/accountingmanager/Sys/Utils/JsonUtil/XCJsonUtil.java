package com.accountingmanager.Sys.Utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XCJsonUtil {
	public static Object json2Object(Object jsonObject) {
		if ((jsonObject instanceof JSONObject)) {
			return json2Map((JSONObject) jsonObject);
		}
		if ((jsonObject instanceof JSONArray)) {
			return json2List((JSONArray) jsonObject);
		}
		return jsonObject;
	}

	public static Object object2Json(Object object) {
		if ((object instanceof List)) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < ((List) object).size(); i++) {
				jsonArray.put(object2Json(((List) object).get(i)));
			}
			return jsonArray;
		}
		if ((object instanceof Map)) {
			JSONObject jsonObject = new JSONObject();
			for (Object key : ((Map) object).keySet()) {
				try {
					jsonObject.put((String) key,
							object2Json(((Map) object).get(key)));
				} catch (JSONException e) {
					XCLog.printStackTrace(e);
					return null;
				}
			}
			return jsonObject;
		}
		if (object == null) {
			return "";
		}
		return object;
	}

	public static Map<String, Object> json2Map(JSONObject jsonObject) {
		Iterator<String> iterator = jsonObject.keys();

		Object object = null;
		Map<String, Object> valueMap = new HashMap();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			try {
				object = jsonObject.get(key);
			} catch (JSONException e) {
				XCLog.printStackTrace(e);
				return null;
			}
			if ((object instanceof JSONObject)) {
				Map<String, Object> map = json2Map((JSONObject) object);
				valueMap.put(key, map);
			} else if ((object instanceof JSONArray)) {
				List<Object> list = json2List((JSONArray) object);
				valueMap.put(key, list);
			} else {
				valueMap.put(key, object);
			}
		}
		return valueMap;
	}

	private static List<Object> json2List(JSONArray jsonArray) {
		Object object = null;
		List<Object> list = new ArrayList();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				object = jsonArray.get(i);
			} catch (JSONException e) {
				XCLog.printStackTrace(e);
				return null;
			}
			if (object == JSONObject.NULL) {
				list.add(null);
			} else if ((object instanceof JSONObject)) {
				Map<String, Object> map = json2Map((JSONObject) object);
				list.add(map);
			} else if ((object instanceof JSONArray)) {
				List<Object> subList = json2List((JSONArray) object);
				list.add(subList);
			} else {
				list.add(object);
			}
		}
		return list;
	}

	public static Object JValue(Object data, String keyPath) {
		try {
			String[] keys = keyPath.split("\\.");
			Object obj = data;
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				if (key.startsWith("[")) {
					int index = XCToolkit.parseInt(key.replace("[", ""));
					if ((obj instanceof ArrayList)) {
						ArrayList<?> list = (ArrayList) obj;
						if (list.size() <= index) {
							return null;
						}
						if (i == keys.length - 1) {
							return list.get(index);
						}
						obj = list.get(index);
					} else if ((obj instanceof JSONArray)) {
						JSONArray list = (JSONArray) obj;
						if (list.length() <= index) {
							return null;
						}
						if (i == keys.length - 1) {
							try {
								return list.get(index);
							} catch (JSONException e) {
								XCLog.printStackTrace(e);
								return null;
							}
						}
						try {
							obj = list.get(index);
						} catch (JSONException e) {
							XCLog.printStackTrace(e);
							return null;
						}
					} else {
						return null;
					}
				} else if ((obj instanceof Map)) {
					Map<String, ?> map = (Map) obj;
					if (i == keys.length - 1) {
						return map.get(key);
					}
					obj = map.get(key);
				} else if ((obj instanceof JSONObject)) {
					JSONObject jObject = (JSONObject) obj;
					if (i == keys.length - 1) {
						return jObject.opt(key);
					}
					obj = jObject.opt(key);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			XCLog.printStackTrace(e);
		}
		return null;
	}
}
