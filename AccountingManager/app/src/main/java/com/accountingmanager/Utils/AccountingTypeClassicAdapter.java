package com.accountingmanager.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-Pc on 2017/4/19.
 */

public class AccountingTypeClassicAdapter extends RecyclerView.Adapter<AccountingTypeClassicAdapter.ViewHold> {
    private Context context;

    private List<AssetsElementModel> adapterList = new ArrayList<>();

    public void setAdapterList(List<AssetsElementModel> adapterList) {
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }

    public AccountingTypeClassicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHold viewHold = new ViewHold(LayoutInflater.
                from(context).inflate(R.layout.v_type_classic_item_layout, null));
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, final int position) {
        holder.type_classic_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemRecyclerViewClickListener != null) {
                    onItemRecyclerViewClickListener.onItemClickListener(holder.type_classic_item_layout, position);
                }
            }
        });

        AssetsElementModel model = adapterList.get(position);

        holder.type_classic_item_Name.setText(model.getMenuName() == null ? "" : model.getMenuName());
        holder.type_classic_item_Number.setText(model.getAmount() == null ? "" : model.getAmount());

        if (StringUtils.isBlank(model.getType())) {
            return;
        }

        if (model.getType().equals("1")) {
            holder.type_classic_item_layout.setBackgroundResource(R.drawable.type_classic_item_color_1);
            holder.type_classic_item_Name.setTextColor(context.getResources().getColor(R.color.type_classic_color_1));
            holder.type_classic_item_Number.setTextColor(context.getResources().getColor(R.color.type_classic_color_1));
        } else if (model.getType().equals("2") || model.getType().equals("3")) {
            holder.type_classic_item_layout.setBackgroundResource(R.drawable.type_classic_item_color_2);
            holder.type_classic_item_Name.setTextColor(context.getResources().getColor(R.color.type_classic_color_2));
            holder.type_classic_item_Number.setTextColor(context.getResources().getColor(R.color.type_classic_color_2));
        } else if (model.getType().equals("4")) {
            holder.type_classic_item_layout.setBackgroundResource(R.drawable.type_classic_item_color_3);
            holder.type_classic_item_Name.setTextColor(context.getResources().getColor(R.color.type_classic_color_3));
            holder.type_classic_item_Number.setTextColor(context.getResources().getColor(R.color.type_classic_color_3));
        } else if (model.getType().equals("5")) {
            holder.type_classic_item_layout.setBackgroundResource(R.drawable.type_classic_item_color_4);
            holder.type_classic_item_Name.setTextColor(context.getResources().getColor(R.color.type_classic_color_4));
            holder.type_classic_item_Number.setTextColor(context.getResources().getColor(R.color.type_classic_color_4));
        }
    }

    @Override
    public int getItemCount() {
        return adapterList == null ? 0 : adapterList.size();
    }

    class ViewHold extends RecyclerView.ViewHolder {
        private RelativeLayout type_classic_item_layout;
        private TextView type_classic_item_Name;
        private TextView type_classic_item_Number;

        public ViewHold(View itemView) {
            super(itemView);
            type_classic_item_layout = (RelativeLayout) itemView.findViewById(R.id.type_classic_item_layout);
            type_classic_item_Name = (TextView) itemView.findViewById(R.id.type_classic_item_Name);
            type_classic_item_Number = (TextView) itemView.findViewById(R.id.type_classic_item_Number);
        }
    }

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;
}
