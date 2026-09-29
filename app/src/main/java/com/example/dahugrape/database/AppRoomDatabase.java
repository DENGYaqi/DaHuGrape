package com.example.dahugrape.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dahugrape.R;
import com.example.dahugrape.database.dao.CartDao;
import com.example.dahugrape.database.dao.ClientDao;
import com.example.dahugrape.database.dao.GrapeDao;
import com.example.dahugrape.database.dao.OrderDao;
import com.example.dahugrape.database.dao.PayModeDao;
import com.example.dahugrape.database.dao.RatingDao;
import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Client;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Order;
import com.example.dahugrape.database.model.PayMode;
import com.example.dahugrape.database.model.Rating;

import java.util.concurrent.Executors;

// Room的数据库类必须是抽象和扩展的RoomDatabase。
// 你用@Database注释该类是一个Room数据库，并使用注释参数声明属于数据库的实体并设置版本号。每个实体对应于一个将在数据库中创建的表。
// 数据库迁移超出了本代码实验室的范围，所以我们在这里将exportSchema设置为false，以避免构建警告。
// 在真正的应用程序中，你应该考虑为Room设置一个目录，用于导出模式，这样你就可以将当前的模式检查到你的版本控制系统中。

/**
 * 在SQLite数据库之上的一个数据库层。每个实体对应于一个将在数据库中创建的表。
 * 如果出现错误 使用reBuild
 */
