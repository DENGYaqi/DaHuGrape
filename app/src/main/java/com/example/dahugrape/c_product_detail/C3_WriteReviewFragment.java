package com.example.dahugrape.c_product_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dahugrape.R;
import com.example.dahugrape.database.adapter.GrapeAdapter;
import com.example.dahugrape.database.adapter.RatingAdapter;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.example.dahugrape.database.viewmodels.RatingViewModel;
import com.example.dahugrape.databinding.C3FragmentWriteReviewBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class C3_WriteReviewFragment extends Fragment implements RatingAdapter.RatingInterface {

    private static final String TAG = "C3_WriteReviewFragment";

    GrapeViewModel grapeViewModel;

    GrapeAdapter grapeAdapter;

    C3FragmentWriteReviewBinding c3FragmentWriteReviewBinding;

    private NavController navController;

    // 获取今天的日期
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String today;

    public C3_WriteReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c3FragmentWriteReviewBinding = C3FragmentWriteReviewBinding.inflate(inflater, container, false);
        return c3FragmentWriteReviewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        // 设置adapter 因为这里载入了interface 所以下面的addItem和onItemClick才可以使用
        grapeViewModel = new ViewModelProvider(requireActivity()).get(GrapeViewModel.class);
        navController = Navigation.findNavController(view);
    }


}