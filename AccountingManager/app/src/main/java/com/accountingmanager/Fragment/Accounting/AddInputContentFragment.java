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
import com.accountingmanager.Sys.Utils.ArrayUtils;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Sys.Widgets.CustomSelectorDialog;
import com.accountingmanager.Sys.Widgets.UiContentView.CashierInputFilter;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加输入数据
 * Created by Home-Pc on 2017/4/24.
 */

public class AddInputContentFragment extends ContentBaseFragment implements View.OnClickListener{
    private UiContentView addInputContent_inputValue, addInputContent_selector_type;
    private AssetsElementModel model;
    private CustomSelectorDialog dialog;
    private List<String> selectorList = new ArrayList<>();
    private Button addInputContent_selector_submit;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_addinputcontent_laytout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        addInputContent_inputValue = (UiContentView) fgView.findViewById(R.id.addInputContent_inputValue);
        addInputContent_selector_type = (UiContentView) fgView.findViewById(R.id.addInputContent_selector_type);
        addInputContent_selector_submit = (Button) fgView.findViewById(R.id.addInputContent_selector_submit);
    }

    @Override
    protected void initEvent() {
        addInputContent_selector_submit.setOnClickListener(this);
        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(model.getMenuName());
        }
        InputFilter[] filters = {new CashierInputFilter()};
        addInputContent_inputValue.setContentFilers(filters);

        selectorList = ArrayUtils.arrayToListForString(getResources().getStringArray(R.array.SelectorMoneyType));
        dialog = new CustomSelectorDialog(getActivity(),getString(R.string.selector_money_type),selectorList);

        addInputContent_selector_type.setOnContentClick(new UiContentView.OnContentClick() {
            @Override
            public void onContentClick(View v) {
                dialog.show();
            }
        });

        dialog.setOnDialogOperationclick(new CustomSelectorDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(String dateString) {
                addInputContent_selector_type.setContentVaule(dateString);
            }

            @Override
            public void cancel() {

            }
        });

        addInputContent_inputValue.setContentImgBitmap(ImgUtils.drawToTvShow(getString(R.string.element)));
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
            case R.id.addInputContent_selector_submit:
                if (StringUtils.isBlank(addInputContent_inputValue.getContentVaule())) {
                    ToastUtils.shortShow(getString(R.string.AddInputContent_MSG_1));
                    return;
                }
                model.setAmount(addInputContent_inputValue.getContentVaule());
                if(!StringUtils.isBlank(addInputContent_selector_type.getContentVaule())){
                    model.setMenuName(model.getMenuName() + AppConfig.getInstance().NAME_PART_LINE + addInputContent_selector_type.getContentVaule());
                }
                CommonUtils.getInstance().insertDB(model);
                sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, model);
                getActivity().finish();
                break;
        }
    }
}
