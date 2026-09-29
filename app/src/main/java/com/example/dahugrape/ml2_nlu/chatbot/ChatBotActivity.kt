package com.example.dahugrape.ml2_nlu.chatbot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dahugrape.R
import com.example.dahugrape.ml2_nlu.chat_bot_utils.BotResponse
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.OPEN_GOOGLE
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.OPEN_SEARCH
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.RECEIVE_ID
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.SEND_ID
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Time
import kotlinx.android.synthetic.main.ml2_activity_chat_bot.*
import kotlinx.coroutines.*

/**
 * 智能客服
 */
class ChatBotActivity : AppCompatActivity() {

    private val TAG = "ChatBotActivity"

    // 用于调试
    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    // 机器人合集 类似于不同的客服
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ml2_activity_chat_bot)

        recyclerView()

        clickEvents()

        val random = (0..3).random() // 从0到3的随机数字(包含)
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents() {

        // 发送消息
        btn_send.setOnClickListener {
            sendMessage()
        }

        // 当用户点击文本视图时，滚动回正确的位置
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        // 如果有消息，在重新打开应用程序时滚动到底部。
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            // 将其添加到我们的本地列表中
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            // 虚假的反应延迟
            delay(1000)

            withContext(Dispatchers.Main) {
                // 获取响应
                val response = BotResponse.basicResponses(message)

                // 将其添加到我们的本地列表中
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                // 将我们的信息插入适配器中
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                // 将我们滚动到最新信息的位置
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                // 启动谷歌
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    // 个性化信息
    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}
