package com.accountingmanager.Fragment.Accounting.Liabilities.Mortgage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;

/**房贷
 * Created by Home-Pc on 2017/5/2.
 */
public class MortgageHomeSelectorFragment extends ContentBaseFragment implements View.OnClickListener{
    private RadioButton mortgage_input_business,mortgage_input_fund,mortgage_input_combination;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_mortgage_home_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        mortgage_input_business = (RadioButton) fgView.findViewById(R.id.mortgage_input_business);
        mortgage_input_fund = (RadioButton) fgView.findViewById(R.id.mortgage_input_fund);
        mortgage_input_combination = (RadioButton) fgView.findViewById(R.id.mortgage_input_combination);
    }

    @Override
    protected void initEvent() {
        mortgage_input_business.setOnClickListener(this);
        mortgage_input_fund.setOnClickListener(this);
        mortgage_input_combination.setOnClickListener(this);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }
        pushFragmentController(R.id.mortgage_input_content,new BusinessLoanFragment(),bundle,false);
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
            case R.id.mortgage_input_business:
                bundle.putString(AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG,AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG_1);
                pushFragmentController(R.id.mortgage_input_content,new BusinessLoanFragment(),bundle,false);
                break;
            case R.id.mortgage_input_fund:
                bundle.putString(AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG,AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG_2);
                pushFragmentController(R.id.mortgage_input_content,new BusinessLoanFragment(),bundle,false);
                break;
            case R.id.mortgage_input_combination:
                pushFragmentController(R.id.mortgage_input_content,new CombinationLoanFragment(),bundle,false);
                break;
        }
    }
}
