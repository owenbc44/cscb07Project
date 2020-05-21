package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mProductPrices = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mProductNames, ArrayList<String> mProductPrices) {
        this.mProductNames = mProductNames;
        this.mProductPrices = mProductPrices;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(mProductNames.get(position));
        holder.productPrice.setText("$" + mProductPrices.get(position));

//        if (position == 0) {
//            int margin = (int) R.dimen.activity_horizontal_margin;
//            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.productParent.getLayoutParams();
//            lp.setMargins(0, margin + 20, 0, margin);
//            holder.productParent.setLayoutParams(lp);
//        } else if (position == mProductNames.size() - 1) {
//            int margin = (int) R.dimen.activity_horizontal_margin;
//            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.productParent.getLayoutParams();
//            lp.setMargins(0, margin, 0, margin + 70);
//            holder.productParent.setLayoutParams(lp);
//        }
    }

    @Override
    public int getItemCount() {
        return mProductNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        RelativeLayout productParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productParent = itemView.findViewById(R.id.product_parent);
        }
    }
}
