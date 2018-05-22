package com.accountingmanager.Fragment.Accounting.P2PNetLoan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;

/**
 * P2P 手动输入主界面
 * Created by Home-Pc on 2017/5/4.
 */

public class NetLoanInputHomeFragment extends ContentBaseFragment implements View.OnClickListener{

    private RadioButton netLoan_input_home_regular,netLoan_input_home_current,netLoan_input_home_balance;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;
    private String title = "";

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_netloan_input_home_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        netLoan_input_home_regular = (RadioButton) fgView.findViewById(R.id.netLoan_input_home_regular);
        netLoan_input_home_current = (RadioButton) fgView.findViewById(R.id.netLoan_input_home_current);
        netLoan_input_home_balance = (RadioButton) fgView.findViewById(R.id.netLoan_input_home_balance);
    }

    @Override
    protected void initEvent() {
        netLoan_input_home_regular.setOnClickListener(this);
        netLoan_input_home_current.setOnClickListener(this);
        netLoan_input_home_balance.setOnClickListener(this);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            title = assetsElementModel.getMenuName();
            setTitleWhiteStyle(title);
        }
        assetsElementModel.setMenuName(title + AppConfig.getInstance().NAME_PART_LINE + netLoan_input_home_regular.getText().toString());
        bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
        pushFragmentController(R.id.netLoan_input_home_content,new NetLoanRegularInputFragment(),bundle,false);

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
            case R.id.netLoan_input_home_regular:
                assetsElementModel.setMenuName(title + AppConfig.getInstance().NAME_PART_LINE + netLoan_input_home_regular.getText().toString());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                pushFragmentController(R.id.netLoan_input_home_content,new NetLoanRegularInputFragment(),bundle,false);
                break;
            case R.id.netLoan_input_home_current:
                assetsElementModel.setMenuName(title + AppConfig.getInstance().NAME_PART_LINE + netLoan_input_home_current.getText().toString());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                pushFragmentController(R.id.netLoan_input_home_content,new NetLoanCurrentInputFragment(),bundle,false);
                break;
            case R.id.netLoan_input_home_balance:
                assetsElementModel.setMenuName(title + AppConfig.getInstance().NAME_PART_LINE + netLoan_input_home_balance.getText().toString());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                pushFragmentController(R.id.netLoan_input_home_content,new NetLoanBalanceInputFragment(),bundle,false);
                break;
        }
    }
}
