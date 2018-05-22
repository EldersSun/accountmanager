package com.accountingmanager.Utils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.accountingmanager.R;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class DescHolder extends RecyclerView.ViewHolder {
    public TextView type_menuNmae;
    public TextView type_menuNumber;

    public DescHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        type_menuNmae = (TextView) itemView.findViewById(R.id.type_menuNmae);
        type_menuNumber = (TextView) itemView.findViewById(R.id.type_menuNumber);
    }
}
