package com.accountingmanager.Fragment.Accounting.Liabilities;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款/欠款 -- 高级
 * Created by Home-Pc on 2017/4/28.
 */

public class ArrearsSeniorFragment extends BaseFragment implements View.OnClickListener, UiContentView.OnContentClick {
    private UiContentView Senior_name, Senior_number, Senior_rate, Senior_time, Senior_start_time, Senior_time_mode;
    private RadioButton Senior_year, Senior_month, Senior_time_month, Senior_time_day;
    private Button Senior_Submit;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    private String rateString = AppConfig.getInstance().YEAR;//利率
    private String timeString = AppConfig.getInstance().MONTH;//期限
    private String startTime = "";//起息时间
    private String timeMode = "";//还款方式

    private SelectorTimeDialog timeDialog;
    private CustomSelectorDialog customSelectorDialog;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_arrearsinputsenior_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Senior_name = (UiContentView) fgView.findViewById(R.id.Senior_name);
        Senior_number = (UiContentView) fgView.findViewById(R.id.Senior_number);
        Senior_rate = (UiContentView) fgView.findViewById(R.id.Senior_rate);
        Senior_time = (UiContentView) fgView.findViewById(R.id.Senior_time);
        Senior_start_time = (UiContentView) fgView.findViewById(R.id.Senior_start_time);
        Senior_time_mode = (UiContentView) fgView.findViewById(R.id.Senior_time_mode);

        Senior_year = (RadioButton) fgView.findViewById(R.id.Senior_year);
        Senior_month = (RadioButton) fgView.findViewById(R.id.Senior_month);
        Senior_time_month = (RadioButton) fgView.findViewById(R.id.Senior_time_month);
        Senior_time_day = (RadioButton) fgView.findViewById(R.id.Senior_time_day);

        Senior_Submit = (Button) fgView.findViewById(R.id.Senior_Submit);
    }

    @Override
    protected void initEvent() {
        Senior_Submit.setOnClickListener(this);
        Senior_year.setOnClickListener(this);
        Senior_month.setOnClickListener(this);
        Senior_time_month.setOnClickListener(this);
        Senior_time_day.setOnClickListener(this);

        Senior_start_time.setOnContentClick(this);
        Senior_time_mode.setOnContentClick(this);

        timeDialog = new SelectorTimeDialog(getActivity());
        startTime = TimeUtils.dateToYMD(Calendar.getInstance().getTime());
        Senior_start_time.setContentVaule(startTime);

        timeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                startTime = TimeUtils.dateToYMD(calendar.getTime());
                Senior_start_time.setContentVaule(startTime);
            }

            @Override
            public void cancel() {

            }
        });

        InputFilter[] filters = {new CashierInputFilter()};
        Senior_number.setContentFilers(filters);
        Senior_rate.setContentFilers(filters);
        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        List<String> selectorList = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.SeniorSelectorType));
        timeMode = selectorList.get(0);
        customSelectorDialog = new CustomSelectorDialog(getActivity(),getString(R.string.SelectorType),selectorList);

        customSelectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                timeMode = dateString;
                Senior_time_mode.setContentVaule(timeMode);
            }

            @Override
            public void cancel() {

            }
        });

        Senior_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        Senior_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
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
            case R.id.Senior_Submit:
                if (StringUtils.isBlank(Senior_name.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_1));
                    return;
                }
                if (StringUtils.isBlank(Senior_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_2));
                    return;
                }
                if (StringUtils.isBlank(Senior_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_3));
                    return;
                }
                if (StringUtils.isBlank(Senior_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_4));
                    return;
                }
                assetsElementModel.setMenuName(Senior_name.getContentVaule());
                assetsElementModel.setAmount(NumberUtils.unAbsForString(Double.valueOf(Senior_number.getContentVaule())));
                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.rate), Senior_rate.getContentVaule() + rateString);
                map.put(getString(R.string.term), Senior_time.getContentVaule() + timeString);
                map.put(getString(R.string.startTime), startTime);
                map.put(getString(R.string.repaymentMode), timeMode);
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_5, assetsElementModel);
                getActivity().finish();
                break;
            case R.id.Senior_year:
                rateString = getString(R.string.year);
                break;
            case R.id.Senior_month:
                rateString = getString(R.string.month);
                break;
            case R.id.Senior_time_month:
                timeString = getString(R.string.month);
                break;
            case R.id.Senior_time_day:
                timeString = getString(R.string.day);
                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()) {
            case R.id.Senior_start_time:
                timeDialog.show();
                break;
            case R.id.Senior_time_mode:
                customSelectorDialog.show();
                break;
        }
    }
}
