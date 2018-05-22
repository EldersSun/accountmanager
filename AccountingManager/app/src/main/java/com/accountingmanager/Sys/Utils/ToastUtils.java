package com.accountingmanager.Sys.Utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.accountingmanager.Application.AccountApplication;


/**
 * 简单提示Toast工具类
 */
public class ToastUtils {

    private static Toast mToast;


    public static void shortShow(Context context, String msg) {
        if (null != msg && msg.length() > 0 && null != context) {
            if (mToast == null) {
                mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }
    }

    public static void shortShow(String str) {
        shortShow(AccountApplication.getInstance(),str);
    }

    public static void shortShow(int source) {
        if (mToast == null) {
            mToast = Toast.makeText(AccountApplication.getInstance(),
                    source,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(source);
        }
        mToast.show();
    }

}
