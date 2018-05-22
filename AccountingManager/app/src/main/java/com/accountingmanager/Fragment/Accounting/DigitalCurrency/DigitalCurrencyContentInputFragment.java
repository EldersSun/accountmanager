package com.accountingmanager.Fragment.Accounting.DigitalCurrency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 数字货币输入内容页面
 * Created by Home-Pc on 2017/5/16.
 */

public class DigitalCurrencyContentInputFragment extends ContentBaseFragment implements View.OnClickListener {

    private UiContentView digital_input_number;
    private Button digital_input_submit;
    private TextView digital_input_tvShow;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_digital_content_input_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        digital_input_number = (UiContentView) fgView.findViewById(R.id.digital_input_number);
        digital_input_submit = (Button) fgView.findViewById(R.id.digital_input_submit);
        digital_input_tvShow = (TextView) fgView.findViewById(R.id.digital_input_tvShow);
    }

    @Override
    protected void initEvent() {
        digital_input_submit.setOnClickListener(this);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
            digital_input_tvShow.setText(getString(R.string.foreign_msg_4) + assetsElementModel.getMenuName() + getString(R.string.foreign_msg_5));
        }

        digital_input_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.individual)));

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
            case R.id.digital_input_submit:
                if (StringUtils.isBlank(digital_input_number.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.digital_input_msg_3) + assetsElementModel.getMenuName() + getString(R.string.digital_input_msg_4));
                    return;
                }

                assetsElementModel.setAmount(digital_input_number.getContentVaule());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_15,assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
