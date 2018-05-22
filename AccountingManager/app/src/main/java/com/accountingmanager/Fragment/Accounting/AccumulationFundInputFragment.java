package com.accountingmanager.Fragment.Accounting;

import android.os.Bundle;
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
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

/**
 * 公积金手动添加页面
 * Created by Home-Pc on 2017/4/27.
 */

public class AccumulationFundInputFragment extends ContentBaseFragment implements View.OnClickListener {
    private UiContentView fund_input_account, fund_input_city;
    private Button fund_input_submit;

    private AssetsElementModel model = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_fund_input_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        fund_input_account = (UiContentView) fgView.findViewById(R.id.fund_input_account);
        fund_input_city = (UiContentView) fgView.findViewById(R.id.fund_input_city);
        fund_input_submit = (Button) fgView.findViewById(R.id.fund_input_submit);
    }

    @Override
    protected void initEvent() {
        fund_input_submit.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(model.getMenuName() == null ? "" : model.getMenuName());
        }

        fund_input_account.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.fund_input_submit:
                if (StringUtils.isBlank(fund_input_account.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.AddInputContent_MSG_1));
                    return;
                }

                if (StringUtils.isBlank(fund_input_city.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.FundInputMsg_1));
                    return;
                }
                model.setAmount(fund_input_account.getContentVaule());
                model.setMenuName(fund_input_city.getContentVaule() + AppConfig.getInstance().NAME_PART_LINE + getString(R.string.fund));

                CommonUtils.getInstance().insertDB(model);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, model);
                getActivity().finish();
                break;
        }
    }
}
