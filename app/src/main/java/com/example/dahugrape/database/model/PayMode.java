package com.example.dahugrape.database.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pay_mode_table")
public class PayMode {
    public static DiffUtil.ItemCallback<PayMode> itemCallback = new DiffUtil.ItemCallback<PayMode>() {
        @Override
        public boolean areItemsTheSame(@NonNull PayMode oldItem, @NonNull PayMode newItem) {
            return oldItem.payModeId == newItem.payModeId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PayMode oldItem, @NonNull PayMode newItem) {
            return oldItem.equals(newItem);
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pay_mode_id")
    private int payModeId;
    @ColumnInfo(name = "pay_mode_we_chat")
    private String weChat; // 微信支付
    @ColumnInfo(name = "pay_mode_card")
    private String card; // 银行支付
    @ColumnInfo(name = "pay_mod_ali_pay")
    private String aliPay; // 支付宝

    public PayMode(String weChat, String card, String aliPay) {
        this.weChat = weChat;
        this.card = card;
        this.aliPay = aliPay;
    }

    public int getPayModeId() {
        return payModeId;
    }

    public void setPayModeId(int payModeId) {
        this.payModeId = payModeId;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAliPay() {
        return aliPay;
    }

    public void setAliPay(String aliPay) {
        this.aliPay = aliPay;
    }

    @Override
    public String toString() {
        return "PayMode{" +
                "payModeId=" + payModeId +
                ", weChat='" + weChat + '\'' +
                ", card='" + card + '\'' +
                ", aliPay='" + aliPay + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayMode payMode = (PayMode) o;
        return getPayModeId() == payMode.getPayModeId() &&
                getWeChat().equals(payMode.getWeChat()) &&
                getCard().equals(payMode.getCard()) &&
                getAliPay().equals(payMode.getAliPay());
    }
}
