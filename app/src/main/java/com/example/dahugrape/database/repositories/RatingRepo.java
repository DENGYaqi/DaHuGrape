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

public class RatingRepo {

    private MutableLiveData<List<Rating>> mutableRatingLiveData;

    public LiveData<List<Rating>> getRatings(Activity activity) {
        if (mutableRatingLiveData == null) {
            mutableRatingLiveData = new MutableLiveData<>();
            loadRatings(activity);
        }
        return mutableRatingLiveData;
    }

    // 如果不用roomDatabase也可以直接在这里载入数据
    // 例如 : grapeList.add(new Grape(...))
    private void loadRatings(Activity activity) {
        // 把数据设置到可观测list上
        mutableRatingLiveData.setValue(AppRoomDatabase.getInstance(activity).ratingDao().getAllRatings()); // 从数据库获取数据 单例模式
    }
}
