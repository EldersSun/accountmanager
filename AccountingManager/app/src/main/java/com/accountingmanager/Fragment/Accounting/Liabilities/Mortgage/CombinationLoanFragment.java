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
 * 组合贷款
 * Created by Home-Pc on 2017/5/3.
 */

public class CombinationLoanFragment extends BaseFragment implements View.OnClickListener, UiContentView.OnContentClick {
    private UiContentView Combination_name, Combination_business_number, Combination_business_rate,
            Combination_fund_Principal, Combination_fund_rate, Combination_time, Combination_startTime,
            Combination_mode, Combination_mode_day;
    private Button Combination_business_selector_rate, Combination_fund_selector_rate, Combination_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    private SelectorTimeDialog timeDialog;

    private String startTime = "";//起息时间
    private String timeMode = "";//还款方式

    private CustomSelectorDialog customSelectorDialog;
    private CustomSelectorDialog selectorDays;
    private CustomSelectorDialog selectorTerm;


    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_combination_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Combination_name = (UiContentView) fgView.findViewById(R.id.Combination_name);
        Combination_business_number = (UiContentView) fgView.findViewById(R.id.Combination_business_number);
        Combination_business_rate = (UiContentView) fgView.findViewById(R.id.Combination_business_rate);
        Combination_fund_Principal = (UiContentView) fgView.findViewById(R.id.Combination_fund_Principal);
        Combination_fund_rate = (UiContentView) fgView.findViewById(R.id.Combination_fund_rate);
        Combination_time = (UiContentView) fgView.findViewById(R.id.Combination_time);
        Combination_startTime = (UiContentView) fgView.findViewById(R.id.Combination_startTime);
        Combination_mode = (UiContentView) fgView.findViewById(R.id.Combination_mode);
        Combination_mode_day = (UiContentView) fgView.findViewById(R.id.Combination_mode_day);

        Combination_business_selector_rate = (Button) fgView.findViewById(R.id.Combination_business_selector_rate);
        Combination_fund_selector_rate = (Button) fgView.findViewById(R.id.Combination_fund_selector_rate);
        Combination_submit = (Button) fgView.findViewById(R.id.Combination_submit);
    }

    @Override
    protected void initEvent() {
        Combination_business_selector_rate.setOnClickListener(this);
        Combination_fund_selector_rate.setOnClickListener(this);
        Combination_submit.setOnClickListener(this);

        Combination_time.setOnContentClick(this);
        Combination_startTime.setOnContentClick(this);
        Combination_mode.setOnContentClick(this);
        Combination_mode_day.setOnContentClick(this);

        InputFilter[] filters = {new CashierInputFilter()};
        Combination_business_number.setContentFilers(filters);
        Combination_business_rate.setContentFilers(filters);
        Combination_fund_Principal.setContentFilers(filters);
        Combination_fund_rate.setContentFilers(filters);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        startTime = TimeUtils.dateToYMD(Calendar.getInstance().getTime());
        Combination_startTime.setContentVaule(startTime);

        timeDialog = new SelectorTimeDialog(getActivity());
        timeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                startTime = TimeUtils.dateToYMD(calendar.getTime());
                Combination_startTime.setContentVaule(startTime);
            }

            @Override
            public void cancel() {

            }
        });

        List<String> selectorList = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.BusinessSelectorType));
        timeMode = selectorList.get(0);
        customSelectorDialog = new CustomSelectorDialog(getActivity(), getString(R.string.SelectorType), selectorList);
        customSelectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                timeMode = dateString;
                Combination_mode.setContentVaule(timeMode);
            }

            @Override
            public void cancel() {

            }
        });

        List<String> days = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            days.add(String.valueOf(i));
        }

        selectorDays = new CustomSelectorDialog(getActivity(), getString(R.string.SelectorType), days);
        selectorDays.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                Combination_mode_day.setContentVaule(dateString + getString(R.string.business_day));
            }

            @Override
            public void cancel() {

            }
        });

        List<String> terms = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            terms.add(String.valueOf(i + getString(R.string.year) + "/" + (i * 12) + getString(R.string.stage)));
        }

        selectorTerm = new CustomSelectorDialog(getActivity(), getString(R.string.SelectorType), terms);
        selectorTerm.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                Combination_time.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });


        Combination_business_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        Combination_business_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
        Combination_fund_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
        Combination_fund_Principal.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.Combination_business_selector_rate:
                break;
            case R.id.Combination_fund_selector_rate:
                break;
            case R.id.Combination_submit:
                if (StringUtils.isBlank(Combination_business_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.combination_msg_11));
                    return;
                }
                if (StringUtils.isBlank(Combination_business_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.combination_msg_12));
                    return;
                }
                if (StringUtils.isBlank(Combination_fund_Principal.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.combination_msg_13));
                    return;
                }
                if (StringUtils.isBlank(Combination_fund_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.combination_msg_14));
                    return;
                }
                if (!StringUtils.isBlank(Combination_name.getContentVaule())) {
                    assetsElementModel.setMenuName(Combination_name.getContentVaule());
                }
                assetsElementModel.setAmount(NumberUtils.unAbsForString(Double.valueOf(Combination_business_number.getContentVaule()) +
                        Double.valueOf(Combination_fund_Principal.getContentVaule())));

                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.Combination_msg_2), Combination_business_rate.getContentVaule());
                map.put(getString(R.string.Combination_msg_10), Combination_fund_rate.getContentVaule());
                map.put(getString(R.string.Combination_msg_4), Combination_time.getContentVaule());
                map.put(getString(R.string.Combination_msg_5), startTime);
                map.put(getString(R.string.Combination_msg_6), timeMode);
                map.put(getString(R.string.Combination_msg_7), Combination_mode_day.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_5, assetsElementModel);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()) {
            case R.id.Combination_time:
                selectorTerm.show();
                break;
            case R.id.Combination_startTime:
                timeDialog.show();
                break;
            case R.id.Combination_mode:
                customSelectorDialog.show();
                break;
            case R.id.Combination_mode_day:
                selectorDays.show();
                break;
        }
    }
}
