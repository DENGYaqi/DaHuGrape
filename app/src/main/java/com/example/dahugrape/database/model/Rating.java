package com.example.dahugrape.database.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "rating_table")
public class Rating {

    public static DiffUtil.ItemCallback<Rating> itemCallback = new DiffUtil.ItemCallback<Rating>() {
        @Override
        public boolean areItemsTheSame(@NonNull Rating oldItem, @NonNull Rating newItem) {
            return oldItem.ratingId == newItem.ratingId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rating oldItem, @NonNull Rating newItem) {
            return oldItem.equals(newItem);
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rating_id")
    private long ratingId;
    @NonNull
    @ColumnInfo(name = "stars")
    private float stars; // 评论星数
    @NonNull
    @ColumnInfo(name = "comment")
    private String comment; // 评论内容
    @NonNull
    @ColumnInfo(name = "date")
    private String date; // 评论时间

    public Rating(float stars, @NonNull String comment, @NonNull String date) {
        this.stars = stars;
        this.comment = comment;
        this.date = date;
    }

    @Ignore
    public Rating() {
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    public void setComment(@NonNull String comment) {
        this.comment = comment;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", stars=" + stars +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return getRatingId() == rating.getRatingId() &&
                Float.compare(rating.getStars(), getStars()) == 0 &&
                getComment().equals(rating.getComment()) &&
                getDate().equals(rating.getDate());
    }
}
