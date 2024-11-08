package com.example.dahugrape.database.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.database.repositories.CartRepo;
import com.example.dahugrape.database.repositories.GrapeRepo;

import java.util.List;

// 调用GrapeRepo函数来使数据显示在view 也就是所谓的MVVM模型
public class GrapeViewModel extends ViewModel {

    GrapeRepo grapeRepo = new GrapeRepo();

    CartRepo cartRepo = new CartRepo();

    MutableLiveData<Grape> mutableGrape = new MutableLiveData<>();

    public LiveData<List<Grape>> getGrapes(Activity activity) {
        return grapeRepo.getGrapes(activity);
    }

    public LiveData<Grape> getGrape() {
        return mutableGrape;
    }

    public void setGrape(Grape grape) {
        mutableGrape.setValue(grape);
    }

    public Grape getGrapeByName(String name) {
        return grapeRepo.getGrapeByName(name);
    }

    public LiveData<List<Cart>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemRatingToGrape(Grape grape, Rating rating){
        return grapeRepo.addItemRating(grape, rating);
    }

    public boolean addItemToCart(Grape grape) {
        return cartRepo.addItemToCart(grape);
    }

    public void removeItemFromCart(Cart cart) {
        cartRepo.removeItemFromCart(cart);
    }

    public boolean minusItemInCart(Grape grape) {
        return cartRepo.minusItemInCart(grape);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    // 重置购物车 : 直接用CartRepo内的initCart()
    public void resetCart() {
        cartRepo.initCart();
    }
}
