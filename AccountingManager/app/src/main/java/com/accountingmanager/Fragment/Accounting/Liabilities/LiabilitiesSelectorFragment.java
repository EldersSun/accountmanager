package com.accountingmanager.Fragment.Accounting.Liabilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Accounting.Liabilities.Mortgage.MortgageHomeSelectorFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 贷款/欠款 选择页面
 * Created by Home-Pc on 2017/4/27.
 */

public class LiabilitiesSelectorFragment extends ContentBaseFragment implements UiContentView.OnContentClick {
    private UiContentView lia_selector_Arrears, lia_selector_loan, lia_selector_Mortgage, lia_selector_CarLoan;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_liabilities_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        lia_selector_Arrears = (UiContentView) fgView.findViewById(R.id.lia_selector_Arrears);
        lia_selector_loan = (UiContentView) fgView.findViewById(R.id.lia_selector_loan);
        lia_selector_Mortgage = (UiContentView) fgView.findViewById(R.id.lia_selector_Mortgage);
        lia_selector_CarLoan = (UiContentView) fgView.findViewById(R.id.lia_selector_CarLoan);
    }

    @Override
    protected void initEvent() {
        lia_selector_Arrears.setOnContentClick(this);
        lia_selector_loan.setOnContentClick(this);
        lia_selector_Mortgage.setOnContentClick(this);
        lia_selector_CarLoan.setOnContentClick(this);

        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_5);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }
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
    public void onContentClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.lia_selector_Arrears://欠款
                assetsElementModel.setMenuName(lia_selector_Arrears.getTitleVaule());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                ServiceBaseActivity.startActivity(getActivity(), ArrearsInputFragment.class.getName(), bundle);
                break;
            case R.id.lia_selector_loan://消费贷款
                assetsElementModel.setMenuName(lia_selector_loan.getTitleVaule());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                ServiceBaseActivity.startActivity(getActivity(), ArrearsInputFragment.class.getName(), bundle);
                break;
            case R.id.lia_selector_Mortgage://房贷
                assetsElementModel.setMenuName(lia_selector_Mortgage.getTitleVaule());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                ServiceBaseActivity.startActivity(getActivity(), MortgageHomeSelectorFragment.class.getName(), bundle);
                break;
            case R.id.lia_selector_CarLoan://车贷
                assetsElementModel.setMenuName(lia_selector_CarLoan.getTitleVaule());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                ServiceBaseActivity.startActivity(getActivity(), ArrearsInputFragment.class.getName(), bundle);
                break;
        }
    }

    @Override
    public void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if(msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_5)){
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3,msgObject);
            getActivity().finish();
        }
    }
}
