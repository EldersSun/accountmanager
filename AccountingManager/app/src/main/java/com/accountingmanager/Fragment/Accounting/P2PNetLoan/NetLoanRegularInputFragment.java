package com.accountingmanager.Fragment.Accounting.P2PNetLoan;

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
 * 定期输入界面
 * Created by Home-Pc on 2017/5/5.
 */

public class NetLoanRegularInputFragment extends BaseFragment implements UiContentView.OnContentClick,View.OnClickListener{

    private UiContentView netLoan_regular_name,netLoan_regular_num,netLoan_regular_rate,
            netLoan_regular_time,netLoan_regular_startTime,netLoan_regular_mode;

    private RadioButton netLoan_regular_year,netLoan_regular_month;

    private Button netLoan_regular_submit;
    private SelectorTimeDialog timeDialog;
    private CustomSelectorDialog selectorDialog;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    private String timeString = "月";//期限


    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_netloan_regular_input_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        netLoan_regular_name = (UiContentView) fgView.findViewById(R.id.netLoan_regular_name);
        netLoan_regular_num = (UiContentView) fgView.findViewById(R.id.netLoan_regular_num);
        netLoan_regular_rate = (UiContentView) fgView.findViewById(R.id.netLoan_regular_rate);
        netLoan_regular_time = (UiContentView) fgView.findViewById(R.id.netLoan_regular_time);
        netLoan_regular_startTime = (UiContentView) fgView.findViewById(R.id.netLoan_regular_startTime);
        netLoan_regular_mode = (UiContentView) fgView.findViewById(R.id.netLoan_regular_mode);
        netLoan_regular_year = (RadioButton) fgView.findViewById(R.id.netLoan_regular_year);
        netLoan_regular_month = (RadioButton) fgView.findViewById(R.id.netLoan_regular_month);
        netLoan_regular_submit = (Button) fgView.findViewById(R.id.netLoan_regular_submit);
    }

    @Override
    protected void initEvent() {
        netLoan_regular_submit.setOnClickListener(this);
        netLoan_regular_year.setOnClickListener(this);
        netLoan_regular_month.setOnClickListener(this);

        netLoan_regular_startTime.setOnContentClick(this);
        netLoan_regular_mode.setOnContentClick(this);

        InputFilter[] filters = {new CashierInputFilter()};
        netLoan_regular_num.setContentFilers(filters);
        netLoan_regular_rate.setContentFilers(filters);

        timeDialog = new SelectorTimeDialog(getActivity());
        netLoan_regular_startTime.setContentVaule(TimeUtils.dateToYMD(Calendar.getInstance().getTime()));
        timeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                netLoan_regular_startTime.setContentVaule(TimeUtils.dateToYMD(calendar.getTime()));
            }

            @Override
            public void cancel() {

            }
        });

        List<String> modlist = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.SelectorRegularMod));
        selectorDialog = new CustomSelectorDialog(getActivity(),getResources().getString(R.string.SelectorType),modlist);
        selectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                netLoan_regular_mode.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        netLoan_regular_num.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        netLoan_regular_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
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
        switch (v.getId()){
            case R.id.netLoan_regular_startTime:
                timeDialog.show();
                break;
            case R.id.netLoan_regular_mode:
                selectorDialog.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.netLoan_regular_submit:

                if (StringUtils.isBlank(netLoan_regular_num.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_2));
                    return;
                }
                if (StringUtils.isBlank(netLoan_regular_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_3));
                    return;
                }
                if (StringUtils.isBlank(netLoan_regular_time.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.simple_msg_4));
                    return;
                }

                if(!StringUtils.isBlank(netLoan_regular_name.getContentVaule())){
                    assetsElementModel.setMenuName(netLoan_regular_name.getContentVaule());
                }
                assetsElementModel.setAmount(netLoan_regular_num.getContentVaule());
                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.rate), netLoan_regular_rate.getContentVaule());
                map.put(getString(R.string.term), netLoan_regular_time.getContentVaule() + timeString);
                map.put(getString(R.string.startTime), netLoan_regular_startTime.getContentVaule());
                map.put(getString(R.string.repaymentMode), netLoan_regular_mode.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_9, assetsElementModel);
                getActivity().finish();
                break;
            case R.id.netLoan_regular_year:
                timeString = getString(R.string.year);
                break;
            case R.id.netLoan_regular_month:
                timeString = getString(R.string.month);
                break;
        }
    }
}
