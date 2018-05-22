package com.accountingmanager.Fragment.Assets;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;

/**
 * 资产首页
 * Created by Home-Pc on 2017/5/23.
 */

public class AssetsHomeFragment extends BaseFragment implements View.OnClickListener {

    private RadioButton Assets_Home_Analysis, Assets_Home_Course;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_assetshome_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Assets_Home_Analysis = (RadioButton) fgView.findViewById(R.id.Assets_Home_Analysis);
        Assets_Home_Course = (RadioButton) fgView.findViewById(R.id.Assets_Home_Course);
    }

    @Override
    protected void initEvent() {
        Assets_Home_Analysis.setOnClickListener(this);
        Assets_Home_Course.setOnClickListener(this);

        pushFragmentController(R.id.Assets_Home_content, new AssetsAnalysisFragment(), null, false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Assets_Home_Analysis:
                pushFragmentController(R.id.Assets_Home_content, new AssetsAnalysisFragment(), null, false);
                break;
            case R.id.Assets_Home_Course:
                pushFragmentController(R.id.Assets_Home_content, new AssetsHomeCourseFragment(), null, false);
                break;
        }
    }
}
