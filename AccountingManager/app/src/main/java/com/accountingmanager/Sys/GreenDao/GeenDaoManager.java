package com.accountingmanager.Sys.GreenDao;

import com.accountingmanager.Application.AccountApplication;
import com.accountingmanager.gen.DaoMaster;
import com.accountingmanager.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 数据库操作类
 * Created by Home-Pc on 2017/4/25.
 */

public class GeenDaoManager {
    /**
     * 实现功能
     * 1.创建数据库
     * 2.创建数据库的表
     * 3.对数据库的升级
     * 4.对数据库的增删查改
     */

    /**
     * TAG
     */
    public static final String TAG = GeenDaoManager.class.getName();
    /**
     * 数据库名称
     */
    private static final String DB_NAME = "greenDao.db";
    /**
     * 多线程访问
     */
    private volatile static GeenDaoManager instance;
    /**
     * 操作类
     */
    private static DaoMaster.DevOpenHelper helper;
    /**
     * 核心类
     */
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    /**
     * 单例模式
     */
    public static GeenDaoManager getInstance() {
        if (instance == null) {
            synchronized (GeenDaoManager.class) {
                instance = new GeenDaoManager();
            }
        }
        return instance;
    }

    /**
     * 判断数据库是否存在
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            helper = new DaoMaster.DevOpenHelper(AccountApplication.getInstance(), DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 完成对数据库的操作，只是个接口
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭DaoSession
     */
    public void closeDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }


    /**
     * 关闭Helper
     */
    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    /**
     * 关闭所有的操作
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

}
