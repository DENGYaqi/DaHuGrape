package com.example.dahugrape.ml2_nlu.chat_bot_utils

import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.OPEN_GOOGLE
import com.example.dahugrape.ml2_nlu.chat_bot_utils.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * bot的内容相关的回答
 */
object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            // 抛出一枚硬币 看正反面
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            // 数学计算
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            // Hello
            message.contains("hello") -> { // 当信息内包含hello 我们就在下面随机选一个回答
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Buongiorno!"
                    else -> "error" }
            }

            // How are you?
            message.contains("how are you") -> { // 当信息内包含how are you? 我们就在下面随机选一个回答
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            // What time is it? // 这里有点奇怪 因为要加问号才会回答
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            // Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            // Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            // When the programme doesn't understand...
            else -> { // 其他情况均是bot不能理解的情况
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }
    }
}