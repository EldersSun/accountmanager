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
 * 固定资产
 * Created by Home-Pc on 2017/5/3.
 */

public class FixedAssetsFragment extends ContentBaseFragment implements View.OnClickListener {

    private UiContentView fixed_name, fixed_number;
    private Button fixed_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_fixed_assets_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        fixed_name = (UiContentView) fgView.findViewById(R.id.fixed_name);
        fixed_number = (UiContentView) fgView.findViewById(R.id.fixed_number);
        fixed_submit = (Button) fgView.findViewById(R.id.fixed_submit);
    }

    @Override
    protected void initEvent() {
        fixed_submit.setOnClickListener(this);

        InputFilter[] filters = {new CashierInputFilter()};
        fixed_number.setContentFilers(filters);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

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
                if (StringUtils.isBlank(fixed_name.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.fixed_msg_1));
                    return;
                }
                if(StringUtils.isBlank(fixed_number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.fixed_msg_2));
                    return;
                }

                assetsElementModel.setMenuName(fixed_name.getContentVaule());
                assetsElementModel.setAmount(fixed_number.getContentVaule());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
