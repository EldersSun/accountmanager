package com.accountingmanager.Sys.Utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class XCJsonHelper {
	private static String TAG = "XCJsonHelper";

	private static boolean isBaseDataType(Class clazz) throws Exception {
		return

		(clazz.equals(String.class)) || (clazz.equals(Integer.class))
				|| (clazz.equals(Byte.class)) || (clazz.equals(Long.class))
				|| (clazz.equals(Double.class)) || (clazz.equals(Float.class))
				|| (clazz.equals(Character.class))
				|| (clazz.equals(Short.class))
				|| (clazz.equals(BigDecimal.class))
				|| (clazz.equals(BigInteger.class))
				|| (clazz.equals(Boolean.class))
				|| (clazz.equals(java.util.Date.class))
				|| (clazz.equals(java.sql.Date.class))
				|| (clazz.equals(Timestamp.class)) || (clazz.isPrimitive());
	}

	public static String toJSONString(Object obj) {
		if (obj == null) {
			return null;
		}
		return XCJsonUtil.object2Json(beanObject(obj)).toString();
	}

	private static Object beanObject(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			if (isBaseDataType(obj.getClass())) {
				return obj;
			}
			if ((obj instanceof List)) {
				ArrayList<Object> list = new ArrayList();
				for (Object o : (List) obj) {
					list.add(beanObject(o));
				}
				return list;
			}
			return beanToMap(obj);
		} catch (Exception e) {
			XCLog.printStackTrace(e);
		}
		return null;
	}

	private static void setValueMap(Object obj, Map<String, Object> valueMap,
			Method method, String fieldName) {
		try {
			Object fieldVal = method.invoke(obj, new Object[0]);
			Object result = null;
			if ((fieldVal != null)
					&& ("Date".equals(fieldVal.getClass().getSimpleName()))) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
				result = sdf.format((java.util.Date) fieldVal);
			} else if (fieldVal != null) {
				if ((fieldVal instanceof List)) {
					ArrayList<Object> list = new ArrayList();
					for (Object o : (List) fieldVal) {
						list.add(beanObject(o));
					}
					result = list.size() > 0 ? list : null;
				} else {
					Object o;
					if ((fieldVal instanceof Map)) {
						Map<Object, Object> map = new HashMap();
						Map<?, ?> fMap = (Map) fieldVal;
						for (Iterator localIterator2 = fMap.keySet().iterator(); localIterator2
								.hasNext();) {
							o = localIterator2.next();
							map.put(o, beanObject(fMap.get(o)));
						}
						result = map.keySet().size() > 0 ? map : null;
					} else if ((fieldVal instanceof Set)) {
						ArrayList<Object> list = new ArrayList();
						for (o = ((Set) fieldVal).iterator(); ((Iterator) o)
								.hasNext();) {
							Object object = ((Iterator) o).next();
							list.add(beanObject(object));
						}
						result = list.size() > 0 ? list : null;
					} else if ((!isBaseDataType(fieldVal.getClass()))
							|| (fieldVal.getClass().getAnnotation(
									XCJsonModel.class) != null)) {
						result = beanObject(fieldVal);
					} else if ((fieldVal instanceof Boolean)) {
						valueMap.put(fieldName,
								Integer.valueOf(((Boolean) fieldVal)
										.booleanValue() ? 1 : 0));
					} else {
						String v = String.valueOf(fieldVal);
						if ((v != null)
								&& ((v.isEmpty()) || (v.equals("null")))) {
							v = null;
						}
						if (v.contains("\\/")) {
							v = v.replaceAll("\\/", "/");
							XCLog.d("====json====", v);
						}
						result = v;
					}
				}
			}
			if (result != null) {
				valueMap.put(fieldName, result);
			}
		} catch (Exception e) {
			XCLog.printStackTrace(e);
		}
	}

	public static Map<String, Object> beanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Class<?> cls = obj.getClass();
		Map<String, Object> valueMap = new HashMap();

		Method[] methods = cls.getDeclaredMethods();
		ArrayList<Method> ms = new ArrayList(methods.length);
		Method[] arrayOfMethod1;
		int j = (arrayOfMethod1 = methods).length;
		for (int i = 0; i < j; i++) {
			Method method = arrayOfMethod1[i];
			if ((method.getReturnType() != null)
					&& (method.getName().length() > 3)
					&& (method.getName().startsWith("get"))) {
				ms.add(method);
			}
		}
		Field[] fields = cls.getDeclaredFields();
		Method fieldGetMet = null;
		Field[] arrayOfField1;
		int m = (arrayOfField1 = fields).length;
		for (int k = 0; k < m; k++) {
			Field field = arrayOfField1[k];
			String fieldGetName = parseMethodName(field.getName(), "get");
			if ((fieldGetMet = haveMethod(ms, fieldGetName)) != null) {
				ms.remove(fieldGetMet);

				setValueMap(obj, valueMap, fieldGetMet, field.getName());
			}
		}
		for (Method method : ms) {
			String methodName = method.getName();
			String filedName = methodName.substring(3);
			filedName = filedName.substring(0, 1).toLowerCase()
					+ filedName.substring(1);
			setValueMap(obj, valueMap, method, filedName);
		}
		return valueMap;
	}

	public static Method haveMethod(ArrayList<Method> methods,
			String fieldMethod) {
		for (Method met : methods) {
			if (fieldMethod.equals(met.getName())) {
				return met;
			}
		}
		return null;
	}

	public static String parseMethodName(String fieldName, String methodType) {
		if ((fieldName == null) || ("".equals(fieldName))) {
			return null;
		}
		return methodType + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	public static void setFiedlValue(Object obj, Method fieldSetMethod,
			String fieldType, Object value) {
		try {
			if ((value != null) && (!"".equals(value))) {
				if ("String".equals(fieldType)) {
					fieldSetMethod.invoke(obj,
							new Object[] { value.toString() });
				} else if ("Date".equals(fieldType)) {
					String v = value.toString();
					String formate = null;
					if ((v.length() == "yyyy-MM-dd HH:mm:ss".length())
							&& (v.contains("-"))) {
						formate = "yyyy-MM-dd HH:mm:ss";
					} else if ((v.length() == "yyyy/MM/dd HH:mm:ss".length())
							&& (v.contains("/"))) {
						formate = "yyyy/MM/dd HH:mm:ss";
					} else if ((v.length() == "yyyy-MM-dd HH:mm".length())
							&& (v.contains("-"))) {
						formate = "yyyy-MM-dd HH:mm";
					} else if ((v.length() == "yyyy/MM/dd HH:mm".length())
							&& (v.contains("/"))) {
						formate = "yyyy/MM/dd HH:mm";
					} else if ((v.length() == "yyyy-MM-dd".length())
							&& (v.contains("-"))) {
						formate = "yyyy-MM-dd";
					} else if ((v.length() == "yyyy/MM/dd".length())
							&& (v.contains("/"))) {
						formate = "yyyy/MM/dd";
					} else if ((v.length() == "yyyy��MM��dd��".length())
							&& (v.contains("��"))) {
						formate = "yyyy��MM��dd��";
					}
					SimpleDateFormat sdf = new SimpleDateFormat(formate,
							Locale.CHINESE);
					java.util.Date temp = sdf.parse(value.toString());
					fieldSetMethod.invoke(obj, new Object[] { temp });
				} else if (("Integer".equals(fieldType))
						|| ("int".equals(fieldType))) {
					Integer intval = Integer.valueOf(Integer.parseInt(value
							.toString()));
					fieldSetMethod.invoke(obj, new Object[] { intval });
				} else if (("Long".equalsIgnoreCase(fieldType))
						|| ("long".equals(fieldType))) {
					Long temp = Long.valueOf(Long.parseLong(value.toString()));
					fieldSetMethod.invoke(obj, new Object[] { temp });
				} else if (("Double".equalsIgnoreCase(fieldType))
						|| ("double".equals(fieldType))) {
					Double temp = Double.valueOf(Double.parseDouble(value
							.toString()));
					fieldSetMethod.invoke(obj, new Object[] { temp });
				} else if (("Float".equalsIgnoreCase(fieldType))
						|| ("float".equals(fieldType))) {
					Float temp = Float.valueOf(Float.parseFloat(value
							.toString()));
					fieldSetMethod.invoke(obj, new Object[] { temp });
				} else if (("Boolean".equalsIgnoreCase(fieldType))
						|| ("boolean".equals(fieldType))) {
					try {
						int iv = Integer.parseInt(value.toString());
						fieldSetMethod.invoke(obj, new Object[] { Boolean
								.valueOf((String) (iv != 0 ? 1 : false)) });
					} catch (Exception e) {
						fieldSetMethod.invoke(obj,
								new Object[] { Boolean.valueOf(Boolean
										.parseBoolean(value.toString())) });
					}
				} else {
					fieldSetMethod.invoke(obj, new Object[] { value });
				}
			}
		} catch (Exception e) {
			XCLog.printStackTrace(e);
		}
	}

	public static <T> T parseObject(JSONObject jo, Class<T> clazz)
			throws JSONException {
		if ((clazz == null) || (isNull(jo))) {
			return null;
		}
		T obj = newInstance(clazz);
		if (obj == null) {
			return null;
		}
		if (isMap(clazz)) {
			setField(obj, jo);
		} else {
			Method[] methods = clazz.getDeclaredMethods();
			Field[] fields = clazz.getDeclaredFields();
			ArrayList<Method> ms = new ArrayList(methods.length);
			Method[] arrayOfMethod1;
			int j = (arrayOfMethod1 = methods).length;
			for (int i = 0; i < j; i++) {
				Method method = arrayOfMethod1[i];
				if ((method.getName().startsWith("set"))
						&& (method.getName().length() > 3)
						&& (method.getParameterTypes().length == 1)) {
					ms.add(method);
				}
			}
			Method fieldSetMet = null;
			Field[] arrayOfField1;
			int k = (arrayOfField1 = fields).length;
			for (j = 0; j < k; j++) {
				Field f = arrayOfField1[j];
				String setMetodName = parseMethodName(f.getName(), "set");
				if ((fieldSetMet = haveMethod(ms, setMetodName)) != null) {
					ms.remove(fieldSetMet);

					setField(obj, fieldSetMet, f, jo);
				}
			}
			for (Method method : ms) {
				String methodName = method.getName();
				String filedName = methodName.substring(3);
				filedName = filedName.substring(0, 1).toLowerCase()
						+ filedName.substring(1);
				Class<?> fClass = method.getParameterTypes()[0];
				Class<?> fcClass = Object.class;
				if (isCollection(fClass)) {
					Type gType = fClass.getGenericSuperclass();
					if ((gType instanceof ParameterizedType)) {
						ParameterizedType ptype = (ParameterizedType) gType;
						Type[] targs = ptype.getActualTypeArguments();
						if ((targs != null) && (targs.length > 0)) {
							Type t = targs[0];
							fcClass = (Class) t;
						}
					}
				} else if (isMap(fClass)) {
					Type gType = fClass.getGenericSuperclass();
					if ((gType instanceof ParameterizedType)) {
						ParameterizedType ptype = (ParameterizedType) gType;
						Type[] targs = ptype.getActualTypeArguments();
						if ((targs != null) && (targs.length > 1)) {
							try {
								fcClass = (Class) targs[1];
							} catch (Exception e) {
								fcClass = Object.class;
							}
						}
					}
				}
				setField(obj, method, jo, fClass, filedName, fcClass);
			}
		}
		return obj;
	}

	public static <T> T parseObject(String jsonStr, Class<T> clazz)
			throws JSONException {
		if ((clazz == null) || (jsonStr == null) || (jsonStr.length() == 0)) {
			return null;
		}
		JSONObject jo = null;
		jo = new JSONObject(jsonStr.replaceAll("\\/", "/"));
		if (isNull(jo)) {
			return null;
		}
		return (T) parseObject(jo, clazz);
	}

	public static <T> ArrayList<T> parseArray(JSONArray ja, Class<T> clazz) {
		if ((clazz == null) || (isNull(ja))) {
			return null;
		}
		int len = ja.length();

		ArrayList<T> array = new ArrayList();
		for (int i = 0; i < len; i++) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				T o = parseObject(jo, clazz);

				array.add(o);
			} catch (JSONException e) {
				XCLog.printStackTrace(e);
			}
		}
		return array;
	}

	public static <T> ArrayList<T> parseArray(String jsonStr, Class<T> clazz) {
		if ((clazz == null) || (jsonStr == null) || (jsonStr.length() == 0)) {
			return null;
		}
		JSONArray jo = null;
		try {
			jo = new JSONArray(jsonStr.replaceAll("\\/", "/"));
		} catch (JSONException e) {
			XCLog.printStackTrace(e);
		}
		if (isNull(jo)) {
			return null;
		}
		return parseArray(jo, clazz);
	}

	public static <T> Collection<T> parseCollection(JSONArray ja,
			Class<?> collectionClazz, Class<T> genericType) {
		if ((collectionClazz == null) || (genericType == null) || (isNull(ja))) {
			return null;
		}
		Collection<T> collection = null;
		try {
			collection = (Collection) newInstance(collectionClazz);
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
		for (int i = 0; i < ja.length(); i++) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				if ((!isNull(jo)) && (genericType != Object.class)) {
					collection.add(parseObject(jo, genericType));
				} else {
					collection.add((T) jo);
				}
			} catch (JSONException e) {
				XCLog.printStackTrace(e);
				return null;
			}
		}
		return collection;
	}

	public static Map<?, ?> parseMap(JSONObject ja, Class<?> value) {
		Iterator<String> keyIter = ja.keys();
		Map<String, Object> valueMap = new HashMap();
		while (keyIter.hasNext()) {
			String key = (String) keyIter.next();

			JSONObject j = ja.optJSONObject(key);
			Object v = null;
			try {
				v = parseObject(j, value);
			} catch (JSONException e) {
				XCLog.printStackTrace(e);
			}
			valueMap.put(key, v);
		}
		return valueMap;
	}

	public static <T> Collection<T> parseCollection(String jsonStr,
			Class<?> collectionClazz, Class<T> genericType) {
		if ((collectionClazz == null) || (genericType == null)
				|| (jsonStr == null) || (jsonStr.length() == 0)) {
			return null;
		}
		JSONArray jo = null;
		try {
			jsonStr = jsonStr.replaceAll("\\/", "/");

			int index = jsonStr.indexOf("[");
			String arrayString = null;
			if (-1 != index) {
				arrayString = jsonStr.substring(index);
			}
			if (arrayString != null) {
				jo = new JSONArray(arrayString);
			} else {
				jo = new JSONArray(jsonStr);
			}
		} catch (JSONException e) {
			XCLog.printStackTrace(e);
		}
		if (isNull(jo)) {
			return null;
		}
		return parseCollection(jo, collectionClazz, genericType);
	}

	private static <T> T newInstance(Class<T> clazz) throws JSONException {
		if (clazz == null) {
			return null;
		}
		T obj = null;
		if (clazz.isInterface()) {
			if (clazz.equals(Map.class)) {
				obj = (T) new HashMap();
			} else if (clazz.equals(List.class)) {
				obj = (T) new ArrayList();
			} else if (clazz.equals(Set.class)) {
				obj = (T) new HashSet();
			} else {
				throw new JSONException("unknown interface: " + clazz);
			}
		} else {
			try {
				obj = clazz.newInstance();
			} catch (Exception e) {
				throw new JSONException(e.getMessage() + ":" + clazz);
			}
		}
		return obj;
	}

	private static void setField(Object obj, JSONObject jo) {
		try {
			Iterator<String> keyIter = jo.keys();

			Map<String, Object> valueMap = (Map) obj;
			while (keyIter.hasNext()) {
				String key = (String) keyIter.next();
				Object value = jo.get(key);
				valueMap.put(key, value);
			}
		} catch (JSONException e) {
			XCLog.printStackTrace(e);
		}
	}

	private static void setField(Object obj, Method fieldSetMethod,
			Field field, JSONObject jo) {
		String name = field.getName();
		Class<?> clazz = field.getType();
		if (jo.isNull(name)) {
			return;
		}
		Class<?> fcClass = null;
		if (isCollection(clazz)) {
			Class<?> c = null;
			Type gType = field.getGenericType();
			if ((gType instanceof ParameterizedType)) {
				ParameterizedType ptype = (ParameterizedType) gType;
				Type[] targs = ptype.getActualTypeArguments();
				if ((targs != null) && (targs.length > 0)) {
					Type t = targs[0];
					c = (Class) t;
				}
			}
			fcClass = c;
		} else if (isMap(clazz)) {
			Class<?> value = null;
			Type gType = field.getGenericType();
			if ((gType instanceof ParameterizedType)) {
				ParameterizedType ptype = (ParameterizedType) gType;
				Type[] targs = ptype.getActualTypeArguments();
				if ((targs != null) && (targs.length > 1)) {
					try {
						value = (Class) targs[1];
					} catch (Exception e) {
						value = Object.class;
					}
				}
			}
			fcClass = value;
		}
		setField(obj, fieldSetMethod, jo, clazz, name, fcClass);
	}

	private static void setField(Object obj, Method fieldSetMethod,
			JSONObject jo, Class<?> fClass, String fName, Class<?> fcClass) {
		if (jo.isNull(fName)) {
			return;
		}
		try {
			if (isArray(fClass)) {
				Class<?> c = fClass.getComponentType();
				JSONArray ja = jo.optJSONArray(fName);
				if (!isNull(ja)) {
					Object array = parseArray(ja, c);
					setFiedlValue(obj, fieldSetMethod, fClass.getSimpleName(),
							array);
				}
			} else if (isCollection(fClass)) {
				JSONArray ja = jo.optJSONArray(fName);
				if (!isNull(ja)) {
					Object o = parseCollection(ja, fClass, fcClass);
					setFiedlValue(obj, fieldSetMethod, fClass.getSimpleName(),
							o);
				}
			} else if (isSingle(fClass)) {
				Object o = jo.opt(fName);
				if (o != null) {
					setFiedlValue(obj, fieldSetMethod, fClass.getSimpleName(),
							o);
				}
			} else if (isMap(fClass)) {
				JSONObject ja = jo.optJSONObject(fName);
				if (!isNull(ja)) {
					Object o = parseMap(ja, fcClass);
					setFiedlValue(obj, fieldSetMethod, fClass.getSimpleName(),
							o);
				}
			} else if (isObject(fClass)) {
				JSONObject j = jo.optJSONObject(fName);
				if ((!isNull(j)) && (fClass != Object.class)) {
					Object o = parseObject(j, fClass);
					setFiedlValue(obj, fieldSetMethod, fClass.getSimpleName(),
							o);
				} else {
					setFiedlValue(obj, fieldSetMethod, fClass.getSimpleName(),
							jo.get(fName));
				}
			} else if (!isList(fClass)) {
				throw new Exception("unknow type!");
			}
		} catch (Exception e) {
			XCLog.printStackTrace(e);
		}
	}

	private static void setField(Object obj, Field field, JSONObject jo) {
		String name = field.getName();
		Class<?> clazz = field.getType();
		try {
			if (isArray(clazz)) {
				Class<?> c = clazz.getComponentType();
				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object array = parseArray(ja, c);
					field.set(obj, array);
				}
			} else if (isCollection(clazz)) {
				Class<?> c = null;
				Type gType = field.getGenericType();
				if ((gType instanceof ParameterizedType)) {
					ParameterizedType ptype = (ParameterizedType) gType;
					Type[] targs = ptype.getActualTypeArguments();
					if ((targs != null) && (targs.length > 0)) {
						Type t = targs[0];
						c = (Class) t;
					}
				}
				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object o = parseCollection(ja, clazz, c);
					field.set(obj, o);
				}
			} else if (isSingle(clazz)) {
				Object o = jo.opt(name);
				if (o != null) {
					field.set(obj, o);
				}
			} else if (isObject(clazz)) {
				JSONObject j = jo.optJSONObject(name);
				if (!isNull(j)) {
					Object o = parseObject(j, clazz);
					field.set(obj, o);
				}
			} else if (isList(clazz)) {
				JSONObject j = jo.optJSONObject(name);
				if (!isNull(j)) {
					Object o = parseObject(j, clazz);
					field.set(obj, o);
				}
			} else {
				throw new Exception("unknow type!");
			}
		} catch (Exception e) {
			XCLog.printStackTrace(e);
		}
	}

	private static boolean isNull(Object obj) {
		if ((obj instanceof JSONObject)) {
			return JSONObject.NULL.equals(obj);
		}
		return obj == null;
	}

	private static boolean isSingle(Class<?> clazz) {
		return (isBoolean(clazz)) || (isNumber(clazz)) || (isString(clazz))
				|| (isDate(clazz));
	}

	private static boolean isBoolean(Class<?> clazz) {
		return (clazz != null)
				&& ((Boolean.TYPE.isAssignableFrom(clazz)) || (Boolean.class
						.isAssignableFrom(clazz)));
	}

	private static boolean isDate(Class<?> clazz) {
		return (clazz != null)
				&& (java.util.Date.class.isAssignableFrom(clazz));
	}

	private static boolean isNumber(Class<?> clazz) {
		return (clazz != null)
				&& ((Byte.TYPE.isAssignableFrom(clazz))
						|| (Short.TYPE.isAssignableFrom(clazz))
						|| (Integer.TYPE.isAssignableFrom(clazz))
						|| (Long.TYPE.isAssignableFrom(clazz))
						|| (Float.TYPE.isAssignableFrom(clazz))
						|| (Double.TYPE.isAssignableFrom(clazz)) || (Number.class
							.isAssignableFrom(clazz)));
	}

	private static boolean isString(Class<?> clazz) {
		return (clazz != null)
				&& ((String.class.isAssignableFrom(clazz))
						|| (Character.TYPE.isAssignableFrom(clazz)) || (Character.class
							.isAssignableFrom(clazz)));
	}

	private static boolean isObject(Class<?> clazz) {
		return (clazz != null) && (!isSingle(clazz)) && (!isArray(clazz))
				&& (!isCollection(clazz)) && (!isMap(clazz))
				&& (!isDate(clazz));
	}

	private static boolean isArray(Class<?> clazz) {
		return (clazz != null) && (clazz.isArray());
	}

	private static boolean isCollection(Class<?> clazz) {
		return (clazz != null) && (Collection.class.isAssignableFrom(clazz));
	}

	private static boolean isMap(Class<?> clazz) {
		return (clazz != null) && (Map.class.isAssignableFrom(clazz));
	}

	private static boolean isList(Class<?> clazz) {
		return (clazz != null) && (List.class.isAssignableFrom(clazz));
	}
}
