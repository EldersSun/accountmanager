package com.accountingmanager.Remote;

public interface IRemoteResponse {

	/** 请求成功 */
	public abstract void onResponsSuccess(int TAG, Object result);

	/** 请求失败 */
	public abstract void onResponsFailed(int TAG, String result);

	/** 网络连接失败 */
	public abstract void onNetConnectFailed(int TAG, String result);

}
