package com.accountingmanager.Fragment.Accounting.Liabilities.Mortgage;

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
import com.accountingmanager.Sys.Utils.ArrayUtils;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Sys.Utils.NumberUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.TimeUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.CustomSelectorDialog;
import com.accountingmanager.Sys.Widgets.SelectorTimeDialog;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商业贷款
 * Created by Home-Pc on 2017/5/3.
 */

public class BusinessLoanFragment extends BaseFragment implements UiContentView.OnContentClick, View.OnClickListener {
    private UiContentView Business_name, Business_number, Business_rate, Business_time, Business_start_time,
            Business_time_mode, Business_repayment_mode;
    private Button Senior_Submit, Business_selector_rate;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;
    private String selectorType = "";

    private String startTime = "";//起息时间
    private String timeMode = "";//还款方式

    private SelectorTimeDialog timeDialog;
    private CustomSelectorDialog customSelectorDialog;
    private CustomSelectorDialog selectorDays;
    private CustomSelectorDialog selectorTerm;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_businessloan_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Business_name = (UiContentView) fgView.findViewById(R.id.Business_name);
        Business_number = (UiContentView) fgView.findViewById(R.id.Business_number);
        Business_rate = (UiContentView) fgView.findViewById(R.id.Business_rate);
        Business_time = (UiContentView) fgView.findViewById(R.id.Business_time);
        Business_start_time = (UiContentView) fgView.findViewById(R.id.Business_start_time);
        Business_time_mode = (UiContentView) fgView.findViewById(R.id.Business_time_mode);
        Business_repayment_mode = (UiContentView) fgView.findViewById(R.id.Business_repayment_mode);
        Senior_Submit = (Button) fgView.findViewById(R.id.Senior_Submit);
        Business_selector_rate = (Button) fgView.findViewById(R.id.Business_selector_rate);
    }

    @Override
    protected void initEvent() {
        Senior_Submit.setOnClickListener(this);
        Business_selector_rate.setOnClickListener(this);

        Business_time.setOnContentClick(this);
        Business_start_time.setOnContentClick(this);
        Business_time_mode.setOnContentClick(this);
        Business_repayment_mode.setOnContentClick(this);

        timeDialog = new SelectorTimeDialog(getActivity());

        InputFilter[] filters = {new CashierInputFilter()};
        Business_number.setContentFilers(filters);
        Business_rate.setContentFilers(filters);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        if (bundle.containsKey(AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG) &&
                !StringUtils.isBlank(bundle.getString(AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG))) {
            selectorType = bundle.getString(AppConfig.getInstance().SELECTOR_LOAN_STATE_TAG);
        }

        startTime = TimeUtils.dateToYMD(Calendar.getInstance().getTime());
        Business_start_time.setContentVaule(startTime);

        timeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                startTime = TimeUtils.dateToYMD(calendar.getTime());
                Business_start_time.setContentVaule(startTime);
            }

            @Override
            public void cancel() {

            }
        });

        List<String> selectorList = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.BusinessSelectorType));
        timeMode = selectorList.get(0);
        customSelectorDialog = new CustomSelectorDialog(getActivity(),getString(R.string.SelectorType),selectorList);
        customSelectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                timeMode = dateString;
                Business_time_mode.setContentVaule(timeMode);
            }

            @Override
            public void cancel() {

            }
        });
        List<String> days = new ArrayList<>();
        for(int i = 1 ; i < 31 ; i ++){
            days.add(String.valueOf(i));
        }

        selectorDays = new CustomSelectorDialog(getActivity(),getString(R.string.SelectorType),days);
        selectorDays.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                Business_repayment_mode.setContentVaule(dateString + getString(R.string.business_day));
            }

            @Override
            public void cancel() {

            }
        });

        List<String> terms = new ArrayList<>();
        for(int i = 1 ; i < 31 ; i ++){
            terms.add(String.valueOf(i + getString(R.string.year) + "/" + (i * 12) + getString(R.string.stage)));
        }

        selectorTerm = new CustomSelectorDialog(getActivity(),getString(R.string.SelectorType),terms);

        selectorTerm.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                Business_time.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });

        Business_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        Business_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
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
            case R.id.Business_time:
                selectorTerm.show();
                break;
            case R.id.Business_start_time:
                timeDialog.show();
                break;
            case R.id.Business_time_mode:
                customSelectorDialog.show();
                break;
            case R.id.Business_repayment_mode:
                selectorDays.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Business_selector_rate://年利率
                break;
            case R.id.Senior_Submit:
                if (StringUtils.isBlank(Business_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_2));
                    return;
                }
                if (StringUtils.isBlank(Business_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_3));
                    return;
                }
                if (StringUtils.isBlank(Business_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_4));
                    return;
                }
                if(!StringUtils.isBlank(Business_name.getContentVaule())){
                    assetsElementModel.setMenuName(Business_name.getContentVaule());
                }
                assetsElementModel.setAmount(NumberUtils.unAbsForString(Double.valueOf(Business_number.getContentVaule())));

                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.Business_msg_3), Business_rate.getContentVaule());
                map.put(getString(R.string.Simple_msg_4), Business_time.getContentVaule());
                map.put(getString(R.string.Simple_msg_5), startTime);
                map.put(getString(R.string.Simple_msg_6), timeMode);
                map.put(getString(R.string.Business_msg_4), Business_repayment_mode.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_5, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
