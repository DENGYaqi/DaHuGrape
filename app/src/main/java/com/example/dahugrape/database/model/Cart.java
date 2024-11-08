package com.example.dahugrape.database.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cart {

    public static DiffUtil.ItemCallback<Cart> itemCallback = new DiffUtil.ItemCallback<Cart>() {
        @Override
        public boolean areItemsTheSame(@NonNull Cart oldItem, @NonNull Cart newItem) {
            // 如果下面这个return返回false 那么 只有areContentsTheSame能运行
            // 因为下面这个比较的是两个产品的不同 但是需要的是比较数量的不同 所以修改为对比数量
            //return oldItem.getGrape().equals(newItem.getGrape());
            return oldItem.getQuantity() == newItem.getQuantity();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cart oldItem, @NonNull Cart newItem) {
            return oldItem.equals(newItem);
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    private int cartId; // 可以变成订单单号
    @Ignore
    private Grape grape; // 一个购物车内有多个葡萄 但是这里只用声明一个
    @NonNull
    @ColumnInfo(name = "cart_quantity")
    private int quantity; // 数量

    // 这个是因为没有创建一个关于Grape converter的类 所以创建一个这个构造器以防止有错误
    public Cart(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    @Ignore
    public Cart(Grape grape, int quantity) {
        this.grape = grape;
        this.quantity = quantity;
    }

    @Ignore
    public Cart() {
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Grape getGrape() {
        return grape;
    }

    public void setGrape(Grape grape) {
        this.grape = grape;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", grape=" + grape +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return getCartId() == cart.getCartId() &&
                getQuantity() == cart.getQuantity() &&
                getGrape().equals(cart.getGrape());
    }
}
