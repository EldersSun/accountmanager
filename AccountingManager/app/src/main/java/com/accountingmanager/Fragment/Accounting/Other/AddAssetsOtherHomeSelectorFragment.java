package com.accountingmanager.Fragment.Accounting.Other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;

/**
 * Created by Home-Pc on 2017/5/17.
 */

public class AddAssetsOtherHomeSelectorFragment extends ContentBaseFragment implements View.OnClickListener{

    private RadioButton addAssets_other_simple,addAssets_other_senior;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_addassets_other_home_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        addAssets_other_simple = (RadioButton) fgView.findViewById(R.id.addAssets_other_simple);
        addAssets_other_senior = (RadioButton) fgView.findViewById(R.id.addAssets_other_senior);
    }

    @Override
    protected void initEvent() {
        addAssets_other_simple.setOnClickListener(this);
        addAssets_other_senior.setOnClickListener(this);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        pushFragmentController(R.id.addAssets_other_content,new AddAssetsOtherSimpleFragment(),bundle,false);

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
            case R.id.addAssets_other_simple:
                pushFragmentController(R.id.addAssets_other_content,new AddAssetsOtherSimpleFragment(),bundle,false);
                break;
            case R.id.addAssets_other_senior:
                pushFragmentController(R.id.addAssets_other_content,new AddAssetsOtherSeniorFragment(),bundle,false);
                break;
        }
    }
}
