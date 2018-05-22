package com.accountingmanager.Fragment.Accounting.NationalDebt;

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

/**
 * 国债内容输入界面
 * Created by Home-Pc on 2017/5/15.
 */

public class NationalDebtContentInputFragment extends ContentBaseFragment implements View.OnClickListener{

    private UiContentView national_content_input_number;
    private Button national_content_input_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_national_content_input_laytout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        national_content_input_number = (UiContentView) fgView.findViewById(R.id.national_content_input_number);
        national_content_input_submit = (Button) fgView.findViewById(R.id.national_content_input_submit);
    }

    @Override
    protected void initEvent() {
        national_content_input_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        national_content_input_number.setContentFilers(filters);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        national_content_input_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.national_content_input_submit:
                if(StringUtils.isBlank(national_content_input_number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.national_msg_6));
                    return;
                }

                assetsElementModel.setAmount(national_content_input_number.getContentVaule());
                CommonUtils.getInstance().insertDB(assetsElementModel);

                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_13,assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
