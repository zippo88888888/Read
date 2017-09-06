package com.official.read.weight.lu_recycler_view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.weight.lu_recycler_view.entity.Product;
import com.official.read.util.Toaster;

import java.util.List;

/**
 * 水平方向适配器
 */
public class HotsAdapter extends RecyclerView.Adapter<HotsAdapter.ProductViewHolder> {

    private List<Product> products;
    private Context context;

    public void setData(List<Product> products) {
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item_hot, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        Product product = products.get(position);
        holder.cover.setImageResource(product.coverResId);
        holder.title.setText(product.title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.makeText("水平方向" + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title;

        ProductViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
