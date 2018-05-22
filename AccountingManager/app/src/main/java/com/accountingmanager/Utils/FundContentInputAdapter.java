package com.accountingmanager.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;

/**
 * Created by Home-Pc on 2017/5/9.
 */

public class FundContentInputAdapter extends RecyclerView.Adapter<FundContentInputAdapter.Viewhold> {
    private Context context;

    public FundContentInputAdapter(Context context){
        this.context = context;
    }


    @Override
    public Viewhold onCreateViewHolder(ViewGroup parent, int viewType) {
        Viewhold viewhold = new Viewhold(LayoutInflater.from(context).inflate(R.layout.v_fund_content_input_item_layout,null));
        return viewhold;
    }

    @Override
    public void onBindViewHolder(final Viewhold holder, final int position) {
        holder.fund_content_item_name.setText("基金名称");
        holder.fund_content_item_code.setText("基金代码");

        holder.fund_content_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemRecyclerViewClickListener != null){
                    onItemRecyclerViewClickListener.onItemClickListener(holder.fund_content_item_layout,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class Viewhold extends RecyclerView.ViewHolder{
        private TextView fund_content_item_name,fund_content_item_code;
        private LinearLayout fund_content_item_layout;

        public Viewhold(View itemView) {
            super(itemView);

            fund_content_item_name = (TextView) itemView.findViewById(R.id.fund_content_item_name);
            fund_content_item_code = (TextView) itemView.findViewById(R.id.fund_content_item_code);

            fund_content_item_layout = (LinearLayout) itemView.findViewById(R.id.fund_content_item_layout);

        }
    }

    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

}
