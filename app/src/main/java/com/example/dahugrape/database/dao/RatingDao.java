package com.example.dahugrape.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dahugrape.database.model.Rating;

import java.util.List;

@Dao
public interface RatingDao {

    @Insert
    void insertRating(Rating rating);

    @Query("SELECT * FROM rating_table")
    List<Rating> getAllRatings();

    @Query("SELECT * FROM rating_table WHERE rating_id LIKE :ratingId")
    Rating findRatingById(int ratingId);

    @Delete
    void deleteRating(Rating rating);

    @Update
    void updateRating(Rating rating);

    @Insert
    void insertMultipleRatings(List<Rating> ratingList);
}
