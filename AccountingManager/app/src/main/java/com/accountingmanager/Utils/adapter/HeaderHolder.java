package com.accountingmanager.Utils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.R;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    public TextView type_title_tvShow;
    public TextView type_title_open;
    public RelativeLayout type_title_layout;
    public ImageView type_title_icon;

    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        type_title_tvShow = (TextView) itemView.findViewById(R.id.type_title_tvShow);
        type_title_open = (TextView) itemView.findViewById(R.id.type_title_open);
        type_title_layout = (RelativeLayout) itemView.findViewById(R.id.type_title_layout);
        type_title_icon = (ImageView) itemView.findViewById(R.id.type_title_icon);
    }
}
