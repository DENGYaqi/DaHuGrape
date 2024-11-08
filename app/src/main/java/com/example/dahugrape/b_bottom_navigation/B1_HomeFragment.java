package com.example.dahugrape.b_bottom_navigation;

import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dahugrape.R;
import com.example.dahugrape.c_product_detail.C1_SlideshowViewAdapter;
import com.example.dahugrape.database.adapter.CategoryAdapter;
import com.example.dahugrape.database.adapter.GrapeAdapter;
import com.example.dahugrape.database.model.Category;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.example.dahugrape.databinding.B1FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class B1_HomeFragment extends Fragment implements GrapeAdapter.GrapeInterface {

    private static final String TAG = "B1_HomeFragment";
    final long DELAY_MS = 500; // 任务执行前的延迟，单位为毫秒
    final long PERIOD_MS = 3000; // 连续的任务执行之间的时间，以毫秒为单位。
    B1FragmentHomeBinding b1FragmentHomeBinding;
    // 类别
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    //轮播图
    C1_SlideshowViewAdapter c1_slideshowViewAdapter;
    int currentPage = 0;
    Timer timer;
    private GrapeAdapter grapeAdapter;
    private GrapeViewModel grapeViewModel;
    private NavController navController;


    public B1_HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b1FragmentHomeBinding = B1FragmentHomeBinding.inflate(inflater, container, false);

        // 进入offer的页面
        b1FragmentHomeBinding.b1TvFlashSaleMoreCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_home_to_navigation_offer);
            }
        });

        // 填充这个布局
        return b1FragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO 要设置显示的数量
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        b1FragmentHomeBinding.b1RvHomeCategory.setLayoutManager(linearLayoutManager);
        b1FragmentHomeBinding.b1RvHomeCategory.setItemAnimator(new DefaultItemAnimator());

        grapeAdapter = new GrapeAdapter(this);

        // 设置 GridLayout Manager 到 recyclerView
        b1FragmentHomeBinding.b1RvHomeFlashSale.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        b1FragmentHomeBinding.b1RvHomeFlashSale.setItemAnimator(new DefaultItemAnimator());

        b1FragmentHomeBinding.b1RvHomeFlashSale.setAdapter(grapeAdapter);

        // 在这里载入ViewModel
        grapeViewModel = new ViewModelProvider(requireActivity()).get(GrapeViewModel.class);
        // 获取仓库数据
        grapeViewModel.getGrapes(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Grape>>() {
            @Override
            public void onChanged(List<Grape> grapes) {
                grapeAdapter.submitList(grapes);
            }
        });

        super.onViewCreated(view, savedInstanceState);

        // 类别
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.iconfont_b1_sleep, "住宿"));
        categoryList.add(new Category(2, R.drawable.iconfont_b1_chateau, "一日游"));
        categoryList.add(new Category(3, R.drawable.iconfont_b1_gardan, "我的花园"));
        categoryList.add(new Category(4, R.drawable.iconfont_b1_buy, "网购"));
        categoryList.add(new Category(5, R.drawable.iconfont_b1_membership, "会员"));

        setCategoryRecycler(categoryList); // 设置类别

        // 轮播图
        Integer[] imagesGreenGrape = {R.drawable.lunbo1, R.drawable.lunbo2, R.drawable.lunbo3};
        c1_slideshowViewAdapter = new C1_SlideshowViewAdapter(imagesGreenGrape);
        b1FragmentHomeBinding.b1Vp.setAdapter(c1_slideshowViewAdapter);
        b1FragmentHomeBinding.b1Dot.setViewPager(b1FragmentHomeBinding.b1Vp);

        // 轮播图自动播放
        // 设置好适配器后，使用定时器
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4 - 1) {
                    currentPage = 0;
                }
                b1FragmentHomeBinding.b1Vp.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // 创建一个新进程
        timer.schedule(new TimerTask() { // 安排任务
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

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
                    navController.navigate(R.id.action_navigation_home_to_navigation_cart);
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
        navController.navigate(R.id.action_navigation_home_to_c1_DetailsGrapeFragment);
    }

    // 设置类别的Recycle View
    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        b1FragmentHomeBinding.b1RvHomeCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), categoryDataList);
        b1FragmentHomeBinding.b1RvHomeCategory.setAdapter(categoryAdapter);
    }

}