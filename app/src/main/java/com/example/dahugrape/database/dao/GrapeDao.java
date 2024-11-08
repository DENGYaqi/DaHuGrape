package com.example.dahugrape.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;

import java.util.List;
import java.util.RandomAccess;

@Dao
public interface GrapeDao {

    @Insert
    void insertGrape(Grape grape);

    @Query("SELECT * FROM grape_table")
    List<Grape> getAllGrapes();

    @Query("SELECT * FROM grape_table WHERE grape_id LIKE :grapeId")
    Grape findGrapeById(int grapeId);

    @Delete
    void deleteGrape(Grape grape);

    @Update
    void updateGrape(Grape grape);

    @Insert
    void insertMultipleGrapes(List<Grape> grapeList);
}
