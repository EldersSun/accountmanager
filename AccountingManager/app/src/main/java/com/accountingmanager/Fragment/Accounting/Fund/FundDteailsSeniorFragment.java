package com.accountingmanager.Fragment.Accounting.Fund;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.accountingmanager.Base.BaseFragment;
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
 * 基金--内容详情页 高级页面
 * Created by Home-Pc on 2017/5/10.
 */

public class FundDteailsSeniorFragment extends BaseFragment implements View.OnClickListener,UiContentView.OnContentClick{

    private UiContentView fund_details_senior_number,fund_details_senior_time,fund_details_senior_rate;
    private Button fund_details_senior_submit,fund_details_senior_fixed;
    private ImageView fund_details_senior_selector_rate;

    private SelectorTimeDialog selectorTimeDialog;

    private String selectorTime = "";

    private AssetsElementModel model = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_fund_details_senior_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        fund_details_senior_number = (UiContentView) fgView.findViewById(R.id.fund_details_senior_number);
        fund_details_senior_time = (UiContentView) fgView.findViewById(R.id.fund_details_senior_time);
        fund_details_senior_rate = (UiContentView) fgView.findViewById(R.id.fund_details_senior_rate);

        fund_details_senior_submit = (Button) fgView.findViewById(R.id.fund_details_senior_submit);
        fund_details_senior_fixed = (Button) fgView.findViewById(R.id.fund_details_senior_fixed);
        fund_details_senior_selector_rate = (ImageView) fgView.findViewById(R.id.fund_details_senior_selector_rate);
    }

    @Override
    protected void initEvent() {
        fund_details_senior_submit.setOnClickListener(this);
        fund_details_senior_fixed.setOnClickListener(this);
        fund_details_senior_selector_rate.setOnClickListener(this);

        fund_details_senior_time.setOnContentClick(this);

        fund_details_senior_fixed.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        fund_details_senior_fixed.getPaint().setAntiAlias(true);

        InputFilter[] filters = {new CashierInputFilter()};
        fund_details_senior_number.setContentFilers(filters);
        fund_details_senior_rate.setContentFilers(filters);

        fund_details_senior_time.setContentVaule(TimeUtils.dateToYMD(Calendar.getInstance().getTime()));

        selectorTimeDialog = new SelectorTimeDialog(getActivity());

        selectorTimeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {
                selectorTime = TimeUtils.dateToYMD(calendar.getTime());
                fund_details_senior_time.setContentVaule(selectorTime);
            }

            @Override
            public void cancel() {

            }
        });

        bundle = getArguments();

        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        fund_details_senior_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        fund_details_senior_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));
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
            case R.id.fund_details_senior_submit:
                if(StringUtils.isBlank(fund_details_senior_number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.fund_details_senior_msg_6));
                    return;
                }
                if(StringUtils.isBlank(selectorTime)){
                    ToastUtils.shortShow(getString(R.string.fund_details_senior_msg_7));
                    return;
                }
                if(StringUtils.isBlank(fund_details_senior_rate.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.fund_details_senior_msg_8));
                    return;
                }

                model.setAmount(fund_details_senior_number.getContentVaule());

                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.fund_details_senior_msg_9),selectorTime);
                map.put(getString(R.string.fund_details_senior_msg_10),fund_details_senior_rate.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                model.setMark(jsonObject.toString());

                CommonUtils.getInstance().insertDB(model);

                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_6,model);
                getActivity().finish();

                break;
            case R.id.fund_details_senior_fixed:
                break;
            case R.id.fund_details_senior_selector_rate:
                break;
        }
    }

    @Override
    public void onContentClick(View v) {
        switch (v.getId()){
            case R.id.fund_details_senior_time:
                selectorTimeDialog.show();
                break;
        }
    }
}
