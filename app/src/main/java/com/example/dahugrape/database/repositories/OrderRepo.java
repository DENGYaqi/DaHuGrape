package com.example.dahugrape.database.repositories;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dahugrape.database.AppRoomDatabase;
import com.example.dahugrape.database.model.Order;

import java.util.List;

public class OrderRepo {

    private MutableLiveData<List<Order>> mutableOrderLiveData;

    public LiveData<List<Order>> getOrders(Activity activity) {
        if (mutableOrderLiveData == null) {
            mutableOrderLiveData = new MutableLiveData<>();
            loadOrders(activity);
        }
        return mutableOrderLiveData;
    }

    // 如果不用roomDatabase也可以直接在这里载入数据
    // 例如 : grapeList.add(new Grape(...))
    private void loadOrders(Activity activity) {
        // 把数据设置到可观测list上
        mutableOrderLiveData.setValue(AppRoomDatabase.getInstance(activity).orderDao().getAllOrders()); // 从数据库获取数据 单例模式
    }
}
