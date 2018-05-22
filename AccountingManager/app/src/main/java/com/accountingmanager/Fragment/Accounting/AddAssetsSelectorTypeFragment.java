package com.accountingmanager.Fragment.Accounting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;

/**
 * 输入类型选择界面
 * Created by Home-Pc on 2017/4/24.
 */

public class AddAssetsSelectorTypeFragment extends ContentBaseFragment implements View.OnClickListener {
    private ImageView addAssetsSelectorType_Manual_icon, addAssetsSelectorType_Auto_icon;
    private TextView addAssetsSelectorType_Manual_TvShow, addAssetsSelectorType_Manual_msg,
            addAssetsSelectorType_Auto_TvShow, addAssetsSelectorType_Auto_msg;

    private LinearLayout addAssetsSelectorType_Manual_layout, addAssetsSelectorType_Auto_layout;

    private String TAG = "";
    private AssetsElementModel model = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_addassets_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        addAssetsSelectorType_Manual_icon = (ImageView) fgView.findViewById(R.id.addAssetsSelectorType_Manual_icon);
        addAssetsSelectorType_Auto_icon = (ImageView) fgView.findViewById(R.id.addAssetsSelectorType_Auto_icon);

        addAssetsSelectorType_Manual_TvShow = (TextView) fgView.findViewById(R.id.addAssetsSelectorType_Manual_TvShow);
        addAssetsSelectorType_Manual_msg = (TextView) fgView.findViewById(R.id.addAssetsSelectorType_Manual_msg);
        addAssetsSelectorType_Auto_TvShow = (TextView) fgView.findViewById(R.id.addAssetsSelectorType_Auto_TvShow);
        addAssetsSelectorType_Auto_msg = (TextView) fgView.findViewById(R.id.addAssetsSelectorType_Auto_msg);

        addAssetsSelectorType_Manual_layout = (LinearLayout) fgView.findViewById(R.id.addAssetsSelectorType_Manual_layout);
        addAssetsSelectorType_Auto_layout = (LinearLayout) fgView.findViewById(R.id.addAssetsSelectorType_Auto_layout);
    }

    @Override
    protected void initEvent() {
        addAssetsSelectorType_Manual_layout.setOnClickListener(this);
        addAssetsSelectorType_Auto_layout.setOnClickListener(this);
        Bundle bundle = getArguments();
        /**
         * model
         */
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) &&
                bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            model = (AssetsElementModel) bundle.getSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(model.getMenuName() == null ? "" : model.getMenuName());
        }
        /**
         * tag
         */
        if (bundle.containsKey(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG) &&
                !StringUtils.isBlank(bundle.getString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG))) {
            TAG = bundle.getString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG);
        }

        /**
         * message
         */
        if (bundle.containsKey(AppConfig.getInstance().JUMP_SELECTOR_TYPE_CONTENT_TAG) &&
                !StringUtils.isBlank(bundle.getString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_CONTENT_TAG))) {
            addAssetsSelectorType_Manual_msg.setText(bundle.getString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_CONTENT_TAG));
        }
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
            case R.id.addAssetsSelectorType_Manual_layout:
                if (!StringUtils.isBlank(TAG)) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,model);
                    bundle.putBoolean(AppConfig.getInstance().ADD_ASSETS_SELECTOR_TYPE,false);
                    sendMessage(TAG, bundle);
                    getActivity().finish();
                }
                break;
            case R.id.addAssetsSelectorType_Auto_layout:
                break;
        }
    }
}
