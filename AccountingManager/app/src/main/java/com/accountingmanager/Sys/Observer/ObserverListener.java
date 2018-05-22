package com.accountingmanager.Sys.Observer;

import java.util.Objects;

/**
 * 观察者接口
 * Created by Home-Pc on 2017/4/25.
 */

public interface ObserverListener {
    public void observerUpData(String msgKey,Object object);
}
