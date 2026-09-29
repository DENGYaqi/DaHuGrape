package com.example.dahugrape.c_product_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.dahugrape.R;
import com.example.dahugrape.database.adapter.GrapeAdapter;
import com.example.dahugrape.database.adapter.RatingAdapter;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.example.dahugrape.database.viewmodels.RatingViewModel;
import com.example.dahugrape.databinding.C1FragmentDetailsGrapeBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class C1_DetailsGrapeFragment extends Fragment implements RatingAdapter.RatingInterface{

    private static final String TAG = "C1_DetailsGrapeFragment";

    C1FragmentDetailsGrapeBinding c1FragmentDetailsGrapeBinding;

    GrapeViewModel grapeViewModel;
    //轮播图
    C1_SlideshowViewAdapter c1_view_adapter;
    // 属性选择 : 种类和甜度
    String choice_category;
    String choice_sweetness;
    private NavController navController;
    // 显示评论
    private RatingAdapter ratingAdapter;
    private RatingViewModel ratingViewModel;

    public C1_DetailsGrapeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c1FragmentDetailsGrapeBinding = C1FragmentDetailsGrapeBinding.inflate(inflater, container, false);
        return c1FragmentDetailsGrapeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grapeViewModel = new ViewModelProvider(requireActivity()).get(GrapeViewModel.class);
        c1FragmentDetailsGrapeBinding.setGrapeViewModel(grapeViewModel);
        navController = Navigation.findNavController(view);

        // 浏览所有评论
        ratingViewModel = new ViewModelProvider(requireActivity()).get(RatingViewModel.class);
        c1FragmentDetailsGrapeBinding.setRatingViewModel(ratingViewModel);
        ratingAdapter = new RatingAdapter(this); // 因为没有implements rating interface

        ratingViewModel.getRatings(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Rating>>() {
            @Override
            public void onChanged(List<Rating> ratingList) { // TODO 这里要根据不同的葡萄 显示不同的评论
                ratingAdapter.submitList(ratingList);
            }
        });

        // setAdapter要在上面载入数据之后
        c1FragmentDetailsGrapeBinding.c1Review.setAdapter(ratingAdapter);

        // 轮播图
        // 葡萄名字
        String red_grape = view.getContext().getString(R.string.red_grape);
        String green_grape = view.getContext().getString(R.string.green_grape);
        String violet_grape = view.getContext().getString(R.string.violet_grape);
        String black_grape = view.getContext().getString(R.string.black_grape);
        String red_wine = view.getContext().getString(R.string.red_wine);
        String white_wine = view.getContext().getString(R.string.white_wine);
        String champagne = view.getContext().getString(R.string.champagne);

        // 从grapeViewModel获取当前葡萄
        // TODO 已经可以简化了
        LiveData<Grape> grape = grapeViewModel.getGrape();
        grape.observe(getActivity(), new Observer<Grape>() {
            @Override
            public void onChanged(Grape grape) {

                c1FragmentDetailsGrapeBinding.c1SeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navController.navigate(R.id.action_c1_DetailsGrapeFragment_to_c2_ReviewProductFragment);
                    }
                });

                // 属性选择 : 种类和甜度
                c1FragmentDetailsGrapeBinding.c1CgCategory.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup group, int checkedId) {
                        Chip chip = group.findViewById(checkedId);
                        if (chip != null) { // 确认点击不为空
                            String string_moldova = getContext().getString(R.string.moldova);
                            String string_shine_muscat = getContext().getString(R.string.shine_muscat);
                            if (chip.getText().equals(string_moldova)) {
                                choice_category = string_moldova; // 这步有点多余
                            } else {
                                choice_category = string_shine_muscat;
                            }
                            grape.setCategory(choice_category);
                        }
                    }
                });

                c1FragmentDetailsGrapeBinding.c1CgSweetness.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup group, int checkedId) {
                        Chip chip = group.findViewById(checkedId);
                        if (chip != null) { // 确认点击不为空
                            String string_sweetness = getContext().getString(R.string.sweet);
                            String string_super_sweetness = getContext().getString(R.string.super_sweet);
                            String string_extra_sweetness = getContext().getString(R.string.extra_sweet);
                            if (chip.getText().equals(string_sweetness)) {
                                choice_sweetness = string_sweetness;
                            } else if (chip.getText().equals(string_super_sweetness)) {
                                choice_sweetness = string_super_sweetness;
                            } else {
                                choice_sweetness = string_extra_sweetness;
                            }
                            grape.setSweetness(choice_sweetness);
                        }
                    }
                });

                // 轮播图
                if (grape.getName().equals(red_grape)) { // 按照葡萄名称匹配相对应的轮播图
                    Integer[] imagesBlackGrape = {R.drawable.c1_red_grape};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesBlackGrape);
                } else if (grape.getName().equals(green_grape)) {
                    Integer[] imagesGreenGrape = {R.drawable.c1_green_grape, R.drawable.c1_green_grape1, R.drawable.c1_green_grape2};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesGreenGrape);
                } else if (grape.getName().equals(violet_grape)) {
                    Integer[] imagesVioletGrape = {R.drawable.c1_violet_grape, R.drawable.c1_violet_grape1};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesVioletGrape);
                } else if (grape.getName().equals(black_grape)) {
                    Integer[] imagesBlackGrape = {R.drawable.c1_black_grape, R.drawable.c1_black_grape1};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesBlackGrape);
                } else if (grape.getName().equals(red_wine)) {
                    Integer[] imagesBlackGrape = {R.drawable.c1_red_wine};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesBlackGrape);
                } else if (grape.getName().equals(white_wine)) {
                    Integer[] imagesBlackGrape = {R.drawable.c1_white_wine};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesBlackGrape);
                } else if (grape.getName().equals(champagne)) {
                    Integer[] imagesBlackGrape = {R.drawable.c1_champagne};
                    c1_view_adapter = new C1_SlideshowViewAdapter(imagesBlackGrape);
                }
                // 应用到View Pager和Dot上
                c1FragmentDetailsGrapeBinding.c1VpGrapeDetails.setAdapter(c1_view_adapter);
                c1FragmentDetailsGrapeBinding.c1DotGrapeDetails.setViewPager(c1FragmentDetailsGrapeBinding.c1VpGrapeDetails);
            }
        });
    }

}
