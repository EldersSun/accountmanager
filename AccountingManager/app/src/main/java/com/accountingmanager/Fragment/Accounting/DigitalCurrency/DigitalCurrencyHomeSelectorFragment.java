package com.accountingmanager.Fragment.Accounting.DigitalCurrency;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 数字货币首页选择界面
 * Created by Home-Pc on 2017/5/16.
 */

public class DigitalCurrencyHomeSelectorFragment extends ContentBaseFragment implements UiContentView.OnContentClick {

    private UiContentView digital_home_Bitcoin, digital_home_WrightCoin;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_digital_home_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        digital_home_Bitcoin = (UiContentView) fgView.findViewById(R.id.digital_home_Bitcoin);
        digital_home_WrightCoin = (UiContentView) fgView.findViewById(R.id.digital_home_WrightCoin);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_15);
        digital_home_Bitcoin.setOnContentClick(this);
        digital_home_WrightCoin.setOnContentClick(this);

        bundle = getArguments();
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
        switch (v.getId()) {
            case R.id.digital_home_Bitcoin:
                assetsElementModel.setMenuName(digital_home_Bitcoin.getTitleVaule());
                assetsElementModel.setMenuIcon(R.drawable.icon_bitcoin);
                break;
            case R.id.digital_home_WrightCoin:
                assetsElementModel.setMenuName(digital_home_WrightCoin.getTitleVaule());
                assetsElementModel.setMenuIcon(R.drawable.icon_wrightcoin);
                break;
        }
        bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
        ServiceBaseActivity.startActivity(getActivity(), DigitalCurrencyContentInputFragment.class.getName(), bundle);
    }

    @Override
    protected void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if (msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_15)) {
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, msgObject);
            getActivity().finish();
        }
    }
}
