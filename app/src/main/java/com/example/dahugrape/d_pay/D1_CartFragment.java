package com.example.dahugrape.d_pay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.dahugrape.R;
import com.example.dahugrape.database.adapter.CartAdapter;
import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.example.dahugrape.databinding.D1FragmentCartBinding;

import java.util.List;

/**
 * 购物车
 */
public class D1_CartFragment extends Fragment implements CartAdapter.CartInterface {

    private static final String TAG = "D1_CartFragment";

    GrapeViewModel grapeViewModel;

    D1FragmentCartBinding d1FragmentCartBinding;

    private NavController navController;

    public D1_CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        d1FragmentCartBinding = D1FragmentCartBinding.inflate(inflater, container, false);
        return d1FragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        CartAdapter cartAdapter = new CartAdapter(this);
        d1FragmentCartBinding.d1RvCartShows.setAdapter(cartAdapter);

        grapeViewModel = new ViewModelProvider(requireActivity()).get(GrapeViewModel.class);
        // 葡萄Live Data
        grapeViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                cartAdapter.submitList(carts);
                d1FragmentCartBinding.d1BtnAddToChoiceConsignee.setEnabled(carts.size() > 0); // 如果购物车内数量不为0 则可以按下这个按钮
                // 如果购物车没有货物 就显示空标签
                if (carts.size() == 0) {
                    d1FragmentCartBinding.d1HintEmptyCart.setVisibility(View.VISIBLE);
                } else {
                    d1FragmentCartBinding.d1HintEmptyCart.setVisibility(View.GONE);
                }
            }
        });

        // 总价格Live Data
        grapeViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                d1FragmentCartBinding.d1AmountItems.setText("$" + aDouble.toString()); // 所有产品的价格
                double shippingPrice = 40.0; // TODO 运费
                d1FragmentCartBinding.d1AmountShipping.setText("$" + shippingPrice);
                double totalPrice = aDouble + shippingPrice; // 总价格 = 所有产品价格 + 运费
                d1FragmentCartBinding.d1AmountTotalPrice.setText("$" + totalPrice);
            }
        });

        d1FragmentCartBinding.d1BtnAddToChoiceConsignee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_cart_to_e3_OrderFragment);
            }
        });
    }

    @Override
    public void deleteItem(Cart cart) { // 删除产品
        //Log.d(TAG, "deleteItem: " + cart.getGrape().getGra_name());
        grapeViewModel.removeItemFromCart(cart);
    }

    @Override
    public boolean minusItem(Grape grape) { // 减少产品
        //Log.d(TAG, "minusItem: " + grape.getGra_name());
        return grapeViewModel.minusItemInCart(grape);
    }

    @Override
    public boolean plusItem(Grape grape) { // 增加产品
        //Log.d(TAG, "plusItem: " + grape.getGra_name());
        return grapeViewModel.addItemToCart(grape);
    }
}