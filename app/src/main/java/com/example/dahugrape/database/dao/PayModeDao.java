package com.example.dahugrape.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dahugrape.database.model.PayMode;

import java.util.List;

@Dao
public interface PayModeDao {

    @Insert
    void insertPayMode(PayMode payMode);

    @Query("SELECT * FROM pay_mode_table")
    List<PayMode> getAllPayModes();

    @Query("SELECT * FROM pay_mode_table WHERE pay_mode_id LIKE :payModeId")
    PayMode findCartById(int payModeId);

    @Delete
    void deletePayMode(PayMode payMode);

    @Update
    void updatePayMode(PayMode payMode);

    @Insert
    void insertMultiplePayModes(List<PayMode> payModeList);
}
