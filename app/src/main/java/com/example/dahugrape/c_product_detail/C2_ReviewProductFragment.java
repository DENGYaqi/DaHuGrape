package com.example.dahugrape.c_product_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.dahugrape.databinding.C2FragmentReviewProductBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

// 按照B2_OfferFragment写
public class C2_ReviewProductFragment extends Fragment implements RatingAdapter.RatingInterface {

    private static final String TAG = "C2_ReviewFragment";

    C2FragmentReviewProductBinding c2FragmentReviewProductBinding;

    private RatingAdapter ratingAdapter;

    private RatingViewModel ratingViewModel;

    // 在不同Fragment之间跳转
    private NavController navController;

    public C2_ReviewProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c2FragmentReviewProductBinding = C2FragmentReviewProductBinding.inflate(inflater, container, false);
        return c2FragmentReviewProductBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置adapter 因为这里载入了interface 所以下面的addItem和onItemClick才可以使用
        ratingViewModel = new ViewModelProvider(requireActivity()).get(RatingViewModel.class);
        ratingAdapter = new RatingAdapter(this);
        ratingViewModel.getRatings(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Rating>>() {
            @Override
            public void onChanged(List<Rating> ratingList) {
                // 默认显示的评论为所有评论
                ratingAdapter.submitList(ratingList);
                ratingAdapter.notifyDataSetChanged();
                // 根据星级标签 设置显示的评论
                c2FragmentReviewProductBinding.c2CgSelectReview.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup group, int checkedId) {
                        ratingAdapter.submitList(selectReviewByStarts(group, checkedId, ratingList));
                        ratingAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        // 获取从C1传过来的Grape
        c2FragmentReviewProductBinding.c2BtnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_c2_ReviewProductFragment_to_c3_WriteReviewFragment);
            }
        });

        // 转去写评论的页面
        c2FragmentReviewProductBinding.c2BtnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_c2_ReviewProductFragment_to_c3_WriteReviewFragment);
            }
        });
        // 需要在sublime list之后再设置Adapter
        c2FragmentReviewProductBinding.c2RvReview.setAdapter(ratingAdapter);
        navController = Navigation.findNavController(view);
    }

    // 根据星级实时显示评论
    public List<Rating> selectReviewByStarts(ChipGroup group, int checkedId, List<Rating> allRatingList){
        List<Rating> dataListRatingByClass = new ArrayList<>(); // 初始化一个ArrayList 放选出星级的评论
        Chip chip = group.findViewById(checkedId);
        String noViews = getResources().getString(R.string.c2_no_views); // 空评论提示标签
        if (chip != null && allRatingList.size() > 0){ // 如果点击不为空 并且有评论 则 关掉空标签提示 显示 该星级的标签
            c2FragmentReviewProductBinding.c2TvEmptyReview.setVisibility(View.GONE);
            c2FragmentReviewProductBinding.c2CgSelectReview.setVisibility(View.VISIBLE);
            switch (checkedId){
                case R.id.c2_chip_1_star: // 如果选中1星标签 则实时显示所有的1星评论
                    dataListRatingByClass = new ArrayList<>();
                    for(Rating rating : allRatingList){
                        if(rating.getStars() < 2f){
                            dataListRatingByClass.add(rating);
                        }
                    }
                    return dataListRatingByClass;
                case R.id.c2_chip_2_star: // 如果选中2星标签 则实时显示所有的2星评论
                    dataListRatingByClass = new ArrayList<>();
                    for(Rating rating : allRatingList){
                        if(rating.getStars() >= 2f && rating.getStars() < 3f){
                            dataListRatingByClass.add(rating);
                        }
                    }
                    return dataListRatingByClass;
                case R.id.c2_chip_3_star: // 如果选中3星标签 则实时显示所有的3星评论
                    dataListRatingByClass = new ArrayList<>();
                    for(Rating rating : allRatingList){
                        if(rating.getStars() >= 3f && rating.getStars() < 4f){
                            dataListRatingByClass.add(rating);
                        }
                    }
                    return dataListRatingByClass;
                case R.id.c2_chip_4_star: // 如果选中4星标签 则实时显示所有的4星评论
                    dataListRatingByClass = new ArrayList<>();
                    for(Rating rating : allRatingList){
                        if(rating.getStars() >= 4f && rating.getStars() < 5f){
                            dataListRatingByClass.add(rating);
                        }
                    }
                    return dataListRatingByClass;
                case R.id.c2_chip_5_star: // 如果选中5星标签 则实时显示所有的5星评论
                    dataListRatingByClass = new ArrayList<>();
                    for(Rating rating : allRatingList){
                        if(rating.getStars() == 5f){
                            dataListRatingByClass.add(rating);
                        }
                    }
                    return dataListRatingByClass;
                case R.id.c2_chip_all_review: // 如果选中所有评论标签 则实时显示所有评论
                    return allRatingList;
            }
        }else if(allRatingList.size() == 0){ // 如果没有评论 就显示空标签提示 并且关掉 RecycleView
            c2FragmentReviewProductBinding.c2CgSelectReview.setVisibility(View.GONE);
            c2FragmentReviewProductBinding.c2TvEmptyReview.setVisibility(View.VISIBLE);
            c2FragmentReviewProductBinding.c2TvEmptyReview.setText(noViews);
            return allRatingList;
        }else { // 如果标签没有被点击 默认显示所有评论
            return allRatingList;
        }
        return allRatingList; // 剩下其他所有情况都显示 所有评论
    }
}