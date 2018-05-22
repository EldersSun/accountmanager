package com.accountingmanager.Sys.Observer;

import java.util.HashMap;
import java.util.Map;

import static android.R.id.list;

/**
 * 观察者管理类
 * Created by Home-Pc on 2017/4/25.
 */

public class ObserverManager implements SubjectListener {
    private static ObserverManager instance;
    private Map<String, ObserverListener> observerMap = new HashMap<>();

    public static ObserverManager getInstance() {
        if (null == instance) {
            synchronized (ObserverManager.class) {
                if (null == instance) {
                    instance = new ObserverManager();
                }
            }
        }
        return instance;
    }

    /**
     * 加入监听队列
     */
    @Override
    public void addObserver(String msgKey, ObserverListener observerListener) {
        observerMap.put(msgKey, observerListener);
    }

    /**
     * 通知观察者刷新数据
     */
    @Override
    public void sendObserver(String msgKey, Object content) {
        for (Map.Entry<String, ObserverListener> entry : observerMap.entrySet()) {
            if (entry.getKey().equals(msgKey)) {
                entry.getValue().observerUpData(msgKey, content);
            }
        }
    }

    /**
     * 监听队列中移除
     */
    @Override
    public void remove(String msgKey) {
        if (observerMap.containsKey(msgKey)) {
            observerMap.remove(msgKey);
        }
    }

    /**
     * 清空监听队列
     */
    public void clearObserver() {
        if (observerMap == null || observerMap.size() == 0) {
            return;
        }
        observerMap.clear();
    }
}