@Database(entities = {
        Cart.class,
        Grape.class,
        Client.class,
        Order.class,
        PayMode.class,
        Rating.class,
}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    //定义数据库名称
    private static final String DATABASE_NAME = "AppRoomDatabase";
    // 定义了一个单例模式(singleton)，ConsigneeRoomDatabase，以防止同时打开多个数据库实例。
    private static volatile AppRoomDatabase INSTANCE;

    /**
     * 它将在第一次访问时创建数据库，
     * 使用Room的数据库构建器在应用context中从ConsigneeRoomDatabase类中创建一个RoomDatabase对象，并将其命名为 "AppRoomDatabase"。
     *
     * @param context 应用context
     * @return 返回一个单例
     */
    public static AppRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
                return INSTANCE;
            }
        }
        return INSTANCE;
    }

    // 预填充数据
    private static AppRoomDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppRoomDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                // 基础类先创建 : Rating, PayMode, Consignee

                                // Rating
                                String comment1 = "Flipping the cassette while reading/examining the fold-out cover";
                                String comment2 = "It's very moisturizing to drinking. This grape is very good.";
                                String comment3 = "Soo great!!!";

                                Rating rating1 = new Rating(4.5f, comment1, "20/01/2021");
                                getInstance(context).ratingDao().insertRating(rating1);
                                Rating rating2 = new Rating(5f, comment2, "03/10/2020");
                                getInstance(context).ratingDao().insertRating(rating2);
                                Rating rating3 = new Rating(3.8f, comment3, "18/01/2009");
                                getInstance(context).ratingDao().insertRating(rating3);

                                // PayMode
                                PayMode payMode = new PayMode("WeChat", "Card", "AliPay");

                                // TODO 新建不了 不知道为什么
                                // Consignee consignee = new Consignee("deng", "123", "province", "city", "district", 123456, "addressDetail");
                                // getInstance(context).consigneeDao().insertConsignee(consignee);

                                // 单关系和多关系的创建 : Cart, Grape; Client, Order;
                                // Cart 需要空的构造器

                                // Grape
                                // 葡萄名字
                                String red_grape = context.getString(R.string.red_grape);
                                String green_grape = context.getString(R.string.green_grape);
                                String violet_grape = context.getString(R.string.violet_grape);
                                String black_grape = context.getString(R.string.black_grape);
                                String red_wine = context.getString(R.string.red_wine);
                                String white_wine = context.getString(R.string.white_wine);
                                String champagne = context.getString(R.string.champagne);

                                // 葡萄描述
                                String description_red_grape = context.getString(R.string.description_red_grape);
                                String description_green_grape = context.getString(R.string.description_green_grape);
                                String description_violet_grape = context.getString(R.string.description_violet_grape);
                                String description_black_grape = context.getString(R.string.description_black_grape);
                                String description_red_wine = context.getString(R.string.description_red_wine);
                                String description_white_wine = context.getString(R.string.description_white_wine);
                                String description_champagne = context.getString(R.string.description_champagne);

                                // 葡萄简介
                                String brief_description_red_grape = context.getString(R.string.brief_description_red_grape);
                                String brief_description_green_grape = context.getString(R.string.brief_description_green_grape);
                                String brief_description_violet_grape = context.getString(R.string.brief_description_violet_grape);
                                String brief_description_black_grape = context.getString(R.string.brief_description_black_grape);
                                String brief_description_red_wine = context.getString(R.string.brief_description_red_wine);
                                String brief_description_white_wine = context.getString(R.string.brief_description_whit_wine);
                                String brief_description_champagne = context.getString(R.string.brief_description_champagne);

                                // 葡萄种类 默认是第一个
                                String moldova = context.getString(R.string.moldova);
                                String shine_muscat = context.getString(R.string.shine_muscat);
                                // 葡萄甜度 默认是第一个
                                String sweet = context.getString(R.string.sweet);
                                String super_sweet = context.getString(R.string.super_sweet);
                                String extra_sweet = context.getString(R.string.extra_sweet);

                                // 为了评论区获取一个葡萄的所有评论
                                Grape redGrape = new Grape(red_grape, "https://i.ibb.co/FKc9hDM/b1-b2-red-grape630.png", R.drawable.b1_b2_red_grape630, 100, brief_description_red_grape, description_red_grape, true, moldova, sweet, rating1);
                                getInstance(context).grapeDao().insertGrape(redGrape);
                                Grape greenGrape = new Grape(green_grape, "https://i.ibb.co/xFTbGB0/b1-b2-green-grape630.png", R.drawable.b1_b2_green_grape630, 200, brief_description_green_grape, description_green_grape, true, moldova, sweet, rating1);
                                getInstance(context).grapeDao().insertGrape(greenGrape);
                                Grape violetGrape = new Grape(violet_grape, "https://i.ibb.co/tCXGVKZ/b1-b2-violet-grape630.png", R.drawable.b1_b2_violet_grape630, 300, brief_description_violet_grape, description_violet_grape, true, moldova, sweet, rating2);
                                getInstance(context).grapeDao().insertGrape(violetGrape);
                                Grape blackGrape = new Grape(black_grape, "https://i.ibb.co/QvQLhY0/b1-b2-black-grape630.png", R.drawable.b1_b2_black_grape630, 400, brief_description_black_grape, description_black_grape, true, moldova, sweet, rating2);
                                getInstance(context).grapeDao().insertGrape(blackGrape);
                                Grape redWine = new Grape(red_wine, "https://i.ibb.co/gMYcJHG/b1-b2-red-wine630.png", R.drawable.b1_b2_red_wine630, 500, brief_description_red_wine, description_red_wine, true, moldova, sweet, rating2);
                                getInstance(context).grapeDao().insertGrape(redWine);
                                Grape whiteWine = new Grape(white_wine, "https://i.ibb.co/NrcTCTS/b1-b2-white-wine630.png", R.drawable.b1_b2_white_wine630, 600, brief_description_white_wine, description_white_wine, true, moldova, sweet, rating3);
                                getInstance(context).grapeDao().insertGrape(whiteWine);
                                Grape champagneWine = new Grape(champagne, "https://i.ibb.co/rQDdkVd/b1-b2-champagne630.png", R.drawable.b1_b2_champagne630, 700, brief_description_champagne, description_champagne, true, moldova, sweet, rating3);
                                getInstance(context).grapeDao().insertGrape(champagneWine);

                                // Order
                                String[] states = {"ordered", "Payed", "Deliver goods", "In delivery", "Delivered"}; // 订单状态 : 已下单, 已付款, 已发货, 送货中, 已送达

                                // Client 到时候联系上FireBase
                            }
                        });
                    }
                })
                .build();
    }

    // 数据库通过一个抽象的 "getter "方法为每个@Dao暴露DAO。
    public abstract CartDao cartDao();

    public abstract GrapeDao grapeDao();

    public abstract ClientDao clientDao();

    public abstract PayModeDao payModeDao();

    public abstract RatingDao ratingDao();

    public abstract OrderDao orderDao();
}