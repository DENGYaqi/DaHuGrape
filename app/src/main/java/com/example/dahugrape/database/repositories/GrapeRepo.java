package com.example.dahugrape.database.repositories;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dahugrape.database.AppRoomDatabase;
import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;

import java.util.ArrayList;
import java.util.List;

// 载入roomDatabase 储存到可观测list内
public class GrapeRepo {

    private MutableLiveData<List<Grape>> mutableGrapeLiveData;

    // 通过名字搜索葡萄
    public Grape getGrapeByName(String name){
        if (mutableGrapeLiveData.getValue() == null) {
            // 如果检测到数据为空 则新建一个
            mutableGrapeLiveData.setValue(new ArrayList<>());
        }
        List<Grape> grapeList = new ArrayList<>(mutableGrapeLiveData.getValue());
        for(Grape grape : grapeList){ // 比对名字找到葡萄
            if(grape.getName().equals(name)){
                return grape;
            }
        }
        return new Grape(); // 如果没找到就新建一个葡萄
    }

    public LiveData<List<Grape>> getGrapes(Activity activity) {
        if (mutableGrapeLiveData == null) {
            mutableGrapeLiveData = new MutableLiveData<>();
            loadGrapes(activity);
        }
        return mutableGrapeLiveData;
    }

    // 如果不用roomDatabase也可以直接在这里载入数据
    // 例如 : grapeList.add(new Grape(...))
    private void loadGrapes(Activity activity) {
        // 把数据设置到可观测list上
        mutableGrapeLiveData.setValue(AppRoomDatabase.getInstance(activity).grapeDao().getAllGrapes()); // 从数据库获取数据 单例模式
    }

    // 加入新的评论
    public boolean addItemRating(Grape grape, Rating rating) {
        if (mutableGrapeLiveData.getValue() == null) {
            return false;
        }
        List<Grape> grapeList = new ArrayList<>(mutableGrapeLiveData.getValue());
        grape.setRating(rating); // 在相对应的葡萄 设置相对应的评论
        grapeList.add(grape); // 添加新葡萄(已经添加过评论的)
        mutableGrapeLiveData.setValue(grapeList); // 应用更新后的(删除项已被删除)
        return true;
    }
}
