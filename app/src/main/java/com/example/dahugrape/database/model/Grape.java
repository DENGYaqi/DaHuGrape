package com.example.dahugrape.database.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dahugrape.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 葡萄产品信息
 */
@Entity(tableName = "grape_table")
public class Grape {
    // 主要是实现了submitList方法来归一化提交数据，这样会触发在子线程对比数据差异，然后再在主线程更新有差异化的数据，这里的差异化可分为：
    // 1. 更新列表中item变化的部分
    // 2. 更新item内部发生的部分变化
    public static DiffUtil.ItemCallback<Grape> itemCallback = new DiffUtil.ItemCallback<Grape>() {
        // areItemsTheSame用于判断两个item是否代表同一份信息，比如判断id是否一致：(oldItem.id == newItem.id)，如果返回false则会直接去更新ui
        @Override
        public boolean areItemsTheSame(@NonNull Grape oldItem, @NonNull Grape newItem) {
            return oldItem.getGrapeId() == newItem.getGrapeId();
        }

        // areContentsTheSame用于判断要显示的内容是否完全一致，areItemsTheSame返回true会进入此判断
        @Override
        public boolean areContentsTheSame(@NonNull Grape oldItem, @NonNull Grape newItem) {
            return oldItem.equals(newItem);
        }
        // getChangePayload 这是可选实现的，如果areItemsTheSame为true而areContentsTheSame为false时会触发此方法，用于返回之间有差异化的数据
    };
    @PrimaryKey(autoGenerate = true) // 每个实体都需要一个主键。为了简单起见，每个字都作为自己的主键。并且让 Room 为实体分配自动 ID。
    @ColumnInfo(name = "grape_id")
    private int grapeId; //葡萄序列号
    @NonNull // 表示一个参数、字段或方法的返回值永远不能为空。
    @ColumnInfo(name = "name") // 如果希望表中的列名与成员变量的名称不同，请指定它的名称。
    private String name; // 葡萄名字
    @NonNull
    @ColumnInfo(name = "image_url")
    private int image; // Home图片
    @NonNull
    @ColumnInfo(name = "imageOffer")
    private String imageOffer; // Offer图片
    @NonNull
    @ColumnInfo(name = "brief_description")
    private String brief_description; // 葡萄简介
    @NonNull
    @ColumnInfo(name = "description")
    private String description; // 葡萄描述
    @NonNull
    @ColumnInfo(name = "price_per_kilo")
    private Integer pricePerKilo; // 葡萄每公斤的价格
    @NonNull
    @ColumnInfo(name = "is_available")
    private boolean isAvailable; // 库存
    @NonNull
    @ColumnInfo(name = "type")
    private String category; // 葡萄种类
    @NonNull
    @ColumnInfo(name = "sweetness")
    private String sweetness; // 葡萄种类
    @Ignore
    private Rating rating; // 一个葡萄内有多个评论

    public Grape(@NonNull String name, @NonNull String imageOffer, @NonNull int image, @NonNull Integer pricePerKilo, @NonNull String brief_description, @NonNull String description, @NonNull boolean isAvailable, @NonNull String category, @NonNull String sweetness, @NonNull Rating rating) {
        this.name = name;
        this.imageOffer = imageOffer;
        this.image = image;
        this.pricePerKilo = pricePerKilo;
        this.brief_description = brief_description;
        this.description = description;
        this.isAvailable = isAvailable;
        this.category = category;
        this.sweetness = sweetness;
        this.rating = rating;
    }

    public Grape() {
    }

    // 给搜索框的
    @Ignore
    public Grape(String queryName) {
        this.name = queryName;
    }

    // 之后要用url的时候可以用
    @BindingAdapter("android:grapeImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        /** 使用Glide来管理图像 **/
        // 第一部分options，对于来自网络的图片来说是必须的，例如管理着错误。
        RequestOptions options = new RequestOptions()
                // 对图像进行居中和修剪
                //.centerCrop()
                // 错误时要显示的图像
                .error(R.mipmap.ic_launcher_round)
                // 默认显示的图像
                .placeholder(R.drawable.b1_image_grape_exemple_1);

        // 第二部分是用于显示在存储器中的图像。
        Glide.with(imageView)
                // 加载图像的url，其url存储在Firestore中或者网络上。
                .load(imageUrl)
                // 我们应用加载选项options
                .apply(options)
                // 调整大小和中心对齐
                .fitCenter()
                // 调整所有图像的大小，使其大小相同
                .override(500, 500)
                // 将图像切割成圆形
                //.circleCrop()
                // 管理缓存中的图片，提高显示效果。
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                // 显示图像的位置
                .into(imageView);
    }

    public int getGrapeId() {
        return grapeId;
    }

    public void setGrapeId(int grapeId) {
        this.grapeId = grapeId;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImageOffer() {
        return imageOffer;
    }

    public void setImageOffer(@NonNull String imageOffer) {
        this.imageOffer = imageOffer;
    }

    @NotNull
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @NotNull
    public Integer getPricePerKilo() {
        return pricePerKilo;
    }

    public void setPricePerKilo(@NonNull Integer pricePerKilo) {
        this.pricePerKilo = pricePerKilo;
    }

    @NonNull
    public String getBrief_description() {
        return brief_description;
    }

    public void setBrief_description(@NonNull String brief_description) {
        this.brief_description = brief_description;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String getSweetness() {
        return sweetness;
    }

    public void setSweetness(@NonNull String sweetness) {
        this.sweetness = sweetness;
    }

    @Override
    public String toString() {
        return "Grape{" +
                "grapeId=" + grapeId +
                ", name='" + name + '\'' +
                ", imageOffer='" + imageOffer + '\'' +
                ", image='" + image + '\'' +
                ", brief_description='" + brief_description + '\'' +
                ", description='" + description + '\'' +
                ", pricePerKilo=" + pricePerKilo +
                ", isAvailable=" + isAvailable +
                ", type='" + category + '\'' +
                ", sweetness='" + sweetness + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grape grape = (Grape) o;
        return getGrapeId() == grape.getGrapeId() &&
                isAvailable() == grape.isAvailable() &&
                getName().equals(grape.getName()) &&
                getImageOffer().equals(grape.getImageOffer()) &&
                getImage() == grape.getImage() &&
                getBrief_description().equals(grape.getBrief_description()) &&
                getDescription().equals(grape.getDescription()) &&
                getPricePerKilo().equals(grape.getPricePerKilo()) &&
                getCategory().equals(grape.getCategory()) &&
                getSweetness().equals(grape.getSweetness()) &&
                Objects.equals(getRating(), grape.getRating());
    }
}
