package ru.yandex.praktikumchatapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yandex.praktikumchatapp.data.ChatRepository

class ChatViewModel(
    val isWithReplies: Boolean = true
) : ViewModel() {

    private val repository = ChatRepository()

    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    init {
        viewModelScope.launch {
            while (isWithReplies) {
                repository.getReplyMessage().collect { response ->
                    _chatState.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message.OtherMessage(response),
                            shouldShowKeyboard = if (currentState.messages.isEmpty()) true else currentState.shouldShowKeyboard
                        )
                    }
                }
            }
        }
    }

    fun sendMyMessage(messageText: String) {
        _chatState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + Message.MyMessage(messageText)
            )
        }
    }
}