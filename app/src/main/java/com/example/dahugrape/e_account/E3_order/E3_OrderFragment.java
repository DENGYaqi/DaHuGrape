package com.example.dahugrape.e_account.E3_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.dahugrape.R;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.example.dahugrape.databinding.E3FragmentOrderBinding;

public class E3_OrderFragment extends Fragment {

    E3FragmentOrderBinding e3FragmentOrderBinding;
    GrapeViewModel grapeViewModel;
    private NavController navController;

    public E3_OrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        e3FragmentOrderBinding = E3FragmentOrderBinding.inflate(inflater, container, false);
        return e3FragmentOrderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        grapeViewModel = new ViewModelProvider(requireActivity()).get(GrapeViewModel.class);

        e3FragmentOrderBinding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_e3_OrderFragment_to_navigation_home);
                // 重置购物车
                grapeViewModel.resetCart();
                // 创建订单
            }
        });
    }
}