package com.accountingmanager.Sys.GreenDao;

import com.accountingmanager.Sys.Model.AssetsElementModel;

import java.util.Collections;
import java.util.List;

/**
 * 完成对某一张表的具体操作
 * Created by Home-Pc on 2017/4/25.
 */
public class CommonUtils {
    private static CommonUtils instance;
    private GeenDaoManager daoManager;

    public CommonUtils() {
        daoManager = GeenDaoManager.getInstance();
    }

    /**
     * 单例模式
     */
    public static CommonUtils getInstance() {
        if (instance == null) {
            synchronized (GeenDaoManager.class) {
                instance = new CommonUtils();
            }
        }
        return instance;
    }

    /**
     * 对数据库中student表的插入操作
     *
     * @param
     * @return
     */
    public boolean insertDB(AssetsElementModel model) {
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(model) != -1 ? true : false;
        return flag;
    }

    /**
     * 批量插入
     *
     * @param
     * @return
     */
    public boolean inserMultDB(final List<AssetsElementModel> modelList) {
        //标识
        boolean flag = false;
        try {
            //插入操作耗时
            daoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (AssetsElementModel model : modelList) {
                        daoManager.getDaoSession().insertOrReplace(model);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 修改
     *
     * @return
     */
    public boolean updateDB(AssetsElementModel model) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(model);
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param
     * @return
     */
    public boolean deleteDB(AssetsElementModel model) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(model);
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 查询单条
     *
     * @param key
     * @return
     */
    public AssetsElementModel queryData(long key) {
        return daoManager.getDaoSession().load(AssetsElementModel.class, key);
    }

    /**
     * 全部查询
     *
     * @return
     */
    public List<AssetsElementModel> queeyAll() {
        return daoManager.getDaoSession().loadAll(AssetsElementModel.class);
    }

    /**
     * 原生查询
     */
    public List<AssetsElementModel> queryNative(String sql) {
        //使用sql进行查询
        List<AssetsElementModel> list = daoManager.getDaoSession().queryRaw(AssetsElementModel.class, sql,
                new String[]{"%l%", "6"});
        return list;
    }

    /**
     * 查询首页数据
     *
     * @return
     */
    public List<AssetsElementModel> contents() {
        List<AssetsElementModel> list;
        list = CommonUtils.getInstance().queeyAll();
        Collections.reverse(list);
        return list;
    }

}
