package com.example.dahugrape.b_bottom_navigation;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dahugrape.R;
import com.example.dahugrape.database.adapter.GrapeAdapter;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.example.dahugrape.databinding.B2FragmentOfferBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * 折扣页面
 */
public class B2_OfferFragment extends Fragment implements GrapeAdapter.GrapeInterface {

    private static final String TAG = "B2_OfferFragment";

    B2FragmentOfferBinding b2FragmentOfferBinding;

    private GrapeAdapter grapeAdapter;

    private GrapeViewModel grapeViewModel;

    // 在不同Fragment之间跳转
    private NavController navController;

    public B2_OfferFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b2FragmentOfferBinding = B2FragmentOfferBinding.inflate(inflater, container, false);
        return b2FragmentOfferBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置adapter 因为这里载入了interface 所以下面的addItem和onItemClick才可以使用
        grapeAdapter = new GrapeAdapter(this);
        b2FragmentOfferBinding.b2RvGrape.setAdapter(grapeAdapter);
        b2FragmentOfferBinding.b2RvGrape.setLayoutManager(new GridLayoutManager(getContext(), 2));
        b2FragmentOfferBinding.b2RvGrape.setItemAnimator(new DefaultItemAnimator());
        // 设置recycleView之间的分隔符 就是比较好看 但是我自己已经有了 以后可以看看别的project需不需要
        // b2FragmentOfferBinding.b2RvGrape.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        // b2FragmentOfferBinding.b2RvGrape.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        // 在这里载入ViewModel
        grapeViewModel = new ViewModelProvider(requireActivity()).get(GrapeViewModel.class);
        // 获取仓库数据
        grapeViewModel.getGrapes(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Grape>>() {
            @Override
            public void onChanged(List<Grape> grapes) {
                grapeAdapter.submitList(grapes);
            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Grape grape) {
        boolean isAdded = grapeViewModel.addItemToCart(grape);
        Log.d(TAG, "addItem: " + grape.getName() + isAdded);
        String added = getContext().getString(R.string.b2_added_to_cart);
        String checkout = getContext().getString(R.string.b2_checkout);
        String maxQuantity = getContext().getString(R.string.b2_max_quantity_in_cart);
        if (isAdded) {
            Snackbar.make(requireView(), grape.getName() + added, Snackbar.LENGTH_LONG).setAction(checkout, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.action_navigation_offer_to_navigation_cart);
                }
            }).show();
        } else {
            Snackbar.make(requireView(), grape.getName() + maxQuantity, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(Grape grape) {
        // 查看data biding是否成功
        // Log.d(TAG, "onItemClick: " + grape.toString());
        grapeViewModel.setGrape(grape);
        navController.navigate(R.id.action_navigation_offer_to_c1_DetailsGrapeFragment);
    }

}