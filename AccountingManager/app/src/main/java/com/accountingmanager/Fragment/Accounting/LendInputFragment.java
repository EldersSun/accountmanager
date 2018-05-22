package com.accountingmanager.Fragment.Accounting;

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
 * 借出/应收
 * Created by Home-Pc on 2017/5/3.
 */

public class LendInputFragment extends ContentBaseFragment implements View.OnClickListener{
    private UiContentView lend_number,lend_name;
    private Button lend_selector,lend_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_lendinput_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        lend_number = (UiContentView) fgView.findViewById(R.id.lend_number);
        lend_name = (UiContentView) fgView.findViewById(R.id.lend_name);
        lend_selector = (Button) fgView.findViewById(R.id.lend_selector);
        lend_submit = (Button) fgView.findViewById(R.id.lend_submit);
    }

    @Override
    protected void initEvent() {
        lend_selector.setOnClickListener(this);
        lend_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        lend_number.setContentFilers(filters);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        lend_number.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.lend_submit:
                if(StringUtils.isBlank(lend_number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.lend_msg_3));
                    return;
                }
                if (StringUtils.isBlank(lend_name.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.lend_msg_4));
                    return;
                }

                assetsElementModel.setMenuName(lend_name.getContentVaule());
                assetsElementModel.setAmount(lend_number.getContentVaule());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, assetsElementModel);
                getActivity().finish();
                break;
            case R.id.lend_selector:
                break;
        }
    }
}
