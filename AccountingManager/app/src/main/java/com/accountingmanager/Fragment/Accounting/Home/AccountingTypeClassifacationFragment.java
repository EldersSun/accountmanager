package com.accountingmanager.Fragment.Accounting.Home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Model.AssetsTypeElementModel;
import com.accountingmanager.Utils.adapter.HotelEntityAdapter;
import com.accountingmanager.Utils.adapter.SectionedSpanSizeLookup;
import com.accountingmanager.Utils.entity.HotelEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类视图
 * Created by Home-Pc on 2017/4/19.
 */

public class AccountingTypeClassifacationFragment extends BaseFragment {
    private RecyclerView v_RecyclerViewShow;
    private HotelEntityAdapter mAdapter;
    private List<AssetsTypeElementModel> adapterList = new ArrayList<>();

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.v_recyclerview_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        v_RecyclerViewShow = (RecyclerView) fgView.findViewById(R.id.v_RecyclerViewShow);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_2);
        mAdapter = new HotelEntityAdapter(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
        v_RecyclerViewShow.setLayoutManager(manager);
        v_RecyclerViewShow.setAdapter(mAdapter);
        getContents();
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
    public void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if (msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_2)) {
            adapterList.clear();
            getContents();
        }
    }
    private Map<String,List<AssetsElementModel>> map = new HashMap<>();

    /**
     * 获取数据源
     */
    private void getContents() {
        List<AssetsElementModel> list = CommonUtils.getInstance().contents();
        for (int i = 0; i < list.size(); i++) {//先添加总类别
            map.put(list.get(i).getMenuType(),new ArrayList<AssetsElementModel>());
        }

        for (int i = 0; i < list.size(); i++) {//根据类别添加元素
            if(map.containsKey(list.get(i).getMenuType())){
                map.get(list.get(i).getMenuType()).add(list.get(i));
            }
        }

        for(Map.Entry<String,List<AssetsElementModel>> entry : map.entrySet()){
            AssetsTypeElementModel model = new AssetsTypeElementModel();
            model.setGroupName(entry.getValue().get(0).getGroupName());
            model.setMenuIcon(entry.getValue().get(0).getGroupIcon());
            model.setType(entry.getValue().get(0).getType());
            model.setAssetsElementModelList(entry.getValue());
            adapterList.add(model);
        }
        mAdapter.setData(adapterList);
    }
}
