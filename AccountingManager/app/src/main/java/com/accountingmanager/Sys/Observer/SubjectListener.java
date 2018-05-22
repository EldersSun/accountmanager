package com.accountingmanager.Sys.Observer;

/**
 * 被观察者接口
 * Created by Home-Pc on 2017/4/25.
 */
public interface SubjectListener {
    /**
     * 添加
     */
    public void addObserver(String msgKey, ObserverListener observerListener);

    /**
     * 更新
     */
    public void sendObserver(String msgKey, Object content);

    /**
     * 删除
     */
    public void remove(String msgKey);
}
