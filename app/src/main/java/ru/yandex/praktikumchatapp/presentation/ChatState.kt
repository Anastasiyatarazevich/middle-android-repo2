package ru.yandex.praktikumchatapp.presentation

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class ChatState (
    val messages: ImmutableList<Message> = persistentListOf(),
    val shouldShowKeyboard: Boolean = false
)