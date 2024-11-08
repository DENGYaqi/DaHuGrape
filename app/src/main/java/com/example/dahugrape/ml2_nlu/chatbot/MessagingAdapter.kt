package com.example.dahugrape.ml2_nlu.chatbot

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dahugrape.R
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.RECEIVE_ID
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.ml2_message_item_chatbot.view.*

class MessagingAdapter : RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>(){

    var messagesList = mutableListOf<Message>()

    /**
     * 内部类是一个使用关键字inner在一个类中创建的类。 换句话说，可以说标记为inner的嵌套类称为内部类。
     * 内部类不能在接口或非内部嵌套类中声明。
     * 内部类优于嵌套类的优点是，即使它是私有的，它也能够访问外部类的成员。 内部类保持对外部类的对象的引用。
     */
    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                // 在视屏中用的是adapterPosition 但是这个已经Deprecated了
                // 原因是 : 当Adapter嵌套其他的Adapter时，这个方法会引起混淆。如果你在Adapter的context中调用这个方法，
                // 你可能想调用getBindingAdapterPosition()，
                // 或者如果你想要RecyclerView看到的位置，你应该调用getAbsoluteAdapterPosition()。
                // 推荐的是getBindingAdapterPosition() 和 getAbsoluteAdapterPosition()
                // getBindingAdapterPosition()这个相对应的是bindingAdapterPosition
                // getAbsoluteAdapterPosition() 相对应的是absoluteAdapterPosition
                // TODO 我这里用的是absoluteAdapterPosition 到时候看上面的解释再选择别的或者就用这个了
                // 当点击到相应的对话的时候就删除
                messagesList.removeAt(absoluteAdapterPosition)
                notifyItemRemoved(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.ml2_message_item_chatbot, parent, false))
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        // when 表达式取代了类 C 语言的 switch 语句
        when (currentMessage.id) {
            SEND_ID -> {
                holder.itemView.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }
}