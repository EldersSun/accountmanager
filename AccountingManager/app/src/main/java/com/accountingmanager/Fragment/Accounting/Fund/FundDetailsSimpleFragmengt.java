package com.accountingmanager.Fragment.Accounting.Fund;

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

/**基金--内容详情页 简单页面
 * Created by Home-Pc on 2017/5/10.
 */

public class FundDetailsSimpleFragmengt extends BaseFragment implements View.OnClickListener {

    private UiContentView fund_details_simple_number,fund_details_simple_amont,fund_details_simple_Profit;
    private Button fund_details_simple_submit;

    private AssetsElementModel model = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_funddetails_simple_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        fund_details_simple_number = (UiContentView) fgView.findViewById(R.id.fund_details_simple_number);
        fund_details_simple_amont = (UiContentView) fgView.findViewById(R.id.fund_details_simple_amont);
        fund_details_simple_Profit = (UiContentView) fgView.findViewById(R.id.fund_details_simple_Profit);

        fund_details_simple_submit = (Button) fgView.findViewById(R.id.fund_details_simple_submit);
    }

    @Override
    protected void initEvent() {

        fund_details_simple_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        fund_details_simple_amont.setContentFilers(filters);
        fund_details_simple_Profit.setContentFilers(filters);

        bundle = getArguments();

        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        fund_details_simple_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.share)));
        fund_details_simple_amont.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        fund_details_simple_Profit.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.fund_details_simple_submit:
                if(StringUtils.isBlank(fund_details_simple_number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.fund_details_simple_msg_7));
                    return;
                }

                if(!StringUtils.isBlank(model.getAmount())){
                    int number = Integer.valueOf(model.getAmount());
                    model.setAmount(String.valueOf(number * Integer.valueOf(fund_details_simple_number.getContentVaule())));
                } else {
                    model.setAmount("100");
                }

                Map<String, String> map = new HashMap<>();

                if(!StringUtils.isBlank(fund_details_simple_amont.getContentVaule())){
                    map.put(getString(R.string.fund_details_simple_msg_8),fund_details_simple_amont.getContentVaule());
                }

                if(!StringUtils.isBlank(fund_details_simple_Profit.getContentVaule())){
                    map.put(getString(R.string.fund_details_simple_msg_9),fund_details_simple_Profit.getContentVaule());
                }

                if(map != null && map.size() != 0){
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                    model.setMark(jsonObject.toString());
                }

                CommonUtils.getInstance().insertDB(model);

                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_6,model);
                getActivity().finish();
                break;
        }
    }
}
