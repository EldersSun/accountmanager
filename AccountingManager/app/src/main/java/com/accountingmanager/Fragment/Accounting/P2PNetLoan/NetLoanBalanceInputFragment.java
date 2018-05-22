package com.accountingmanager.Fragment.Accounting.P2PNetLoan;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * P2P -- 账户余额
 * Created by Home-Pc on 2017/5/8.
 */

public class NetLoanBalanceInputFragment extends BaseFragment implements View.OnClickListener{
    private UiContentView NetLoan_Balance_Num;
    private Button NetLoan_Balance_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();


    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_netloan_balance_input_laytou,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        NetLoan_Balance_Num = (UiContentView) fgView.findViewById(R.id.NetLoan_Balance_Num);
        NetLoan_Balance_submit = (Button) fgView.findViewById(R.id.NetLoan_Balance_submit);
    }

    @Override
    protected void initEvent() {
        NetLoan_Balance_submit.setOnClickListener(this);


        InputFilter[] filters = {new CashierInputFilter()};
        NetLoan_Balance_Num.setContentFilers(filters);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        NetLoan_Balance_Num.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.NetLoan_Balance_submit:
                if(StringUtils.isBlank(NetLoan_Balance_Num.getContentVaule())){
                    ToastUtils.shortShow(R.string.simple_msg_2);
                    return;
                }
                assetsElementModel.setAmount(NetLoan_Balance_Num.getContentVaule());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_9, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
