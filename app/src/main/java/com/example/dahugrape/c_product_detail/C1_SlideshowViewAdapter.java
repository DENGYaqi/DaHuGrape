package com.example.dahugrape.c_product_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.dahugrape.R;

/**
 * 轮播图片的适配器
 */
public class C1_SlideshowViewAdapter extends PagerAdapter {

    private static final String TAG = "C1_SlideshowViewAdapter";

    private final Integer[] images;
    private LayoutInflater layoutInflater;

    public C1_SlideshowViewAdapter(Integer[] images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.c1_item_slideshow, null);
        ImageView imageView = view.findViewById(R.id.c1_iv_cate_logo);
        imageView.setImageResource(images[position]);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
