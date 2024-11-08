package com.example.dahugrape.database.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Grape;

import java.util.ArrayList;
import java.util.List;

// 获取Room的数据 包含各种数据处理的功能 为ViewModel提供功能
public class CartRepo {

    private final MutableLiveData<List<Cart>> mutableCart = new MutableLiveData<>(); // 所有的购物车

    private final MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>(); // 总价

    public LiveData<List<Cart>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }

    // 初始化购物车
    public void initCart() {
        mutableCart.setValue(new ArrayList<Cart>());
        calculateCartTotal(); // 计算总价格
    }

    // 1. 判断是否加入了购物车 如果是就加入购物车
    // 2. 增加一件产品
    // 3. 相同的产品就融合并且只增加数量
    public boolean addItemToCart(Grape grape) {
        // 如果数据库为空就初始化一个
        if (mutableCart.getValue() == null) {
            initCart();
        }
        // 如果存在这个需要加入购物车的产品 就加入到购物车的list内
        List<Cart> cartList = new ArrayList<>(mutableCart.getValue());
        for (Cart cart : cartList) {
            if (cart.getGrape().getGrapeId() == grape.getGrapeId()) {
                if (cart.getQuantity() == 10) { // 每样产品限购10个 如果超出了就不让增加了 之后这里可以与库存联系上
                    return false;
                }
                int index = cartList.indexOf(cart);
                cart.setQuantity(cart.getQuantity() + 1);
                cartList.set(index, cart);

                mutableCart.setValue(cartList); // 应用新数量
                calculateCartTotal(); // 计算总价格
                return true;
            }
        }
        // 如果不存在就新建
        Cart cart = new Cart(grape, 1);
        cartList.add(cart);
        mutableCart.setValue(cartList); // 应用新数量
        calculateCartTotal(); // 计算总价格
        return true;
    }

    // 把产品从购物车删除
    public void removeItemFromCart(Cart cart) {
        if (mutableCart.getValue() == null) {
            return;
        }
        List<Cart> cartList = new ArrayList<>(mutableCart.getValue());
        cartList.remove(cart);
        mutableCart.setValue(cartList); // 应用更新后的(删除项已被删除)
        calculateCartTotal(); // 设置总价的改变
    }

    // 减少一件产品
    public boolean minusItemInCart(Grape grape) {
        // 如果没有这个产品就初始化购物车
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<Cart> cartList = new ArrayList<>(mutableCart.getValue());
        for (Cart cart : cartList) {
            if (cart.getGrape().getGrapeId() == grape.getGrapeId()) {
                if (cart.getQuantity() <= 1) { // 每样产品最少的数量是1个
                    return false;
                }
                int index = cartList.indexOf(cart);
                cart.setQuantity(cart.getQuantity() - 1);
                cartList.set(index, cart);
                mutableCart.setValue(cartList); // 应用新数量
                calculateCartTotal(); // 计算总价格
                return true;
            }
        }
        // Create a new quantity of item为了要加入上面的list
        Cart cart = new Cart(grape, 1);
        cartList.add(cart);
        mutableCart.setValue(cartList); // 应用新数量
        calculateCartTotal(); // 计算总价格
        return true;
    }

    // 计算购物车总价格
    private void calculateCartTotal() {
        if (mutableCart.getValue() == null) return;
        double total = 0.0;
        List<Cart> cartList = mutableCart.getValue();
        for (Cart cart : cartList) {
            total += cart.getGrape().getPricePerKilo() * cart.getQuantity(); // 产品价格乘以数量
        }
        mutableTotalPrice.setValue(total);
    }

    public LiveData<Double> getTotalPrice() {
        if (mutableTotalPrice.getValue() == null) {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }
}
