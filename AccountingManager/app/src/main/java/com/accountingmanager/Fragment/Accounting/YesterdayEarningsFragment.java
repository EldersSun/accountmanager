package com.accountingmanager.Fragment.Accounting;

import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;

/**
 * 昨天收益页面
 * Created by Home-Pc on 2017/4/26.
 */

public class YesterdayEarningsFragment extends ContentBaseFragment {

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_yesterday_earnings_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {

    }

    @Override
    protected void initEvent() {
        setTitleWhiteStyle("昨天收益");
    }

    @Override
    public void onResponsSuccess(int TAG, Object result) {

    }

    @Override
    public void onResponsFailed(int TAG, String result) {

    }

    @Override
    public void onNetConnectFailed(int TAG, String result) {

    }
}
