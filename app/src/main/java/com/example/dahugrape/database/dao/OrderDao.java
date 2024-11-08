package com.example.dahugrape.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dahugrape.database.model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insertOrder(Order order);

    @Query("SELECT * FROM order_table")
    List<Order> getAllOrders();

    @Query("SELECT * FROM order_table WHERE order_id LIKE :orderId")
    Order findOrderById(int orderId);

    @Delete
    void deleteOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Insert
    void insertMultipleOrders(List<Order> orderList);
}
