package com.example.dahugrape.database.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dahugrape.R;
import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.databinding.D1ItemCartBinding;
import com.google.android.material.snackbar.Snackbar;

public class CartAdapter extends ListAdapter<Cart, CartAdapter.CartVH> {

    private final CartInterface cartInterface;

    public CartAdapter(CartInterface cartInterface) {
        super(Cart.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 应用Data binding
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        D1ItemCartBinding cartBinding = D1ItemCartBinding.inflate(layoutInflater, parent, false);
        return new CartVH(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        holder.d1ItemCartBinding.setCart(getItem(position));
        holder.d1ItemCartBinding.executePendingBindings();
    }

    public interface CartInterface {
        void deleteItem(Cart cart); // 删除商品

        boolean minusItem(Grape grape); // 减少商品数量

        boolean plusItem(Grape grape); // 增加商品数量 用的是GrapeInterface内addItemToCart的函数
    }

    class CartVH extends RecyclerView.ViewHolder {

        D1ItemCartBinding d1ItemCartBinding;

        public CartVH(@NonNull D1ItemCartBinding d1ItemCartBinding) {
            super(d1ItemCartBinding.getRoot());
            this.d1ItemCartBinding = d1ItemCartBinding;

            // 删除按钮
            d1ItemCartBinding.d1ImgBntDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAbsoluteAdapterPosition()));
                }
            });

            // TODO 下面两个函数是一样的 看看如何简化
            // 减少产品按钮
            d1ItemCartBinding.d1ImgBntMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String leastProduct = v.getContext().getString(R.string.cart_adapter_least_Product);
                    if (cartInterface.minusItem(getItem(getAbsoluteAdapterPosition()).getGrape())) { // 如果返回true 则表示还能减少数量 就-1
                        notifyDataSetChanged(); // 为的是让价格的标签 和 数量的标签 同步改变
                    } else { // 不能再减少就显示信息 "最少的产品数量是1"
                        Snackbar.make(itemView, leastProduct, Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            // 增加按钮
            d1ItemCartBinding.d1ImgBntPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String maxProduct = v.getContext().getString(R.string.cart_adapter_max_Product);
                    if (cartInterface.plusItem(getItem(getAbsoluteAdapterPosition()).getGrape())) { // // 如果返回true 则表示还能增加数量 就+1
                        notifyDataSetChanged(); // 为的是让价格的标签 和 数量的标签 同步改变
                    } else { // 不能再增加就显示信息 "最大的产品数量是10"
                        Snackbar.make(itemView, maxProduct, Snackbar.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}
