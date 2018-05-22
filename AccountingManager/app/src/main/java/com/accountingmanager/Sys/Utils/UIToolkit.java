package com.accountingmanager.Sys.Utils;

import android.content.Context;
import android.view.WindowManager;

import java.lang.reflect.Method;


public class UIToolkit {
    @SuppressWarnings("unused")
    private static UIToolkit instance = new UIToolkit();

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * ƴ��ĳ���Ե� get����set����
     *
     * @param fieldName  �ֶ�����
     * @param methodType ��������
     * @return ��������
     */
    public static String parseMethodName(String fieldName, String methodType) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return methodType + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * �ж��Ƿ����ĳ���Ե� get����
     *
     * @param methods     ���÷���������
     * @param fieldMethod ��������
     * @return true����false
     */
    public static boolean haveMethod(Method[] methods, String fieldMethod) {
        for (Method met : methods) {
            if (fieldMethod.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    //С������
    public static final int FontSizeStyleSmall = 0;
    //�к�����
    public static final int FontSizeStyleMiddle = 1;
    //�������
    public static final int FontSizeStyleLarge = 2;
    private static int g_smallFontSize = 12;
    private static int g_middleFontSize = 14;
    private static int g_largeFontSize = 16;

    //��Ŀ�������ͺ�
    public static int getDefaultFontSize(int fontStyle) {
        if (fontStyle == FontSizeStyleSmall) {
            return g_smallFontSize;
        } else if (fontStyle == FontSizeStyleLarge) {
            return g_largeFontSize;
        } else {
            return g_middleFontSize;
        }
    }

    public static int smallFont() {
        return getDefaultFontSize(FontSizeStyleSmall);
    }

    public static int middleFont() {
        return getDefaultFontSize(FontSizeStyleMiddle);
    }

    public static int largeFont() {
        return getDefaultFontSize(FontSizeStyleLarge);
    }

    //������Ŀ�������ͺ�
    public static void setDefaultFontSize(Context context, int fontStyle, int fontSizeSP) {
        if (fontStyle == FontSizeStyleSmall) {
            g_smallFontSize = sp2px(context, fontSizeSP);
        } else if (fontStyle == FontSizeStyleLarge) {
            g_largeFontSize = sp2px(context, fontSizeSP);
        } else {
            g_middleFontSize = sp2px(context, fontSizeSP);
        }
    }


    /**
     * 获取屏幕的宽高
     *
     * @param context
     * @return
     */
    public static int[] getSceenDisplay(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        int result[] = {width, height};
        return result;
    }


}