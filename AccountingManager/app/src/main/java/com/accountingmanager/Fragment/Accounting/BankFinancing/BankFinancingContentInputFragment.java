package com.accountingmanager.Fragment.Accounting.BankFinancing;

import android.os.Bundle;
import android.text.InputFilter;
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
import com.accountingmanager.Sys.Utils.TimeUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.SelectorTimeDialog;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;
import com.alibaba.fastjson.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 银行理财内容输入界面
 * Created by Home-Pc on 2017/5/15.
 */

public class BankFinancingContentInputFragment extends ContentBaseFragment implements View.OnClickListener,UiContentView.OnContentClick{

    private UiContentView bank_financing_input_number,bank_financing_input_rate,bank_financing_input_startTime,bank_financing_input_time;
    private Button bank_financing_input_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    private SelectorTimeDialog selectorTimeDialog;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_bankfinancing_content_input_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        bank_financing_input_number = (UiContentView) fgView.findViewById(R.id.bank_financing_input_number);
        bank_financing_input_rate = (UiContentView) fgView.findViewById(R.id.bank_financing_input_rate);
        bank_financing_input_startTime = (UiContentView) fgView.findViewById(R.id.bank_financing_input_startTime);
        bank_financing_input_time = (UiContentView) fgView.findViewById(R.id.bank_financing_input_time);
        bank_financing_input_submit = (Button) fgView.findViewById(R.id.bank_financing_input_submit);
    }

    @Override
    protected void initEvent() {
        bank_financing_input_startTime.setOnContentClick(this);
        bank_financing_input_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        bank_financing_input_number.setContentFilers(filters);
        bank_financing_input_rate.setContentFilers(filters);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }


        bank_financing_input_startTime.setContentVaule(TimeUtils.dateToYMD(Calendar.getInstance().getTime()));

        selectorTimeDialog = new SelectorTimeDialog(getActivity());

        selectorTimeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                bank_financing_input_startTime.setContentVaule(TimeUtils.dateToYMD(calendar.getTime()));
            }

            @Override
            public void cancel() {

            }
        });

        bank_financing_input_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
        bank_financing_input_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        bank_financing_input_time.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.day)));
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
            case R.id.bank_financing_input_submit:
                if (StringUtils.isBlank(bank_financing_input_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.fixed_deposit_input_msg_7));
                    return;
                }

                if (StringUtils.isBlank(bank_financing_input_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.fixed_deposit_input_msg_8));
                    return;
                }

                if (StringUtils.isBlank(bank_financing_input_startTime.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.bank_financing_input_msg_5));
                    return;
                }

                if (StringUtils.isBlank(bank_financing_input_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.bank_financing_input_msg_6));
                    return;
                }
                assetsElementModel.setAmount(bank_financing_input_number.getContentVaule());

                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.bank_financing_input_msg_3), bank_financing_input_startTime.getContentVaule());
                map.put(getString(R.string.bank_financing_input_msg_2), bank_financing_input_rate.getContentVaule());
                map.put(getString(R.string.bank_financing_input_msg_4), bank_financing_input_time.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());

                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_11, assetsElementModel);
                getActivity().finish();

                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()){
            case R.id.bank_financing_input_startTime:
                selectorTimeDialog.show();
                break;
        }
    }
}
