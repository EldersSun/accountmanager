package com.accountingmanager.Fragment.Accounting.Liabilities;

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
import com.accountingmanager.Sys.Utils.NumberUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 贷款/欠款 -- 简单的
 * Created by Home-Pc on 2017/4/28.
 */

public class ArrearsSimpleFragment extends BaseFragment implements View.OnClickListener{
    private UiContentView Simple_name,Simple_Number;
    private Button Simple_Submit;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_arrearsinputsimple_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Simple_name = (UiContentView) fgView.findViewById(R.id.Simple_name);
        Simple_Number = (UiContentView) fgView.findViewById(R.id.Simple_Number);
        Simple_Submit = (Button) fgView.findViewById(R.id.Simple_Submit);
    }

    @Override
    protected void initEvent() {
        Simple_Submit.setOnClickListener(this);
        InputFilter[] filters = {new CashierInputFilter()};
        Simple_Number.setContentFilers(filters);
        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }

        Simple_Number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.Simple_Submit:
                if(StringUtils.isBlank(Simple_name.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.simple_msg_1));
                    return;
                }
                if(StringUtils.isBlank(Simple_Number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.simple_msg_2));
                    return;
                }

                assetsElementModel.setMenuName(Simple_name.getContentVaule());
                assetsElementModel.setAmount(NumberUtils.unAbsForString(Double.valueOf(Simple_Number.getContentVaule())));
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_5, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
