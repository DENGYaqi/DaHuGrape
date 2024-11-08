package com.example.dahugrape.database.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_table")
public class Client { // 与Firebase的User区分

    public static DiffUtil.ItemCallback<Client> itemCallback = new DiffUtil.ItemCallback<Client>() {
        @Override
        public boolean areItemsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem.clientId == newItem.clientId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem.equals(newItem);
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "client_id")
    private int clientId;
    @ColumnInfo(name = "client_name")
    private String clientName;
    @ColumnInfo(name = "client_image_url")
    private String clientImageUrl;
    @Ignore
    private Order order; // 一个user内有多个订单
    @Ignore
    private Cart cart; // 一个user内有一个购物车
    @Ignore
    private Rating rating; // 一个user内有多个评论 一个用户可以评论多个葡萄

    public Client(String clientName, String clientImageUrl, Order order, Cart cart, Rating rating) {
        this.clientName = clientName;
        this.clientImageUrl = clientImageUrl;
        this.order = order;
        this.cart = cart;
        this.rating = rating;
    }

    public Client() {
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientImageUrl() {
        return clientImageUrl;
    }

    public void setClientImageUrl(String clientImageUrl) {
        this.clientImageUrl = clientImageUrl;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientImageUrl='" + clientImageUrl + '\'' +
                ", order=" + order +
                ", cart=" + cart +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getClientId() == client.getClientId() &&
                getClientName().equals(client.getClientName()) &&
                getClientImageUrl().equals(client.getClientImageUrl()) &&
                getOrder().equals(client.getOrder()) &&
                getCart().equals(client.getCart()) &&
                getRating().equals(client.getRating());
    }
}
