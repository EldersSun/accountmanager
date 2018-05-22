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
import com.accountingmanager.Sys.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-Pc on 2017/4/20.
 */

public class ExpirAdapter extends RecyclerView.Adapter<ExpirAdapter.ViewHold>{
    private Context context;
    private List<AssetsElementModel> adapterList = new ArrayList<>();

    public void setAdapterList(List<AssetsElementModel> adapterList) {
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }
    public ExpirAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHold hold = new ViewHold(LayoutInflater.from(context).inflate(R.layout.v_expir_item_layout,null));
        return hold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, final int position) {
        holder.expire_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemRecyclerViewClickListener != null) {
                    onItemRecyclerViewClickListener.onItemClickListener(holder.expire_item_layout, position);
                }
            }
        });
        AssetsElementModel model = adapterList.get(position);

        holder.expire_item_content_typeName.setText(model.getMenuName() == null ? "" : model.getMenuName());
        holder.expire_item_content_typeNum.setText(model.getAmount() == null ? "" : model.getAmount());
        if(StringUtils.isBlank(model.getType())){
            return;
        }
        if (model.getType().equals("1")) {
            holder.expire_item_content_imgShow.setImageResource(R.drawable.expire_back_1);
            holder.expire_item_tvShow.setTextColor(context.getResources().getColor(R.color.type_classic_color_1));
            holder.expire_item_content_typeName.setTextColor(context.getResources().getColor(R.color.type_classic_color_1));
            holder.expire_item_content_typeNum.setTextColor(context.getResources().getColor(R.color.type_classic_color_1));
        } else if (model.getType().equals("2") || model.getType().equals("3")) {
            holder.expire_item_content_imgShow.setImageResource(R.drawable.expire_back_2);
            holder.expire_item_tvShow.setTextColor(context.getResources().getColor(R.color.type_classic_color_2));
            holder.expire_item_content_typeName.setTextColor(context.getResources().getColor(R.color.type_classic_color_2));
            holder.expire_item_content_typeNum.setTextColor(context.getResources().getColor(R.color.type_classic_color_2));
        } else if (model.getType().equals("4")) {
            holder.expire_item_content_imgShow.setImageResource(R.drawable.expire_back_3);
            holder.expire_item_tvShow.setTextColor(context.getResources().getColor(R.color.type_classic_color_3));
            holder.expire_item_content_typeName.setTextColor(context.getResources().getColor(R.color.type_classic_color_3));
            holder.expire_item_content_typeNum.setTextColor(context.getResources().getColor(R.color.type_classic_color_3));
        } else if (model.getType().equals("5")) {
            holder.expire_item_content_imgShow.setImageResource(R.drawable.expire_back_4);
            holder.expire_item_tvShow.setTextColor(context.getResources().getColor(R.color.type_classic_color_4));
            holder.expire_item_content_typeName.setTextColor(context.getResources().getColor(R.color.type_classic_color_4));
            holder.expire_item_content_typeNum.setTextColor(context.getResources().getColor(R.color.type_classic_color_4));
        }
    }

    @Override
    public int getItemCount() {
        return adapterList == null ? 0 : adapterList.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{
        private LinearLayout expire_item_layout;
        private ImageView expire_item_content_imgShow;
        private TextView expire_item_tvShow;
        private TextView expire_item_content_typeName;
        private TextView expire_item_content_typeNum;

        public ViewHold(View itemView) {
            super(itemView);
            expire_item_layout = (LinearLayout) itemView.findViewById(R.id.expire_item_layout);
            expire_item_content_imgShow = (ImageView) itemView.findViewById(R.id.expire_item_content_imgShow);
            expire_item_tvShow = (TextView) itemView.findViewById(R.id.expire_item_tvShow);
            expire_item_content_typeName = (TextView) itemView.findViewById(R.id.expire_item_content_typeName);
            expire_item_content_typeNum = (TextView) itemView.findViewById(R.id.expire_item_content_typeNum);
        }
    }

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;
}
