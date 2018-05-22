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
 * 银行卡输入内容页面
 * Created by Home-Pc on 2017/4/26.
 */

public class BankInputContentFragment extends ContentBaseFragment implements View.OnClickListener {
    private UiContentView bank_input_content_card_balance, bank_input_content_card_number;
    private AssetsElementModel model;
    private Button bank_input_content_card_submit;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_bank_input_content_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        bank_input_content_card_balance = (UiContentView) fgView.findViewById(R.id.bank_input_content_card_balance);
        bank_input_content_card_number = (UiContentView) fgView.findViewById(R.id.bank_input_content_card_number);
        bank_input_content_card_submit = (Button) fgView.findViewById(R.id.bank_input_content_card_submit);
        InputFilter[] filters = {new CashierInputFilter()};
        bank_input_content_card_balance.setContentFilers(filters);
    }

    @Override
    protected void initEvent() {
        bank_input_content_card_submit.setOnClickListener(this);
        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(model.getMenuName());
        }

        bank_input_content_card_balance.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.bank_input_content_card_submit:
                if (StringUtils.isBlank(bank_input_content_card_balance.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.AddInputContent_MSG_1));
                    return;
                }
                model.setAmount(bank_input_content_card_balance.getContentVaule());
                if (!StringUtils.isBlank(bank_input_content_card_number.getContentVaule())) {
                    model.setMenuName(model.getMenuName() + AppConfig.getInstance().NAME_PART_LINE + bank_input_content_card_number.getContentVaule());
                }
                CommonUtils.getInstance().insertDB(model);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_8, model);
                getActivity().finish();
                break;
        }
    }
}
