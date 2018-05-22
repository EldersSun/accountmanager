package com.accountingmanager.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Model.AssetsElementModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-Pc on 2017/4/18.
 */

public class AddAssetsMenuAdapter extends RecyclerView.Adapter<AddAssetsMenuAdapter.ViewHold>{
    private Context context;

    private List<AssetsElementModel> menuList = new ArrayList<>();

    public void setMenuList(List<AssetsElementModel> menuList) {
        this.menuList = menuList;
        this.notifyDataSetChanged();
    }

    public AddAssetsMenuAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHold viewHold = new ViewHold(LayoutInflater.from(context).inflate(R.layout.v_addassets_item_layout,null));
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, final int position) {
        holder.addAssets_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemRecyclerViewClickListener != null){
                    onItemRecyclerViewClickListener.onItemClickListener(holder.addAssets_item_layout,position);
                }
            }
        });
        if(menuList.get(position) == null){
            return;
        }
        AssetsElementModel assetsElementModel = menuList.get(position);
        holder.addAssets_item_imgShow.setImageResource(assetsElementModel.getMenuIcon());
        holder.addAssets_item_tvShow.setText(assetsElementModel.getMenuName());
    }

    @Override
    public int getItemCount() {
        return menuList == null ? 0 : menuList.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{
        private LinearLayout addAssets_item_layout;
        private ImageView addAssets_item_imgShow;
        private TextView addAssets_item_tvShow;

        public ViewHold(View itemView) {
            super(itemView);
            addAssets_item_layout = (LinearLayout) itemView.findViewById(R.id.addAssets_item_layout);
            addAssets_item_imgShow = (ImageView) itemView.findViewById(R.id.addAssets_item_imgShow);
            addAssets_item_tvShow = (TextView) itemView.findViewById(R.id.addAssets_item_tvShow);
        }
    }

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener){
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

}
