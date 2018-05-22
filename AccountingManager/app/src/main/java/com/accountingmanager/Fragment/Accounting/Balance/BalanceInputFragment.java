package com.accountingmanager.Fragment.Accounting.Balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 账户余额输入界面
 * Created by Home-Pc on 2017/4/27.
 */

public class BalanceInputFragment extends ContentBaseFragment implements View.OnClickListener {
    private UiContentView account_Balance_name,account_Balance_input;
    private Button account_Balance_submit;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_balance_input_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        account_Balance_name = (UiContentView) fgView.findViewById(R.id.account_Balance_name);
        account_Balance_input = (UiContentView) fgView.findViewById(R.id.account_Balance_input);
        account_Balance_submit = (Button) fgView.findViewById(R.id.account_Balance_submit);
    }

    @Override
    protected void initEvent() {
        account_Balance_submit.setOnClickListener(this);
        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
            if(assetsElementModel.getMenuName().equals(getString(R.string.moreAccount))){
                account_Balance_name.setVisibility(View.VISIBLE);
            } else {
                account_Balance_name.setVisibility(View.GONE);
            }
        }

        account_Balance_input.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.account_Balance_submit:
                if (StringUtils.isBlank(account_Balance_input.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.AddInputContent_MSG_1));
                    return;
                }
                assetsElementModel.setAmount(account_Balance_input.getContentVaule());
                if(!StringUtils.isBlank(account_Balance_name.getContentVaule())){
                    assetsElementModel.setMenuName(account_Balance_name.getContentVaule());
                }
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_4, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
