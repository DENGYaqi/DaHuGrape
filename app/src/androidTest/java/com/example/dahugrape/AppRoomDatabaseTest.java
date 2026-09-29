package com.example.dahugrape;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dahugrape.database.AppRoomDatabase;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class AppRoomDatabaseTest extends TestCase {

    private AppRoomDatabase DB;
    private ConsigneeDao consigneeDao;
    private GrapeDao grapeDao;
    private CartDao cartDao;
    Consignee consignee1;
    Consignee consignee2;
    List<Consignee> consignees;

    // 初始化各种变量
    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        DB = Room.inMemoryDatabaseBuilder(context, AppRoomDatabase.class).build();
        consigneeDao = DB.consigneeDao();
        grapeDao = DB.grapeDao();
        cartDao = DB.cartDao();
        consignee1 = new Consignee("DENG", "123456789", "Guangdong"
                , "Shenzhen", "Longgqng", 518000
                , "henggang");
        consignee2 = new Consignee("XU", "910111213", "Shandong"
                , "Yantai", "xxxx", 123456
                , "xxxx");
    }


    // 关闭数据库
    @After
    public void closeDb() throws IOException {
        DB.close();
    }

    @Test
    public void writeAndReadConsignee() throws Exception {
         consigneeDao.insert(consignee1);
         consignees = consigneeDao.getAll();
         // 断言 如果在数据库中有创建好的收件人的名字 那么insert和getAll通过
         assertThat(consignees.get(0).getName()).isEqualTo(consignee1.getName());
    }

    @Test
    public void deleteConsignee() throws Exception {
        consigneeDao.delete(consignee1);
        consignees = consigneeDao.getAll();
        // 断言 如果list的size为0 那么delete通过
        assertThat(consignees.size()).isEqualTo(0);
    }

    @Test
    public void updateConsignee() throws Exception {
        // 先创建第一个收件人 放入原list中
        consigneeDao.insert(consignee1);
        consignees = consigneeDao.getAll();
        // 获取第一个收件人的ID
        int consignee1ID = consigneeDao.getAll().get(0).getCon_id();
        // 把第一个收件人的所有信息改为第二个收件人
        consigneeDao.update(consignee1ID, "XU", "910111213", "Shandong"
                , "Yantai", "xxxx", 123456
                , "xxxx");
        // 覆盖原list
        consignees = consigneeDao.getAll();
        // 如果重新提取的list内的收件人为第二个收件人 则 update 通过
        assertThat(consignees.get(0).getName()).isEqualTo(consignee2.getName());
    }
}