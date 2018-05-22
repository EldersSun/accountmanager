package com.accountingmanager.Base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.accountingmanager.Application.AccountApplication;
import com.accountingmanager.R;
import com.accountingmanager.Remote.IRemoteResponse;
import com.accountingmanager.Remote.RequestTool;
import com.accountingmanager.Sys.Communication.OnTouchEventClickListener;
import com.accountingmanager.Sys.Communication.OnkeyDownInterface;
import com.accountingmanager.Sys.Observer.ObserverListener;
import com.accountingmanager.Sys.Observer.ObserverManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 程序基类
 * Created by Home_Pc on 2017/3/8.
 */

public abstract class BaseActivity extends FragmentActivity implements IRemoteResponse,ObserverListener {

    private LinearLayout ac_base_layout;
    protected LayoutInflater inflater;
    protected LinearLayout ac_base_title;
    protected FrameLayout ac_base_Content;
    protected View contentView;

    protected RequestTool mRequestTool;

    private OnTouchEventClickListener onTouchEventClickListener;
    private OnkeyDownInterface onkeyDownInterface;

    public void setOnkeyDownInterface(OnkeyDownInterface onkeyDownInterface) {
        this.onkeyDownInterface = onkeyDownInterface;
    }

    public void setOnTouchEventClickListener(OnTouchEventClickListener onTouchEventClickListener) {
        this.onTouchEventClickListener = onTouchEventClickListener;
    }

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base_layout);
        inflater = LayoutInflater.from(this);
        initFindViews();
        initWidgets();
        initEvent();
    }

    protected abstract View initViews(LayoutInflater inflater);

    protected abstract void initWidgets();

    protected abstract void initEvent();

    private void initFindViews() {
        mRequestTool = new RequestTool(this);
        ac_base_layout = (LinearLayout) findViewById(R.id.ac_base_layout);
        ac_base_title = (LinearLayout) findViewById(R.id.ac_base_title);
        ac_base_Content = (FrameLayout) findViewById(R.id.ac_base_Content);
        contentView = initViews(inflater);
        if (contentView == null) {
            Log.i("initFindViews", "initFindViews为空");
            return;
        }
        ac_base_Content.addView(contentView);
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

    protected void pushFragmentController(int id, Fragment fragment, Bundle bundle, boolean isAddFlag) {
        if (this != null) {
            FragmentManager fmManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fmManager.beginTransaction();
            fragments = fmManager.getFragments();
            if (fragment != null) {
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
                if (isAddFlag) {
                    fmManager.popBackStack(fragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                fragmentTransaction.replace(id, fragment);
                if (isAddFlag) {
                    fragmentTransaction.isAddToBackStackAllowed();
                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                }

//                if(isAddFlag){
//
//                } else {
//                    fragmentTransaction.commit();
//                }

                fragmentTransaction.commitAllowingStateLoss();


//                List<Fragment> list = fmManager.getFragments();
//                if(list == null || list.size() == 0){
//                    this.finish();
//                }
            }
        }
    }



    protected void pushFragmentController(Fragment fragment, Bundle bundle, boolean isAddFlag) {
        pushFragmentController(R.id.main_container, fragment, bundle, isAddFlag);
    }

    protected void pushFragmentController(Fragment fragment, boolean isAddFlag) {
        pushFragmentController(R.id.main_container, fragment, null, isAddFlag);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onkeyDownInterface != null) {
            return onkeyDownInterface.OnkeyDown(keyCode, event) == true ? true : super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /********************************************* 关于消息 *********************************************/

    protected List<String> msgKeyList = new ArrayList<>();

    protected void observeMessage(String msgKey) {
        msgKeyList.add(msgKey);
        ObserverManager.getInstance().addObserver(msgKey,this);
    }

    protected void onReceiveMessage(String msgkey, Object msgObject) {

    }

    protected void sendMessage(String msgkey, Object msgObject) {
        ObserverManager.getInstance().sendObserver(msgkey,msgObject);
    }

    protected void removeMessage(String msgKey){
        ObserverManager.getInstance().remove(msgKey);
    }

    @Override
    public void observerUpData(String msgKey, Object object) {
        onReceiveMessage(msgKey,object);
    }

    /********************************************* 关于请求 *********************************************/
    protected void requestForHttp(int tag, String url, Map<String, Object> map, Boolean isEtc) {
        if (mRequestTool == null) {
            mRequestTool = new RequestTool(this);
        }
        mRequestTool.requestForHttp(this, tag, url, map, isEtc);
    }

    protected void requestForHttp(int tag, String url, Map<String, Object> map) {
        requestForHttp(tag, url, map, false);
    }


    /**
     * 文件上传
     *
     * @param tag
     * @param url
     * @param fileMap
     * @param map
     * @param isEtc
     */
    protected void requestForHttpFile(int tag, String url, Map<String, File> fileMap, Map<String, String> map, Boolean isEtc) {
        if (mRequestTool == null) {
            mRequestTool = new RequestTool(this);
        }
        mRequestTool.requestForHttpFile(this, tag, url, fileMap, map, isEtc);
    }

    /**
     * @param tag
     * @param url
     * @param fileMap
     * @param map
     */
    protected void requestForHttpFile(int tag, String url, Map<String, File> fileMap, Map<String, String> map) {
        requestForHttpFile(tag, url, fileMap, map, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(msgKeyList != null && msgKeyList.size() != 0){
            for(int i = 0 ; i < msgKeyList.size() ; i ++){
                removeMessage(msgKeyList.get(i));
            }
        }
        super.onDestroy();
    }
}
