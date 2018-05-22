package com.accountingmanager.Fragment.Accounting.Other;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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

/**
 * 添加元素页面的其他模块的简单页面
 * Created by Home-Pc on 2017/5/17.
 */

public class AddAssetsOtherSimpleFragment extends BaseFragment implements OnClickListener {
    private UiContentView addAssets_other_simple_name, addAssets_other_simple_number, addAssets_other_simple_rate;
    private EditText addAssets_other_simple_mark;
    private Button addAssets_other_simple_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;


    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_assets_other_simple_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        addAssets_other_simple_name = (UiContentView) fgView.findViewById(R.id.addAssets_other_simple_name);
        addAssets_other_simple_number = (UiContentView) fgView.findViewById(R.id.addAssets_other_simple_number);
        addAssets_other_simple_rate = (UiContentView) fgView.findViewById(R.id.addAssets_other_simple_rate);
        addAssets_other_simple_mark = (EditText) fgView.findViewById(R.id.addAssets_other_simple_mark);
        addAssets_other_simple_submit = (Button) fgView.findViewById(R.id.addAssets_other_simple_submit);
    }

    @Override
    protected void initEvent() {
        addAssets_other_simple_submit.setOnClickListener(this);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        InputFilter[] filters = {new CashierInputFilter()};
        addAssets_other_simple_number.setContentFilers(filters);
        addAssets_other_simple_rate.setContentFilers(filters);

        addAssets_other_simple_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
        addAssets_other_simple_rate.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.rate_icon)));

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
            case R.id.addAssets_other_simple_submit:
                if (StringUtils.isBlank(addAssets_other_simple_name.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_simple_msg_8));
                    return;
                }
                if (StringUtils.isBlank(addAssets_other_simple_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.addAssets_other_simple_msg_9));
                    return;
                }

                assetsElementModel.setMenuName(addAssets_other_simple_name.getContentVaule());
                assetsElementModel.setAmount(addAssets_other_simple_number.getContentVaule());

                Map<String, String> map = new HashMap<>();

                if (!StringUtils.isBlank(addAssets_other_simple_rate.getContentVaule())) {
                    map.put(getString(R.string.addAssets_other_simple_msg_10), addAssets_other_simple_rate.getContentVaule());
                }

                if (addAssets_other_simple_mark.getText() != null && !StringUtils.isBlank(addAssets_other_simple_mark.getText().toString())) {
                    map.put(getString(R.string.addAssets_other_simple_msg_11), addAssets_other_simple_mark.getText().toString());
                }

                if (!map.isEmpty() && map.size() != 0) {
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
                    assetsElementModel.setMark(jsonObject.toString());
                }

                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
