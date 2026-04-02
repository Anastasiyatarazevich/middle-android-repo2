package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import kotlin.math.pow

const val INIT_DELAY = 100L
const val DELAY = 2.0

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {

    fun getReplyMessage(): Flow<String> {
        return api.getReply()
            .retryWhen { cause, attempt ->
                if (cause is Exception) {
                    val multiplier = DELAY.pow(attempt.toDouble())
                    val delayTime = (INIT_DELAY * multiplier).toLong()
                    delay(delayTime)
                    true
                } else {
                    false
                }
            }
    }
}