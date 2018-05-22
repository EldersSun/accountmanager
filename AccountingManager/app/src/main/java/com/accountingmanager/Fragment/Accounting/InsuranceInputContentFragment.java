package com.accountingmanager.Fragment.Accounting;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.ArrayUtils;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.TimeUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.CustomSelectorDialog;
import com.accountingmanager.Sys.Widgets.SelectorTimeDialog;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;
import com.alibaba.fastjson.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保险理财内容输入页面
 * Created by Home-Pc on 2017/5/15.
 */

public class InsuranceInputContentFragment extends ContentBaseFragment implements View.OnClickListener, UiContentView.OnContentClick {
    private UiContentView insurance_financing_name, insurance_financing_number, insurance_financing_rate,
            insurance_financing_time, insurance_financing_startTime, insurance_financing_mode;
    private RadioButton insurance_financing_month, insurance_financing_day;
    private Button insurance_financing_submit;

    private SelectorTimeDialog selectorTimeDialog;
    private CustomSelectorDialog customSelectorDialog;

    private String timeType = AppConfig.getInstance().MONTH;


    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_insurance_financing_input_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        insurance_financing_name = (UiContentView) fgView.findViewById(R.id.insurance_financing_name);
        insurance_financing_number = (UiContentView) fgView.findViewById(R.id.insurance_financing_number);
        insurance_financing_rate = (UiContentView) fgView.findViewById(R.id.insurance_financing_rate);
        insurance_financing_time = (UiContentView) fgView.findViewById(R.id.insurance_financing_time);
        insurance_financing_startTime = (UiContentView) fgView.findViewById(R.id.insurance_financing_startTime);
        insurance_financing_mode = (UiContentView) fgView.findViewById(R.id.insurance_financing_mode);
        insurance_financing_month = (RadioButton) fgView.findViewById(R.id.insurance_financing_month);
        insurance_financing_day = (RadioButton) fgView.findViewById(R.id.insurance_financing_day);
        insurance_financing_submit = (Button) fgView.findViewById(R.id.insurance_financing_submit);
    }

    @Override
    protected void initEvent() {
        insurance_financing_startTime.setOnContentClick(this);
        insurance_financing_mode.setOnContentClick(this);

        insurance_financing_month.setOnClickListener(this);
        insurance_financing_day.setOnClickListener(this);
        insurance_financing_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        insurance_financing_number.setContentFilers(filters);
        insurance_financing_rate.setContentFilers(filters);


        insurance_financing_startTime.setContentVaule(TimeUtils.dateToYMD(Calendar.getInstance().getTime()));
        selectorTimeDialog = new SelectorTimeDialog(getActivity());
        selectorTimeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                insurance_financing_startTime.setContentVaule(TimeUtils.dateToYMD(calendar.getTime()));
            }

            @Override
            public void cancel() {

            }
        });
        List<String> list = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.SeniorSelectorType));
        customSelectorDialog = new CustomSelectorDialog(getActivity(), getString(R.string.regular_msg_9), list);
        customSelectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                insurance_financing_mode.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });


        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        insurance_financing_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        insurance_financing_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));

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
        switch (v.getId()) {
            case R.id.insurance_financing_submit:
                if (StringUtils.isBlank(insurance_financing_name.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.insurance_financing_msg_7));
                    return;
                }
                if (StringUtils.isBlank(insurance_financing_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.insurance_financing_msg_8));
                    return;
                }
                if (StringUtils.isBlank(insurance_financing_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.insurance_financing_msg_9));
                    return;
                }
                if (StringUtils.isBlank(insurance_financing_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.insurance_financing_msg_10));
                    return;
                }
                if (StringUtils.isBlank(insurance_financing_mode.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.insurance_financing_msg_11));
                    return;
                }

                assetsElementModel.setMenuName(insurance_financing_name.getContentVaule());
                assetsElementModel.setAmount(insurance_financing_number.getContentVaule());
                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.insurance_financing_msg_12), insurance_financing_rate.getContentVaule());
                map.put(getString(R.string.insurance_financing_msg_13), insurance_financing_time.getContentVaule() + timeType);
                map.put(getString(R.string.insurance_financing_msg_14), insurance_financing_startTime.getContentVaule());
                map.put(getString(R.string.insurance_financing_msg_15), insurance_financing_mode.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, assetsElementModel);
                getActivity().finish();
                break;
            case R.id.insurance_financing_day:
                timeType = getString(R.string.day);
                break;
            case R.id.insurance_financing_month:
                timeType = getString(R.string.month);
                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()) {
            case R.id.insurance_financing_startTime:
                selectorTimeDialog.show();
                break;
            case R.id.insurance_financing_mode:
                customSelectorDialog.show();
                break;
        }
    }
}
