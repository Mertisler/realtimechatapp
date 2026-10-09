package com.loc.realtimechatapp.domain.model

data class Message(
    val id: String,
    val text: String,
    val senderName: String,
    val timestamp: Long,
    val isFromMe: Boolean
)