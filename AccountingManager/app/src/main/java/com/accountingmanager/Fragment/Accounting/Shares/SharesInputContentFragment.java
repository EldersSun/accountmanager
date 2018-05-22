package com.accountingmanager.Fragment.Accounting.Shares;

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
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Home-Pc on 2017/5/10.
 */

public class SharesInputContentFragment extends ContentBaseFragment implements View.OnClickListener {

    private UiContentView Shares_input_content_number, Shares_input_content_emelent;
    private Button Shares_input_content_submit;

    private AssetsElementModel model = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_shares_input_content_laytout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Shares_input_content_number = (UiContentView) fgView.findViewById(R.id.Shares_input_content_number);
        Shares_input_content_emelent = (UiContentView) fgView.findViewById(R.id.Shares_input_content_emelent);

        Shares_input_content_submit = (Button) fgView.findViewById(R.id.Shares_input_content_submit);
    }

    @Override
    protected void initEvent() {
        Shares_input_content_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        Shares_input_content_emelent.setContentFilers(filters);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(model.getMenuName() == null ? "" : model.getMenuName());
        }


        Shares_input_content_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.gu)));
        Shares_input_content_emelent.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));

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
            case R.id.Shares_input_content_submit:
                if (StringUtils.isBlank(Shares_input_content_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.Shares_input_content_msg_4));
                    return;
                }

                if (StringUtils.isBlank(Shares_input_content_emelent.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.Shares_input_content_msg_5));
                    return;
                }
                model.setAmount(String.valueOf(Integer.valueOf(Shares_input_content_number.getContentVaule()) * Integer.valueOf(Shares_input_content_emelent.getContentVaule())));

                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.Shares_input_content_msg_6), Shares_input_content_emelent.getContentVaule());
                map.put(getString(R.string.Shares_input_content_msg_7), Shares_input_content_number.getContentVaule());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                model.setMark(jsonObject.toString());
                CommonUtils.getInstance().insertDB(model);

                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_7, model);
                getActivity().finish();

                break;
        }
    }
}
