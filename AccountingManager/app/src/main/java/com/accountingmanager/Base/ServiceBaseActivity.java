package com.accountingmanager.Base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnTouchEventClickListener;
import com.accountingmanager.Sys.Communication.OnkeyDownInterface;

import java.util.List;


public class ServiceBaseActivity extends FragmentActivity {

    public final static String homeFragmentKey = "homeFragment";
    public final static String contentKey = "contentKey";
    private FrameLayout content;
    private OnkeyDownInterface onkeyDownInterface;
    private OnTouchEventClickListener onTouchEventClickListener;


    public void setOnkeyDownInterface(OnkeyDownInterface onkeyDownInterface) {
        this.onkeyDownInterface = onkeyDownInterface;
    }

    public void setOnTouchEventClickListener(OnTouchEventClickListener onTouchEventClickListener) {
        this.onTouchEventClickListener = onTouchEventClickListener;
    }

    public static void startActivity(Activity context, String homeFragment) {
        startActivity(context, homeFragment, null);
    }

    public static void startActivity(Activity context, String homeFragment,
                                     Bundle bundle) {
        Bundle contentBundle = new Bundle();
        if (bundle != null) {
            contentBundle.putBundle(contentKey, bundle);
        }
        Intent intent = new Intent(context, ServiceBaseActivity.class);
        contentBundle.putString(homeFragmentKey, homeFragment);
        intent.putExtras(contentBundle);
        context.startActivity(intent);
    }


    public static void startActivityForResult(Activity context, String homeFragment, int code,
                                              Bundle bundle) {
        Intent intent = new Intent(context, ServiceBaseActivity.class);
        intent.putExtra(homeFragmentKey, homeFragment);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent, code);
    }

    public static void startActivityForResult(Activity context, String homeFragment, int code) {
        startActivityForResult(context, homeFragment, code, null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (null != imm && null != getCurrentFocus()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        0);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_common);
        initWidgets();
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private Fragment fragment;

    protected void initWidgets() {
        content = (FrameLayout) findViewById(R.id.content);
        Bundle bundle = getIntent().getExtras();
        String homeFragment = "";
        if (bundle.containsKey(homeFragmentKey)) {
            homeFragment = bundle.getString(homeFragmentKey);
        }

        Class class1 = null;
        try {
            if (null != homeFragment
                    && null != (class1 = Class.forName(homeFragment))) {
                Fragment fm = (Fragment) class1.newInstance();
                Bundle contentBundle = bundle.getBundle(contentKey);
                if (null != contentBundle) {
                    fm.setArguments(contentBundle);
                }
                pushFragment(fm);
            }
        } catch (Exception e) {
        }
    }

    private void pushFragment(Fragment fragment) {
        FragmentManager fmManager = getSupportFragmentManager();
//        fmManager.popBackStack(fragment.getClass().getName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fmManager.beginTransaction();
        if (fragment == null) {
            return;
        }
        fragmentTransaction.add(R.id.content, fragment);
//        fragmentTransaction.isAddToBackStackAllowed();
//        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commitAllowingStateLoss();

//        List<Fragment> list = fmManager.getFragments();
//        if(list == null || list.size() == 0){
//            this.finish();
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onkeyDownInterface != null) {
            return onkeyDownInterface.OnkeyDown(keyCode, event) == true ? true : super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (onTouchEventClickListener != null) {
            return onTouchEventClickListener.setOnTouchEventClickListener(event) == true ? true : super.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
