package com.accountingmanager.Sys.Utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;

import com.accountingmanager.Application.AccountApplication;

/**
 * 动态设置控件的宽高和位置
 */
public class ViewSet {

	/** 屏幕高度 */
	public static int SCREEN_HEIGHT = 800;
	/** 屏幕宽度 */
	public static int SCREEN_WIDTH = 480;

	/**
	 * 获取屏幕宽高
	 * 
	 * @param ct
	 */
	public static void getScreen(Context ct) {
		DisplayMetrics displaymetrics = ct.getResources().getDisplayMetrics();

		SCREEN_HEIGHT = displaymetrics.heightPixels;
		SCREEN_WIDTH = displaymetrics.widthPixels;
	}
	
	
	/**
	 * 获取状态栏高度
	 * @param ct
	 * @return true:竖屏;false:横屏
	 */
	public static int getStatusBarHeight(Context context) {

		Class c = null;
		Object bj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			bj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(bj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}
	/**
	 * 获取屏幕宽高
	 * @param ct
	 * @return true:竖屏;false:横屏
	 */
	public static boolean getScreena(Context ct) {
		DisplayMetrics displaymetrics = ct.getResources().getDisplayMetrics();
		
		SCREEN_HEIGHT = displaymetrics.heightPixels;
		SCREEN_WIDTH = displaymetrics.widthPixels;
		return SCREEN_HEIGHT > SCREEN_WIDTH;
	}
	
	/**
	 * 获取控件对应高度
	 * 
	 * @param f
	 *            比例
	 * @return
	 */
	public static int getViewHeight(float f) {
		return (int) (SCREEN_HEIGHT * f);
	}

	/**
	 * 获取控件对应宽度
	 * 
	 * @param f
	 *            比例
	 * @return
	 */
	public static int getViewWidth(float f) {
		return (int) (SCREEN_WIDTH * f);
	}

	/**
	 * 动态设置控件的宽高和位置</br> 规则：先满足距离左面和距离上面</br> 宽高传入值=-1为FILL_PARENT，=-2
	 * 为WRAP_CONTENT
	 * 
	 * @param view
	 *            控件
	 * @param height
	 *            高
	 * @param width
	 *            宽
	 * @param lift
	 *            距离左面
	 * @param top
	 *            距离上面
	 * @param right
	 *            距离右面
	 * @param bottom
	 *            距离下面
	 */
	public static void setView(View view, int height, int width, int left,
			int top, int right, int bottom) {

		if (view != null) {

			MarginLayoutParams params = (MarginLayoutParams) view
					.getLayoutParams();

			if (left > 0) {
				params.leftMargin = left;
			}

			if (top > 0) {
				params.topMargin = top;
			}

			if (right > 0) {
				params.rightMargin = right;
			}

			if (bottom > 0) {
				params.bottomMargin = bottom;
			}

			if (height > 0) {
				params.height = height;
			} else if (height == -1) {
				params.height = ViewGroup.LayoutParams.FILL_PARENT;
			} else if (height == -2) {
				params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			if (width > 0) {
				params.width = width;
			} else if (width == -1) {
				params.width = ViewGroup.LayoutParams.FILL_PARENT;
			} else if (width == -2) {
				params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			view.setLayoutParams(params);
		}

	}

	/**
	 * 动态设置控件的宽高</br> 宽高传入值=-1为FILL_PARENT，=-2 为WRAP_CONTENT
	 * 
	 * @param view
	 *            控件
	 * @param height
	 *            高
	 * @param width
	 *            宽
	 */
	public static void setViewWidthHeight(View view, int height, int width) {

		if (view != null) {

			MarginLayoutParams params = (MarginLayoutParams) view
					.getLayoutParams();

			if (height > 0) {
				params.height = height;
			} else if (height == -1) {
				params.height = ViewGroup.LayoutParams.FILL_PARENT;
			} else if (width == -2) {
				params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			if (width > 0) {
				params.width = width;
			} else if (width == -1) {
				params.width = ViewGroup.LayoutParams.FILL_PARENT;
			} else if (width == -2) {
				params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			view.setLayoutParams(params);
		}
	}
	/**
	 * 按比例动态设置控件的宽高
	 * @param view
	 * @param heightRatio
	 * @param widthRatio
	 * @return

	 */
	public static int[] setViewRatioa(View view, float heightRatio,
			float widthRatio) {
		return setViewRatio(view, SCREEN_HEIGHT, SCREEN_WIDTH, heightRatio, widthRatio);
	}
	/**
	 * 按比例动态设置控件的宽高
	 * 
	 * @param view
	 * @param parentHeitht
	 * @param heightRatio
	 * @param parentWidth
	 * @param widthRatio
	 */
	public static int[] setViewRatio(View view, int parentHeitht, int parentWidth,
			float heightRatio, float widthRatio) {

		int height = (int) (parentHeitht * heightRatio);
		int width = (int) (parentWidth * widthRatio);

		if (view != null) {

			MarginLayoutParams params = (MarginLayoutParams) view
					.getLayoutParams();

			if (height > 0) {
				params.height = height;
			}

			if (width > 0) {
				params.width = width;
			}

			view.setLayoutParams(params);
		}
		return new int[]{width,height};
	}

	/**
	 * 按比例动态设置控件的宽高
	 * 
	 * @param view
	 * @param height
	 *            比例
	 * @param width
	 *            比例
	 */
	public static void setViewRatio(View view, float heightRatio,
			float widthRatio) {

		int height = (int) (SCREEN_HEIGHT * heightRatio);
		int width = (int) (SCREEN_WIDTH * widthRatio);

		if (view != null) {

			MarginLayoutParams params = (MarginLayoutParams) view
					.getLayoutParams();

			if (height > 0) {
				params.height = height;
			}

			if (width > 0) {
				params.width = width;
			}

			view.setLayoutParams(params);
		}
	}

	/**
	 * 按比例设置控件宽高和位置
	 * 
	 * @param view
	 * @param heightRatio
	 * @param widthRatio
	 * @param leftRatio
	 * @param topRatio
	 * @param rightRatio
	 * @param bottomRatio
	 */
	public static void setViewRatio(View view, float heightRatio,
			float widthRatio, float leftRatio, float topRatio,
			float rightRatio, float bottomRatio) {

		int height = (int) (SCREEN_HEIGHT * heightRatio);
		int width = (int) (SCREEN_WIDTH * widthRatio);
		int left = (int) (SCREEN_WIDTH * leftRatio);
		int top = (int) (SCREEN_HEIGHT * topRatio);
		int right = (int) (SCREEN_WIDTH * rightRatio);
		int bottom = (int) (SCREEN_HEIGHT * bottomRatio);

		if (view != null) {

			MarginLayoutParams params = (MarginLayoutParams) view
					.getLayoutParams();

			if (left > 0) {
				params.leftMargin = left;
			}

			if (top > 0) {
				params.topMargin = top;
			}

			if (right > 0) {
				params.rightMargin = right;
			}

			if (bottom > 0) {
				params.bottomMargin = bottom;
			}

			if (height > 0) {
				params.height = height;
			}

			if (width > 0) {
				params.width = width;
			}

			view.setLayoutParams(params);
		}

	}
	/**
	 * 按比例设置控件宽高和位置
	 * @param view
	 * @param parentHeitht
	 * @param parentWidth
	 * @param heightRatio
	 * @param widthRatio
	 * @param leftMarginRatio
	 * @param topMarginRatio
	 * @param rightMarginRatio
	 * @param bottomMarginRatio
	 */
	public static void setViewRatio(View view, int parentHeitht, int parentWidth, float heightRatio,
			float widthRatio, float leftMarginRatio, float topMarginRatio,
			float rightMarginRatio, float bottomMarginRatio) {

		int height = (int) (parentHeitht * heightRatio);
		int width = (int) (parentWidth * widthRatio);
		int left = (int) (parentWidth * leftMarginRatio);
		int top = (int) (parentHeitht * topMarginRatio);
		int right = (int) (parentWidth * rightMarginRatio);
		int bottom = (int) (parentHeitht * bottomMarginRatio);

		if (view != null) {
			MarginLayoutParams params = (MarginLayoutParams) view
					.getLayoutParams();

			if (left > 0) {
				params.leftMargin = left;
			}

			if (top > 0) {
				params.topMargin = top;
			}

			if (right > 0) {
				params.rightMargin = right;
			}

			if (bottom > 0) {
				params.bottomMargin = bottom;
			}

			if (height > 0) {
				params.height = height;
			}

			if (width > 0) {
				params.width = width;
			}
			view.setLayoutParams(params);
		}
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		Context context = AccountApplication.getInstance();
		return context.getApplicationContext().getResources()
				.getDisplayMetrics().heightPixels
				- AdrToolkit.dip2px(20);// 天线高度
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @return
	 */
	public static int getScreenWidth() {
		Context context = AccountApplication.getInstance();
		return context.getApplicationContext().getResources()
				.getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取某个View的高度
	 * 
	 * @param view
	 * @return
	 */
	public static int getViewHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int height = view.getMeasuredHeight();
		int width = view.getMeasuredWidth();
		return height;
	}

	/**
	 * 设置控件所在的位置X，并且不改变宽高， X为绝对位置，此时Y可能归0
	 */
	public static void setLayoutX(View view, int x) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(x, margin.topMargin, x + margin.width,
				margin.bottomMargin);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 设置控件所在的位置Y，并且不改变宽高， Y为绝对位置，此时X可能归0
	 */
	public static void setLayoutY(View view, int y) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(margin.leftMargin, y, margin.rightMargin, y
				+ margin.height);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 设置控件所在的位置XY，并且不改变宽高， XY为绝对位置
	 */
	public static void setLayout(View view, int x, int y) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(x, y, x + margin.width, y + margin.height);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
	}

}
