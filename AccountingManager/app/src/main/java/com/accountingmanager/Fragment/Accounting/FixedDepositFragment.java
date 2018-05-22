package com.accountingmanager.Fragment.Accounting;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

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
import java.util.Map;

/**
 * 固定存款
 * Created by Home-Pc on 2017/5/11.
 */

public class FixedDepositFragment extends ContentBaseFragment implements UiContentView.OnContentClick, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar fixed_Deposit_period;
    private UiContentView fixed_time, fixed_bank, fixed_rate, fixed_number;
    private Button fixed_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    private String selectorDeposit = "";
    private String time = "";

    private SelectorTimeDialog selectorTimeDialog;
    private CustomSelectorDialog customSelectorDialog;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_fixed_deposit_laytout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        fixed_Deposit_period = (SeekBar) fgView.findViewById(R.id.fixed_Deposit_period);
        fixed_time = (UiContentView) fgView.findViewById(R.id.fixed_time);
        fixed_bank = (UiContentView) fgView.findViewById(R.id.fixed_bank);
        fixed_rate = (UiContentView) fgView.findViewById(R.id.fixed_rate);
        fixed_number = (UiContentView) fgView.findViewById(R.id.fixed_number);
        fixed_submit = (Button) fgView.findViewById(R.id.fixed_submit);
    }

    @Override
    protected void initEvent() {

        fixed_time.setOnContentClick(this);
        fixed_bank.setOnContentClick(this);
        fixed_submit.setOnClickListener(this);

        fixed_Deposit_period.setOnSeekBarChangeListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        fixed_rate.setContentFilers(filters);
        fixed_number.setContentFilers(filters);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        fixed_time.setContentVaule(TimeUtils.dateToYMD(Calendar.getInstance().getTime()));

        selectorTimeDialog = new SelectorTimeDialog(getActivity());

        selectorTimeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                time = TimeUtils.dateToYMD(calendar.getTime());
                fixed_time.setContentVaule(time);
            }

            @Override
            public void cancel() {

            }
        });

        customSelectorDialog = new CustomSelectorDialog(getActivity(), getString(R.string.Selector_Bank),
                ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.SelectorBankList)));

        customSelectorDialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                fixed_bank.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });

        fixed_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
        fixed_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.fixed_submit:
                if (StringUtils.isBlank(fixed_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.fixed_deposit_input_msg_7));
                    return;
                }

                if (StringUtils.isBlank(fixed_rate.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.fixed_deposit_input_msg_8));
                    return;
                }

                if (StringUtils.isBlank(time)) {
                    ToastUtils.shortShow(getString(R.string.fixed_deposit_input_msg_9));
                    return;
                }

                if (StringUtils.isBlank(fixed_bank.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.fixed_deposit_input_msg_10));
                    return;
                }
                assetsElementModel.setMenuName(assetsElementModel.getMenuName() + " - " + fixed_bank.getContentVaule());
                assetsElementModel.setAmount(fixed_number.getContentVaule());

                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.fixed_deposit_input_msg_11), time);
                map.put(getString(R.string.fixed_deposit_input_msg_12), fixed_rate.getContentVaule());
                map.put(getString(R.string.fixed_deposit_input_msg_13), fixed_bank.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                assetsElementModel.setMark(jsonObject.toString());

                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, assetsElementModel);
                getActivity().finish();

                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()) {
            case R.id.fixed_time:
                selectorTimeDialog.show();
                break;
            case R.id.fixed_bank:
                customSelectorDialog.show();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progress > 0 && progress <= 20) {
            selectorDeposit = getString(R.string.fixed_deposit_input_msg_14);
            seekBar.setProgress(20);
        } else if (progress > 20 && progress <= 40) {
            selectorDeposit = getString(R.string.fixed_deposit_input_msg_15);
            seekBar.setProgress(40);
        } else if (progress > 40 && progress <= 60) {
            selectorDeposit = getString(R.string.fixed_deposit_input_msg_16);
            seekBar.setProgress(60);
        } else if (progress > 60 && progress <= 80) {
            selectorDeposit = getString(R.string.fixed_deposit_input_msg_17);
            seekBar.setProgress(80);
        } else if (progress > 80 && progress <= 100) {
            selectorDeposit = getString(R.string.fixed_deposit_input_msg_18);
            seekBar.setProgress(100);
        } else {
            selectorDeposit = getString(R.string.fixed_deposit_input_msg_19);
            seekBar.setProgress(0);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
