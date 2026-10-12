package com.loc.realtimechatapp.presentation.chat

import com.loc.realtimechatapp.domain.model.Message

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val messageText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)