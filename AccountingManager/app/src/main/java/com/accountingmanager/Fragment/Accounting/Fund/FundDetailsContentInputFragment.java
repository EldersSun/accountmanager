package com.accountingmanager.Fragment.Accounting.Fund;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;

/**
 *基金理财输入界面
 * Created by Home-Pc on 2017/5/9.
 */

public class FundDetailsContentInputFragment extends ContentBaseFragment implements View.OnClickListener {

    private RadioButton fund_details_content_input_simple,fund_details_content_input_senior;
    private TextView fund_details_msg;

    private AssetsElementModel model = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_funddetails_content_input_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        fund_details_content_input_simple = (RadioButton) fgView.findViewById(R.id.fund_details_content_input_simple);
        fund_details_content_input_senior = (RadioButton) fgView.findViewById(R.id.fund_details_content_input_senior);
        fund_details_msg = (TextView) fgView.findViewById(R.id.fund_details_msg);

    }

    @Override
    protected void initEvent() {

        fund_details_content_input_simple.setOnClickListener(this);
        fund_details_content_input_senior.setOnClickListener(this);
        fund_details_msg.setOnClickListener(this);

        bundle = getArguments();

        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(model.getMenuName() == null ? "" : model.getMenuName());
        }

        pushFragmentController(R.id.fund_details_content,new FundDetailsSimpleFragmengt(),bundle,false);

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
        switch (v.getId()){
            case R.id.fund_details_content_input_simple:
                pushFragmentController(R.id.fund_details_content,new FundDetailsSimpleFragmengt(),bundle,false);
                break;
            case R.id.fund_details_content_input_senior:
                pushFragmentController(R.id.fund_details_content,new FundDteailsSeniorFragment(),bundle,false);
                break;
            case R.id.fund_details_msg:
                break;
        }
    }
}
