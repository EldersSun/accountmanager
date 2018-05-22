package com.accountingmanager.Sys.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.accountingmanager.Application.AccountApplication;


/**
 * 和Android有关的工具箱
 */
public class AdrToolkit implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static String apkVersionName;

    private static final int REQUEST_PHONE = 0;


    /**
     * 获取当前Apk版本名称 </br> android:versionName
     *
     * @return
     */
    public static String getApkVersionName() {
        if (StringUtils.isNotBlank(apkVersionName)) {
            return apkVersionName;
        }
        Context context = AccountApplication.getInstance();
        try {
            apkVersionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return apkVersionName;
    }

    /**
     * 获取当前Apk版本号</br> android:versionCode
     *
     * @return
     */
    public static int getApkVersionCode() {
        Context context = AccountApplication.getInstance();
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verCode;
    }

    /**
     * 获取到客户ID，即IMSI(用于识别移动用户所归属的移动通信网；MSIN是移动用户识别码，用以识别某一移动通信网中的移动用户)
     *
     * @return
     */
    public static String getIMSI() {
        Context context = AccountApplication.getInstance();
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    private static String imei = null;

    /**
     * 获取手机的IMEI号码(是移动设备国际身份码的缩写，移动设备国际辨识码，是由15位数字组成的"电子串号"，它与每台手机一一对应，
     * 而且该码是全世界唯一的)
     *
     * @return
     */
    public static String getIMEI(final Context context) {
        if (null != imei) {
            return imei;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        return imei;
    }


    // 获取手机号码
    public static String getPhoneNumber() {
        Context context = AccountApplication.getInstance();
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    // 获取手机的android操作系统版本
    public static String getDeviceSoftwareVersion() {
        Context context = AccountApplication.getInstance();
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceSoftwareVersion();
    }

    // 获取到SIM卡唯一编号ID
    public static String getSimSerialNumber() {
        Context context = AccountApplication.getInstance();
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
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
     * 将px转成dip
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        Context context = AccountApplication.getInstance();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip转成px
     *
     * @param pxValue
     * @return
     */
    public static int dip2px(float dipValue) {
        Context context = AccountApplication.getInstance();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private static String SDCARD_PATH = null;

    /**
     * 获取SDCard的路径
     *
     * @return
     */
    public static String getSDCardPath() {
        if (null == SDCARD_PATH) {
            SDCARD_PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        }
        return SDCARD_PATH;
    }

    /*
     * 判断是否需要隐藏软键盘
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top
                    + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /*
     * 隐藏软键盘
     */
    public static Boolean hideInputMethod(Activity context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && null != context.getCurrentFocus()) {
            return imm.hideSoftInputFromWindow(context.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // View currentFocus = context.getCurrentFocus();
        // if (null != currentFocus) {
        // currentFocus.clearFocus();
        // }
        return false;
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editTv
     */
    public static void showInputMethod(Activity context, EditText editTv) {
        InputMethodManager inputManager = (InputMethodManager) editTv
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editTv, 0);
    }

    public static String getDeviceType() {
        return Build.MODEL;// 机型
    }

    /**
     * 设置app 应用占用手机的内存
     *
     * @param size
     */
    public static void setMinHeapSize(long size) {
        try {
            Class<?> cls = Class.forName("dalvik.system.VMRuntime");
            Method getRuntime = cls.getMethod("getRuntime");
            Object obj = getRuntime.invoke(null);// obj就是Runtime
            if (obj == null) {
                System.err.println("obj is null");
            } else {
                System.out.println(obj.getClass().getName());
                Class<?> runtimeClass = obj.getClass();
                Method setMinimumHeapSize = runtimeClass.getMethod(
                        "setMinimumHeapSize", long.class);
                setMinimumHeapSize.invoke(obj, size);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE:
                break;
        }
    }
}
