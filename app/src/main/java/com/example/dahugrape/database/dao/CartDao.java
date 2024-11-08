package com.example.dahugrape.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dahugrape.database.model.Cart;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insertCart(Cart cart);

    @Query("SELECT * FROM cart_table")
    List<Cart> getAllCarts();

    @Query("SELECT * FROM cart_table WHERE cart_id LIKE :cartId")
    Cart findCartById(int cartId);

    @Delete
    void deleteCart(Cart cart);

    @Update
    void updateCart(Cart cart);

    @Insert
    void insertMultipleCarts(List<Cart> cartList);
}
