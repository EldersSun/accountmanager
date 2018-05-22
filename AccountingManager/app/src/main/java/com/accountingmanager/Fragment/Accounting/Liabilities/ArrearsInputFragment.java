package com.accountingmanager.Fragment.Accounting.Liabilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;

/**
 * 贷款页面
 * Created by Home-Pc on 2017/4/28.
 */

public class ArrearsInputFragment extends ContentBaseFragment implements View.OnClickListener {
    private RadioButton arrears_input_simple, arrears_input_senior;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_arrearsinput_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        arrears_input_simple = (RadioButton) fgView.findViewById(R.id.arrears_input_simple);
        arrears_input_senior = (RadioButton) fgView.findViewById(R.id.arrears_input_senior);
    }

    @Override
    protected void initEvent() {
        arrears_input_simple.setOnClickListener(this);
        arrears_input_senior.setOnClickListener(this);
        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }
        pushFragmentController(R.id.arrears_input_content, new ArrearsSimpleFragment(), bundle, false);
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
            case R.id.arrears_input_simple://简单
                pushFragmentController(R.id.arrears_input_content, new ArrearsSimpleFragment(), bundle, false);
                break;
            case R.id.arrears_input_senior://高级
                pushFragmentController(R.id.arrears_input_content, new ArrearsSeniorFragment(), bundle, false);
                break;
        }
    }
}
