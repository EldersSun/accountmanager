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
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * P2P 网贷--活期
 * Created by Home-Pc on 2017/5/8.
 */

public class NetLoanCurrentInputFragment extends BaseFragment implements View.OnClickListener{
    private UiContentView netLoan_current_number,netLoan_current_rate;
    private Button  netLoan_current_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_netloan_current_input_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        netLoan_current_number = (UiContentView) fgView.findViewById(R.id.netLoan_current_number);
        netLoan_current_rate = (UiContentView) fgView.findViewById(R.id.netLoan_current_rate);
        netLoan_current_submit = (Button) fgView.findViewById(R.id.netLoan_current_submit);
    }

    @Override
    protected void initEvent() {
        netLoan_current_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        netLoan_current_number.setContentFilers(filters);
        netLoan_current_rate.setContentFilers(filters);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        netLoan_current_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        netLoan_current_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
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
            case R.id.netLoan_current_submit:
                if(StringUtils.isBlank(netLoan_current_number.getContentVaule())){
                    ToastUtils.shortShow(R.string.simple_msg_2);
                    return;
                }
                if(StringUtils.isBlank(netLoan_current_rate.getContentVaule())){
                    ToastUtils.shortShow(R.string.simple_msg_3);
                    return;
                }
                assetsElementModel.setAmount(netLoan_current_number.getContentVaule());
                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.rate), netLoan_current_rate.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_9, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
