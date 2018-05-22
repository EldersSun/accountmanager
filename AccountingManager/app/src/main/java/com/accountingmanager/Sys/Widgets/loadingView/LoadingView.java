package com.accountingmanager.Sys.Widgets.loadingView;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.accountingmanager.R;

public class LoadingView {

    private static MyDialog sDialog;
    private static boolean isCanShow = true;
    private static float _alpha = 0.7f;
    private static String viewMessage = "请耐心等待";

    public static void dismiss() {
        if (sDialog != null && sDialog.isShowing()) {
            try {
                if (sDialog.mOwnerActivity != null && !sDialog.mOwnerActivity.isFinishing()) {
                    sDialog.dismiss();
                }
            } catch (Exception ex) {

            }
            sDialog = null;
        }
    }

    public static boolean isShow() {
        if (sDialog != null) {
            return sDialog.isShowing();
        }
        return false;
    }

    public static void show(Context context, Activity ownerActivity) {
        show(context, ownerActivity, context.getString(R.string.dialog_wait));
    }

    public static void show(Context context, Activity ownerActivity, String message) {
        if (sDialog != null) {
            dismiss();
        }
        if (!isCanShow) {
            return;
        }

        if (context == null || ownerActivity == null || (ownerActivity != null && ownerActivity.isFinishing())) {
            return;
        }
        viewMessage = message;
        sDialog = new MyDialog(context, ownerActivity, message);
        Window window = sDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = _alpha;
        window.setAttributes(lp);
        sDialog.setCancelable(true);
        sDialog.setCanceledOnTouchOutside(false);
        sDialog.show();
    }


    /**
     * 设置是否可以取消LoadingView
     * @param context
     * @param ownerActivity
     * @param message
     * @param canCancel
     */
    public static void show(Context context, Activity ownerActivity, String message, boolean canCancel) {
        if (sDialog != null) {
            dismiss();
        }
        if (!isCanShow) {
            return;
        }

        if (context == null || ownerActivity == null || (ownerActivity != null && ownerActivity.isFinishing())) {
            return;
        }
        viewMessage = message;
        sDialog = new MyDialog(context, ownerActivity, message);
        Window window = sDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = _alpha;
        window.setAttributes(lp);
        sDialog.setCancelable(canCancel);
        sDialog.setCanceledOnTouchOutside(false);
        sDialog.show();
    }


    public static void show(Context context, Activity ownerActivity, int resourceID) {
        String message = context.getString(resourceID);
        show(context, ownerActivity, message);
    }

    public static void showAlpha(Context context, Activity ownerActivity, float alpha) {
        _alpha = alpha;
        show(context, ownerActivity);
    }

    public static void setCanShow(boolean is) {
        isCanShow = is;
    }

    public static String getViewMessage() {
        return viewMessage;
    }
}
