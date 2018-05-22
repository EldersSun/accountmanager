package com.accountingmanager.Fragment.Accounting.Balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Accounting.BankInputContentFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 账户余额--选择账户界面
 * Created by Home-Pc on 2017/4/27.
 */

public class BalanceSeletorFragment extends ContentBaseFragment implements UiContentView.OnContentClick {
    private UiContentView balance_selector_aliPay, balance_selector_weChat, balance_selector_more;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_balance_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        balance_selector_aliPay = (UiContentView) fgView.findViewById(R.id.balance_selector_aliPay);
        balance_selector_weChat = (UiContentView) fgView.findViewById(R.id.balance_selector_weChat);
        balance_selector_more = (UiContentView) fgView.findViewById(R.id.balance_selector_more);
    }

    @Override
    protected void initEvent() {
        balance_selector_aliPay.setOnContentClick(this);
        balance_selector_weChat.setOnContentClick(this);
        balance_selector_more.setOnContentClick(this);

        setTitleWhiteStyle(getString(R.string.selectorAccount));
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_4);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
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
        switch (v.getId()) {
            case R.id.balance_selector_aliPay:
                assetsElementModel.setMenuName(getString(R.string.aliPay));
                assetsElementModel.setMenuIcon(R.drawable.icon_alipay);
                break;
            case R.id.balance_selector_weChat:
                assetsElementModel.setMenuName(getString(R.string.weChat));
                assetsElementModel.setMenuIcon(R.drawable.icon_wechat);
                break;
            case R.id.balance_selector_more:
                assetsElementModel.setMenuName(getString(R.string.moreAccount));
                assetsElementModel.setMenuIcon(R.drawable.icon_more);
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
        ServiceBaseActivity.startActivity(getActivity(), BalanceInputFragment.class.getName(),bundle);
    }

    @Override
    public void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if(msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_4)){
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3,msgObject);
            getActivity().finish();
        }
    }
}
