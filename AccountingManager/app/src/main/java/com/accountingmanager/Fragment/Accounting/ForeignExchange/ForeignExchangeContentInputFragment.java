package com.accountingmanager.Fragment.Accounting.ForeignExchange;

import android.os.Bundle;
import android.text.InputFilter;
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
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 外汇内容输入页面
 * Created by Home-Pc on 2017/5/16.
 */
public class ForeignExchangeContentInputFragment extends ContentBaseFragment implements View.OnClickListener {

    private UiContentView foreign_number;
    private TextView foreign_tvShow;
    private Button foreign_submit;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_foreign_content_input_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        foreign_number = (UiContentView) fgView.findViewById(R.id.foreign_number);
        foreign_tvShow = (TextView) fgView.findViewById(R.id.foreign_tvShow);
        foreign_submit = (Button) fgView.findViewById(R.id.foreign_submit);
    }

    @Override
    protected void initEvent() {
        foreign_submit.setOnClickListener(this);
        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());

            foreign_tvShow.setText(getString(R.string.foreign_msg_4) + assetsElementModel.getMenuName() + getString(R.string.foreign_msg_5));
        }

        if (bundle.containsKey(AppConfig.getInstance().FOREIGN_SELECTOR_CONTENT_TAG) &&
                !StringUtils.isBlank(bundle.getString(AppConfig.getInstance().FOREIGN_SELECTOR_CONTENT_TAG))) {
            String company = bundle.getString(AppConfig.getInstance().FOREIGN_SELECTOR_CONTENT_TAG);
            foreign_number.setContentImgBitmap(ImgUtils.drawToTvShow(company));
        }

        InputFilter[] filters = {new CashierInputFilter()};
        foreign_number.setContentFilers(filters);
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
            case R.id.foreign_submit:
                if(StringUtils.isBlank(foreign_number.getContentVaule())){
                    ToastUtils.shortShow(getString(R.string.foreign_msg_3));
                    return;
                }
                assetsElementModel.setAmount(foreign_number.getContentVaule());
                CommonUtils.getInstance().insertDB(assetsElementModel);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_14,assetsElementModel);
                getActivity().finish();
                break;
        }
    }
}
