package com.accountingmanager.Sys.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.accountingmanager.Application.AccountApplication;
import com.accountingmanager.Remote.IRemoteResponse;
import com.accountingmanager.Remote.RequestTool;
import com.accountingmanager.Sys.Shared.SharedPreferencesUtil;
import com.accountingmanager.Sys.Utils.StringUtils;

/**
 * 程序配置文件
 * Created by Home_Pc on 2017/3/10.
 */
public class AppConfig {

    private static AppConfig instance;

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                instance = new AppConfig();
            }
        }
        return instance;
    }

    /******************************************* 常量 *******************************************/
    public final String DAY = "天";
    public final String MONTH = "月";
    public final String YEAR = "年";

    /******************************************* 系统 *******************************************/
    /**
     * 总倒计时 时间 一分钟
     */
    public final int millisInFuture = 1000 * 60;
    /**
     * 倒计时的步进
     */
    public final int countDownInterval = 1000;

    /**
     * 文件上传类型
     */
    public final String upDateType = "application/octet-stream";

    /**
     * 清空数据
     */
    public void clearUserData() {

    }


    /**
     * 清空用户磁盘文件
     */
    public void clearUserFile() {
        AccountApplication.getInstance().clearLruCache();
        File imgFile = new File(SysSDCardCacheDir.getImgDir().getPath());
        File videoFile = new File(SysSDCardCacheDir.getVideoDir().getPath());
        if (imgFile.exists()) {
            File[] files = imgFile.listFiles();
            if (files == null) return;
            for (int i = 0; i < files.length; i++) {
                if (files[i].exists()) {
                    files[i].delete();
                }
            }
            imgFile.delete();
        }
        if (videoFile.exists()) {
            File[] files = videoFile.listFiles();
            if (files == null) return;
            for (int i = 0; i < files.length; i++) {
                if (files[i].exists()) {
                    files[i].delete();
                }
            }
            videoFile.delete();
        }
    }

    /******************************************* TAG *******************************************/

    /**
     * 保存首页页面选择状态 tag
     */
    public final String SELECTOR_HOME_PAGE = "selector_home_page";
    /**
     * 页面传递对象tag
     */
    public final String FRAGMENT_OBJECT_TAG = "object_tag";
    /**
     * 公积金tag
     */
    public final String JUMP_SELECTOR_TYPE_PAGE_TAG = "selector_type_page_tag";
    /**
     * 公积金手动选择界面msg tag
     */
    public final String JUMP_SELECTOR_TYPE_CONTENT_TAG = "selector_type_content_tag";
    /**
     * 输入类型选择界面tag
     */
    public final String ADD_ASSETS_SELECTOR_TYPE = "selector_input_type";
    /**
     * 商业贷款的输入类型选择tag
     */
    public final String SELECTOR_LOAN_STATE_TAG = "selector_loan_input_type_State";
    /**
     * 商业贷款的输入类型选择tag1
     */
    public final String SELECTOR_LOAN_STATE_TAG_1 = "selector_loan_input_type_State_1";
    /**
     * 商业贷款的输入类型选择tag2
     */
    public final String SELECTOR_LOAN_STATE_TAG_2 = "selector_loan_input_type_State_2";

    /**
     * P2P 网贷监听输入选择类型（手动/自动）页面tag
     */
    public final String SELECTOR_NETLOAN_HOME_STATE = "selector_NetLoan_state";
    /**
     * 基金理财首页监听输入选择类型（手动/自动） 页面tag
     */
    public final String SELECTOR_MONETARY_HOME_STATE = "selector_monetary_state";
    /**
     * 基金首页监听输入选择类型（手动/自动） 页面tag
     */
    public final String SELECTOR_FUND_HOME_STATE = "selector_fund_state";
    /**
     * 股票首页监听输入选择类型（手动/自动） 页面tag
     */
    public final String SELECTOR_SHARES_HOME_STATE = "selector_shares_state";

    /**
     * 选择银行列表监听输入选择类型（手动/自动） 页面tag
     */
    public final String FRAGMENT_SELECTOR_BANK_TAG = "selector_bankcard_input_type";
    /**
     * 公积金选择输入类型（手动/自动）页面TAG
     */
    public final String SELECTOR_ACCUMULATION_FUND = "selector_accumulation_fund";
    /**
     * 名称分割线tag
     */
    public final String NAME_PART_LINE = " - ";
    /**
     * 外汇首页选择页面向内容页面传递bitmap TAG
     */
    public final String FOREIGN_SELECTOR_CONTENT_TAG = "foreign_selector_content_tag";


    /******************************************* 消息 *******************************************/
    /**
     * 通知首页总数更新
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_1 = "0X9999";
    /**
     * 通知首页视图更新数据
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_2 = "0X9998";
    /**
     * 由添加页面监听，其他二级子菜单发送消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_3 = "0X9997";
    /**
     * 账户余额由输入界面向选择页传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_4 = "0X9996";
    /**
     * 贷款输入界面向选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_5 = "0X9995";
    /**
     * 基金输入界面向选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_6 = "0X9994";
    /**
     * 股票输入界面向选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_7 = "0X9993";
    /**
     * 银行卡输入界面向选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_8 = "0X9992";
    /**
     * p2p 余额账户输入界面向选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_9 = "0X9991";
    /**
     * 理财基金输入页面向选择页传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_10 = "0X9990";
    /**
     * 银行理财输入界面向二级选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_11 = "0X9989";
    /**
     * 银行理财二级选择界面向一级选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_12 = "0X9988";
    /**
     * 国债输入页面向一级选择界面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_13 = "0X9987";
    /**
     * 外汇内容输入页面向一级选择页面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_14 = "0X9986";
    /**
     * 数字货币内容输入页面向一级选择页面传递消息
     */
    public final String ADD_INPUT_GET_CONTENT_TAG_15 = "0X9985";


    /*************************************** 网络请求相关 ***************************************/
    public final String RESULT_CODE = "code";
    public final String RESULT_MSG = "msg";
    public final String appUrl = "";

}
