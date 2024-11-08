package com.example.dahugrape.database.repositories;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dahugrape.database.AppRoomDatabase;
import com.example.dahugrape.database.model.PayMode;

import java.util.List;

public class PayModeRepo {

    private MutableLiveData<List<PayMode>> mutablePayModeLiveData;

    public LiveData<List<PayMode>> getPayModes(Activity activity) {
        if (mutablePayModeLiveData == null) {
            mutablePayModeLiveData = new MutableLiveData<>();
            loadPayModes(activity);
        }
        return mutablePayModeLiveData;
    }

    // 如果不用roomDatabase也可以直接在这里载入数据
    // 例如 : grapeList.add(new Grape(...))
    private void loadPayModes(Activity activity) {
        // 把数据设置到可观测list上
        mutablePayModeLiveData.setValue(AppRoomDatabase.getInstance(activity).payModeDao().getAllPayModes()); // 从数据库获取数据 单例模式
    }
}
