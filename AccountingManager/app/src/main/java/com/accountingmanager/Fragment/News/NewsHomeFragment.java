package com.accountingmanager.Fragment.News;

import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;

/**
 * Created by Home-Pc on 2017/4/24.
 */

public class NewsHomeFragment extends BaseFragment {
    @Override
    public void onResponsSuccess(int TAG, Object result) {

    }

    @Override
    public void onResponsFailed(int TAG, String result) {

    }

    @Override
    public void onNetConnectFailed(int TAG, String result) {

    }

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_newshome_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {

    }

    @Override
    protected void initEvent() {

    }
}
