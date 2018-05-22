package com.accountingmanager.Fragment.Accounting.Other;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.accountingmanager.Base.BaseFragment;
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
 * 添加元素页面的其他模块的高级页面
 * Created by Home-Pc on 2017/5/17.
 */

public class AddAssetsOtherSeniorFragment extends BaseFragment implements View.OnClickListener, UiContentView.OnContentClick {

    private UiContentView addAssets_other_senior_name, addAssets_other_senior_number, addAssets_other_senior_rate,
            addAssets_other_senior_time, addAssets_other_senior_start_time, addAssets_other_senior_mode;

    private EditText addAssets_other_senior_mark;
    private Button addAssets_other_senior_submit;

    private RadioButton addAssets_other_senior_month, addAssets_other_senior_day;


    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    private SelectorTimeDialog selectorTimeDialog;
    private CustomSelectorDialog customSelectorDialog;

    private String timeType = "";

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_assets_other_senior_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        addAssets_other_senior_name = (UiContentView) fgView.findViewById(R.id.addAssets_other_senior_name);
        addAssets_other_senior_number = (UiContentView) fgView.findViewById(R.id.addAssets_other_senior_number);
        addAssets_other_senior_rate = (UiContentView) fgView.findViewById(R.id.addAssets_other_senior_rate);
        addAssets_other_senior_time = (UiContentView) fgView.findViewById(R.id.addAssets_other_senior_time);
        addAssets_other_senior_start_time = (UiContentView) fgView.findViewById(R.id.addAssets_other_senior_start_time);
        addAssets_other_senior_mode = (UiContentView) fgView.findViewById(R.id.addAssets_other_senior_mode);
        addAssets_other_senior_mark = (EditText) fgView.findViewById(R.id.addAssets_other_senior_mark);
        addAssets_other_senior_submit = (Button) fgView.findViewById(R.id.addAssets_other_senior_submit);
        addAssets_other_senior_month = (RadioButton) fgView.findViewById(R.id.addAssets_other_senior_month);
        addAssets_other_senior_day = (RadioButton) fgView.findViewById(R.id.addAssets_other_senior_day);
    }

    @Override
    protected void initEvent() {
        addAssets_other_senior_start_time.setOnContentClick(this);
        addAssets_other_senior_mode.setOnContentClick(this);
        addAssets_other_senior_submit.setOnClickListener(this);
        addAssets_other_senior_month.setOnClickListener(this);
        addAssets_other_senior_day.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        addAssets_other_senior_number.setContentFilers(filters);
        addAssets_other_senior_rate.setContentFilers(filters);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        addAssets_other_senior_start_time.setContentVaule(TimeUtils.dateToYMD(Calendar.getInstance().getTime()));

        selectorTimeDialog = new SelectorTimeDialog(getActivity());
        selectorTimeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                addAssets_other_senior_start_time.setContentVaule(TimeUtils.dateToYMD(calendar.getTime()));
            }

            @Override
            public void cancel() {

            }
        });

        List<String> list = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.SeniorSelectorType));
        if (list != null && list.size() != 0) {
            addAssets_other_senior_mode.setContentVaule(list.get(0));
        }
        customSelectorDialog = new CustomSelectorDialog(getActivity(), getString(R.string.insurance_financing_msg_11), list);
        customSelectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                addAssets_other_senior_mode.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });

        addAssets_other_senior_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        addAssets_other_senior_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));

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
            case R.id.addAssets_other_senior_submit:
                if (StringUtils.isBlank(addAssets_other_senior_name.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_senior_msg_8));
                    return;
                }

                if (StringUtils.isBlank(addAssets_other_senior_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_senior_msg_9));
                    return;
                }

                if (StringUtils.isBlank(addAssets_other_senior_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_senior_msg_10));
                    return;
                }

                if (StringUtils.isBlank(addAssets_other_senior_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_senior_msg_11));
                    return;
                }

                if (StringUtils.isBlank(addAssets_other_senior_start_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_senior_msg_12));
                    return;
                }

                if (StringUtils.isBlank(addAssets_other_senior_mode.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_senior_msg_13));
                    return;
                }

                assetsElementModel.setMenuName(addAssets_other_senior_name.getContentVaule());
                assetsElementModel.setAmount(addAssets_other_senior_number.getContentVaule());

                Map<String, String> map = new HashMap<>();

                map.put(getString(R.string.addAssets_other_senior_msg_14), addAssets_other_senior_rate.getContentVaule());
                map.put(getString(R.string.addAssets_other_senior_msg_15), addAssets_other_senior_time.getContentVaule() + timeType);
                map.put(getString(R.string.addAssets_other_senior_msg_16), addAssets_other_senior_start_time.getContentVaule() + timeType);
                map.put(getString(R.string.addAssets_other_senior_msg_17), addAssets_other_senior_mode.getContentVaule() + timeType);

                if (addAssets_other_senior_mark.getText() != null && !StringUtils.isBlank(addAssets_other_senior_mark.getText().toString())) {
                    map.put(getString(R.string.addAssets_other_simple_msg_11), addAssets_other_senior_mark.getText().toString());
                }

                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());

                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, assetsElementModel);
                getActivity().finish();
                break;
            case R.id.addAssets_other_senior_month:
                timeType = getString(R.string.month);
                break;
            case R.id.addAssets_other_senior_day:
                timeType = getString(R.string.day);
                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()) {
            case R.id.addAssets_other_senior_start_time:
                selectorTimeDialog.show();
                break;
            case R.id.addAssets_other_senior_mode:
                customSelectorDialog.show();
                break;
        }
    }
}
