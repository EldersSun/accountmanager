package com.accountingmanager.Sys.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.accountingmanager.Sys.Utils.Adr.MResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayUtils {

	public static List<String> arrayToListForString(String[] arrray) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arrray.length; i++) {
			list.add(arrray[i]);
		}
		return list;
	}

	/** 将资源文件中的图片名转换成Bitmap保存到List中 */
	public static List<Bitmap> getResourcesToBitmap(Context context, int res) {
		List<Bitmap> list = new ArrayList<Bitmap>();
		List<String> resources = ArrayUtils.arrayToListForString(context
				.getResources().getStringArray(res));
		for (int i = 0; i < resources.size(); i++) {
			list.add(BitmapFactory.decodeResource(context.getResources(),
					MResource.getIdByNameForDrawable(context, resources.get(i))));
		}
		return list;
	}

	/**
	 * 获取两个List的不同元素
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> getDiffrentForString(List<String> list1,
													List<String> list2) {
		long st = System.nanoTime();
		Map<String, Integer> map = new HashMap<String, Integer>(list1.size()
				+ list2.size());
		List<String> diff = new ArrayList<String>();
		for (String string : list1) {
			map.put(string, 1);
		}
		for (String string : list2) {
			Integer cc = map.get(string);
			if (cc != null) {
				map.put(string, ++cc);
				continue;
			}
			map.put(string, 1);
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				diff.add(entry.getKey());
			}
		}
		System.out.println("getDiffrent3 total times "
				+ (System.nanoTime() - st));
		return diff;
	}

	/**
	 * 获取两个List的不同元素
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	public static List<Integer> getDiffrentForInt(List<Integer> list1,
												  List<Integer> list2) {
		long st = System.nanoTime();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>(list1.size()
				+ list2.size());
		List<Integer> diff = new ArrayList<Integer>();
		for (Integer i : list1) {
			map.put(i, 1);
		}
		for (Integer i : list2) {
			Integer cc = map.get(i);
			if (cc != null) {
				map.put(i, ++cc);
				continue;
			}
			map.put(i, 1);
		}
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				diff.add(entry.getKey());
			}
		}
		System.out.println("getDiffrent3 total times "
				+ (System.nanoTime() - st));
		return diff;
	}

	/**
	 * String 转数组
	 * @param resources 资源
	 * @param mark	标识符
	 * @return
	 */
	public static List<String>stringToArray(String resources,String mark){
		List<String> list = new ArrayList<String>();
		if(!StringUtils.isBlank(resources)){
			if(resources.contains(mark)){
				String [] res = resources.split(mark);
				Collections.addAll(list, res);
			}
		}
		return list;
	}

	/**
	 * String 转数组
	 * @param resources 资源
	 * @param mark	标识符
	 * @return
	 */
	public static List<Integer> integerToArray(String resources,String mark){
		List<Integer> list = new ArrayList<Integer>();
		if(!StringUtils.isBlank(resources)){
			if(resources.contains(mark)){
				String [] res = resources.split(mark);
				for(int i = 0 ; i < res.length ;i++){
					list.add(Integer.valueOf(res[i].trim()));
				}
			}
		}
		return list;
	}
}
