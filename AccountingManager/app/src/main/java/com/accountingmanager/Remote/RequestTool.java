package com.accountingmanager.Remote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.accountingmanager.R;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Utils.AdrToolkit;
import com.accountingmanager.Sys.Utils.JsonUtil.XCJsonUtil;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求工具类
 *
 * @author Home_Pc
 */
@SuppressLint("HandlerLeak")
public class RequestTool {

    /**
     * 返回结果
     */
    private IRemoteResponse mIRemoteResponse;

    private final int MESSAGE_WHAT_SUCC_1 = 0;
    private final int MESSAGE_WHAT_ERROE_1 = 1;
    private final int MESSAGE_WHAT_ERROE_2 = 2;
    private int TAG = -1;
    private Context mContext;

    public final int CONNECT_TIMEOUT = 60;
    public final int READ_TIMEOUT = 100;
    public final int WRITE_TIMEOUT = 60;

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    /**
     * 请求时使用的线程池
     * corePoolSize- 池中所保存的线程数，包括空闲线程。需要注意的是在初创建线程池时线程不会立即启动，直到有任务提交才开始启动线程并逐渐时线程数目达到corePoolSize。
     * 若想一开始就创建所有核心线程需调用prestartAllCoreThreads方法。
     * maximumPoolSize-池中允许的最大线程数。需要注意的是当核心线程满且阻塞队列也满时才会判断当前线程数是否小于最大线程数，并决定是否创建新线程。
     * keepAliveTime - 当线程数大于核心时，多于的空闲线程最多存活时间
     * unit - keepAliveTime 参数的时间单位。
     * workQueue - 当线程数目超过核心线程数时用于保存任务的队列。主要有3种类型的BlockingQueue可供选择：无界队列，有界队列和同步移交。
     * threadFactory - 执行程序创建新线程时使用的工厂。
     * handler - 阻塞队列已满且线程数达到最大值时所采取的饱和策略。
     */
    private static ExecutorService pool = new ThreadPoolExecutor(0, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    public RequestTool(IRemoteResponse mIRemoteResponse) {
        this.mIRemoteResponse = mIRemoteResponse;
    }

    /**
     * 正确消息
     */
    private Handler succHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIRemoteResponse.onResponsSuccess(TAG, msg.obj);
        }
    };

    /**
     * 错误
     */
    private Handler errorHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIRemoteResponse.onResponsFailed(TAG, String.valueOf(msg.obj));
        }
    };

    /**
     * @param context
     * @param tag
     * @param url
     * @param map
     * @param isEtc
     * @return
     */
    public void requestForHttp(Context context, final int tag, final String url, final Map<String, Object> map, final Boolean isEtc) {
        this.TAG = tag;
        this.mContext = context;
        try {
            ArrayList<Callable<Integer>> callers = new ArrayList<>();
            if (context == null) {
                errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                return;
            }
            if (!NetWorkUtil.isNetworkConnected(context)) {
//                Toast.makeText(context, R.string.netWorkErr, Toast.LENGTH_SHORT).show();
                mIRemoteResponse.onNetConnectFailed(tag, context.getResources().getString(R.string.netWorkErr));
                return;
            }
            if (StringUtils.isBlank(url)) {
                return;
            }
            String imei = AdrToolkit.getIMEI(context);
            if (StringUtils.isBlank(imei)) {
                ToastUtils.shortShow(R.string.netWorkErr_1);
                return;
            }

            map.put("source", "Android");
            map.put("deviceId", imei);
            map.put("uuid", UUID.randomUUID().toString());

            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    requestForHttpforJson(AppConfig.getInstance().appUrl + url, map);
                    return null;
                }
            };
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        requestForHttpforJson(AppConfig.getInstance().appUrl + url, map);
                    } catch (IOException e) {
                        errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                    }
                }
            };
            //是否等待
            if (isEtc) {
                callers.add(callable);
                pool.invokeAll(callers);
            } else {
                pool.execute(runnable);
            }
        } catch (InterruptedException e) {
            errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
        }
    }

    public void requestForHttpFile(Context context, final int tag, final String url,
                                   final Map<String, File> fileMap, final Map<String, String> map, final Boolean isEtc) {
        this.TAG = tag;
        this.mContext = context;
        try {
            ArrayList<Callable<Integer>> callers = new ArrayList<>();
            if (context == null) {
                errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                return;
            }
            if (!NetWorkUtil.isNetworkConnected(context)) {
//                Toast.makeText(context, R.string.netWorkErr, Toast.LENGTH_SHORT).show();
                mIRemoteResponse.onNetConnectFailed(tag, context.getResources().getString(R.string.netWorkErr));
                return;
            }
            if (StringUtils.isBlank(url)) {
                return;
            }
            String imei = AdrToolkit.getIMEI(context);
            if (StringUtils.isBlank(imei)) {
                ToastUtils.shortShow(R.string.netWorkErr_1);
                return;
            }

            map.put("source", "Android");
            map.put("deviceId", imei);
            map.put("uuid", UUID.randomUUID().toString());

            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    upLoadFile(AppConfig.getInstance().appUrl + url, fileMap, map);
                    return null;
                }
            };
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    upLoadFile(AppConfig.getInstance().appUrl + url, fileMap, map);
                }
            };
            //是否等待
            if (isEtc) {
                callers.add(callable);
                pool.invokeAll(callers);
            } else {
                pool.execute(runnable);
            }
        } catch (InterruptedException e) {
            errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
        }
    }

    /**
     * 通过url获取数据
     *
     * @param context
     * @param tag
     * @param url
     * @param isEtc
     */
    public void requestUrlForData(Context context, final int tag, final String url, final Boolean isEtc) {
        this.TAG = tag;
        this.mContext = context;
        try {
            ArrayList<Callable<Integer>> callers = new ArrayList<>();
            if (context == null) {
                errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                return;
            }
            if (!NetWorkUtil.isNetworkConnected(context)) {
//                Toast.makeText(context, R.string.netWorkErr, Toast.LENGTH_SHORT).show();
                mIRemoteResponse.onNetConnectFailed(tag, context.getResources().getString(R.string.netWorkErr));
                return;
            }
            if (StringUtils.isBlank(url)) {
                return;
            }
            String imei = AdrToolkit.getIMEI(context);
            if (StringUtils.isBlank(imei)) {
                ToastUtils.shortShow(R.string.netWorkErr_1);
                return;
            }

            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    requestGetByUrl(url);
                    return null;
                }
            };
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    requestGetByUrl(url);
                }
            };
            //是否等待
            if (isEtc) {
                callers.add(callable);
                pool.invokeAll(callers);
            } else {
                pool.execute(runnable);
            }
        } catch (InterruptedException e) {
            errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
        }
    }

    private void requestForHttpforJson(final String url, final Map<String, Object> map) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, JSONObject.toJSONString(map));
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求连接超时
                if (e != null && e.getCause() != null && e.getCause().equals(SocketTimeoutException.class)) {
                    client.newCall(call.request()).enqueue(this);
                } else {
                    Message msg = new Message();
                    msg.what = MESSAGE_WHAT_ERROE_1;
                    msg.obj = R.string.newwork_timeout;
                    errorHandle.sendMessage(msg);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(response);
            }
        });
    }

    /**
     * 文件提交
     */
    private void upLoadFile(String url, Map<String, File> fileMap, Map<String, String> map) {
        RequestBody requestBody;
        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(AppConfig.getInstance().upDateType), entry.getValue());
            multipartBody.addFormDataPart(entry.getKey(), entry.getKey(), fileBody);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            multipartBody.addFormDataPart(entry.getKey(), entry.getValue());
        }

        requestBody = multipartBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //请求连接超时
                if (e != null && e.getCause() != null && e.getCause().equals(SocketTimeoutException.class)) {
                    client.newCall(call.request()).enqueue(this);
                } else {
                    Message msg = new Message();
                    msg.what = MESSAGE_WHAT_ERROE_1;
                    msg.obj = R.string.newwork_timeout;
                    errorHandle.sendMessage(msg);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                handleResponse(response);
            }
        });
    }

    /**
     * 通过url获取文件
     */
    private void requestGetByUrl(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //创建一个Call
        final Call call = client.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求连接超时
                if (e != null && e.getCause() != null && e.getCause().equals(SocketTimeoutException.class)) {
                    client.newCall(call.request()).enqueue(this);
                } else {
                    Message msg = new Message();
                    msg.what = MESSAGE_WHAT_ERROE_1;
                    msg.obj = R.string.newwork_timeout;
                    errorHandle.sendMessage(msg);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Map<String, Object> getResultMap = XCJsonUtil.json2Map(new org.json.JSONObject(result));
                        Message msg = new Message();
                        msg.what = MESSAGE_WHAT_SUCC_1;
                        msg.obj = getResultMap;
                        succHandle.sendMessage(msg);
                    } catch (JSONException e) {
                        errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                    }
                } else {
                    errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                }
            }
        });
    }


    /**
     * 处理网络请求成功后的信息
     *
     * @param response
     */
    private void handleResponse(Response response) {
        try {
            String result = response.body().string();
            Map<String, Object> getResultMap = XCJsonUtil.json2Map(new org.json.JSONObject(result));

            if (getResultMap == null) {
                errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                return;
            }
            String resultCode = (String) getResultMap.get(AppConfig.getInstance().RESULT_CODE);
            if (resultCode == null) {
                errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
                return;
            }

        } catch (Exception e) {
            errorHandle.sendEmptyMessage(MESSAGE_WHAT_ERROE_1);
        }
    }
}
