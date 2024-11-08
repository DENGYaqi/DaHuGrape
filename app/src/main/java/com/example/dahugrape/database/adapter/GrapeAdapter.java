package com.example.dahugrape.database.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.databinding.B1B2GrapeItemBinding;

public class GrapeAdapter extends ListAdapter<Grape, GrapeAdapter.GrapeViewHolder> {

    GrapeInterface grapeInterface;

    public GrapeAdapter(GrapeInterface grapeInterface) {
        super(Grape.itemCallback);
        this.grapeInterface = grapeInterface;
    }

    @NonNull
    @Override
    public GrapeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 载入视图 Data binding
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        B1B2GrapeItemBinding b1B2ItemGrapeBinding = B1B2GrapeItemBinding.inflate(layoutInflater, parent, false);
        // 应用interface
        b1B2ItemGrapeBinding.setGrapeInterface(grapeInterface);
        return new GrapeViewHolder(b1B2ItemGrapeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GrapeViewHolder holder, int position) {
        Grape grape = getItem(position);
        holder.ItemGrapeBinding.recentlyLayout.setBackgroundResource(grape.getImage()); // 设置图片
        holder.ItemGrapeBinding.recentlyLayout.setOnClickListener(new View.OnClickListener() { // 点击一次就显示蒙版
            @Override
            public void onClick(View v) {
                holder.ItemGrapeBinding.b1B2BackgroundGrid.setVisibility(View.VISIBLE); // 显示灰色蒙版
                holder.ItemGrapeBinding.b1B2BtnFastBuy.setVisibility(View.VISIBLE); // 显示快速购买
                holder.ItemGrapeBinding.b1B2BtnSeeDetail.setVisibility(View.VISIBLE); // 显示去看看

                holder.ItemGrapeBinding.b1B2BackgroundGrid.setOnClickListener(new View.OnClickListener() { // 点击一次蒙版就关掉
                    @Override
                    public void onClick(View v) { // 再点击一次蒙版就关掉
                        holder.ItemGrapeBinding.b1B2BackgroundGrid.setVisibility(View.GONE); // 关掉灰色蒙版
                        holder.ItemGrapeBinding.b1B2BtnFastBuy.setVisibility(View.GONE); // 关掉快速购买
                        holder.ItemGrapeBinding.b1B2BtnSeeDetail.setVisibility(View.GONE); // 关掉去看看
                    }
                });
            }
        });
        holder.ItemGrapeBinding.setGrape(grape);
        holder.ItemGrapeBinding.executePendingBindings();
    }

    public interface GrapeInterface {
        void addItem(Grape grape); // 添加葡萄

        void onItemClick(Grape grape); // 点击控制
    }

    class GrapeViewHolder extends RecyclerView.ViewHolder {

        B1B2GrapeItemBinding ItemGrapeBinding;

        public GrapeViewHolder(B1B2GrapeItemBinding binding) {
            super(binding.getRoot());
            this.ItemGrapeBinding = binding;
        }
    }
}
