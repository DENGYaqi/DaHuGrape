package com.example.dahugrape.database.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_table")
public class Order {

    public static DiffUtil.ItemCallback<Order> itemCallback = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.orderId == newItem.orderId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.equals(newItem);
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private int orderId;
    @ColumnInfo(name = "order_state")
    private String state; // 订单状态 : 已下单, 已付款, 已发货, 送货中, 已送达
    @ColumnInfo(name = "order_date_delivery")
    private String dateDelivery; // 送达时间
    @Ignore
    private PayMode paymentModes; // 支付方式 : 银行卡, 支付宝, 微信
    @Ignore
    private Cart cart; // 一个订单内有一个购物车 一个购物车内有多个葡萄 相当于商品详情

    public Order(String state, String dateDelivery, PayMode paymentModes, Cart cart) {
        this.state = state;
        this.dateDelivery = dateDelivery;
        this.paymentModes = paymentModes;
        this.cart = cart;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public PayMode getPaymentModes() {
        return paymentModes;
    }

    public void setPaymentModes(PayMode paymentModes) {
        this.paymentModes = paymentModes;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", state='" + state + '\'' +
                ", dateDelivery='" + dateDelivery + '\'' +
                ", paymentModes=" + paymentModes +
                ", cart=" + cart +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getOrderId() == order.getOrderId() &&
                getState().equals(order.getState()) &&
                getDateDelivery().equals(order.getDateDelivery()) &&
                getPaymentModes().equals(order.getPaymentModes()) &&
                getCart().equals(order.getCart());
    }
}
