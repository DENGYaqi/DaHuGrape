package com.example.dahugrape.database.repositories;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dahugrape.database.AppRoomDatabase;
import com.example.dahugrape.database.model.Client;

import java.util.List;

public class ClientRepo {

    private MutableLiveData<List<Client>> mutableClientLiveData;

    public LiveData<List<Client>> getClients(Activity activity) {
        if (mutableClientLiveData == null) {
            mutableClientLiveData = new MutableLiveData<>();
            loadClients(activity);
        }
        return mutableClientLiveData;
    }

    // 如果不用roomDatabase也可以直接在这里载入数据
    // 例如 : grapeList.add(new Grape(...))
    private void loadClients(Activity activity) {
        // 把数据设置到可观测list上
        mutableClientLiveData.setValue(AppRoomDatabase.getInstance(activity).clientDao().getAllClients()); // 从数据库获取数据 单例模式
    }
}
