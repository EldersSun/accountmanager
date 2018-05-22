package com.accountingmanager.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Model.ForeignContentModel;
import com.accountingmanager.Sys.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 外汇首页适配器
 * Created by Home-Pc on 2017/5/16.
 */

public class ForeignHomeSelectorAdapter extends RecyclerView.Adapter<ForeignHomeSelectorAdapter.ViewHold>{

    private List<ForeignContentModel> list = new ArrayList<>();

    private Context context;

    public ForeignHomeSelectorAdapter(Context context){
        this.context = context;
    }

    public void setApadpterData(List<ForeignContentModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHold viewHold = new ViewHold(LayoutInflater.from(context).inflate(R.layout.v_foreign_home_selector_item_layout,null));
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, final int position) {
        holder.foreign_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemRecyclerViewClickListener != null){
                    onItemRecyclerViewClickListener.onItemClickListener(holder.foreign_layout,position);
                }
            }
        });

        ForeignContentModel model = list.get(position);

        if(model == null){
            return ;
        }

        if(model.getImgResources() != 0){
            holder.foreign_icon.setImageResource(model.getImgResources());
        }

        if(!StringUtils.isBlank(model.getName())){
            holder.foreign_name.setText(model.getName());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{

        private RelativeLayout foreign_layout;
        private ImageView foreign_icon;
        private TextView foreign_name;

        public ViewHold(View itemView) {
            super(itemView);

            foreign_layout = (RelativeLayout) itemView.findViewById(R.id.foreign_layout);
            foreign_icon = (ImageView) itemView.findViewById(R.id.foreign_icon);
            foreign_name = (TextView) itemView.findViewById(R.id.foreign_name);
        }
    }


    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

}
