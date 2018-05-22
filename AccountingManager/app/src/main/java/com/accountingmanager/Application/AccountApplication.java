package com.accountingmanager.Application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.accountingmanager.Base.BaseActivity;
import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.Sys.GreenDao.GeenDaoManager;
import com.accountingmanager.Sys.Utils.StringUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * application
 * Created by Home_Pc on 2017/3/8.
 */

public class AccountApplication extends Application {

    private static AccountApplication instance;
    public static AccountApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Application",Application.class.getName());
        instance = this;
        GeenDaoManager.getInstance();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }


    public void setLruCache(String key, Bitmap bitmap) {
        if (StringUtils.isBlank(key) || bitmap == null) {
            return;
        }
        lruCache.put(key, bitmap);
    }

    public Bitmap getLruCache(String key) {
        return lruCache.get(key);
    }

    public void clearLruCache() {
        lruCache.evictAll();
    }

    public LruCache<String, Bitmap> lruCache = new LruCache<>(1024 * 1024 * 10);

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
