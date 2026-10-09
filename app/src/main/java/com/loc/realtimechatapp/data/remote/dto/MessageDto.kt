package com.loc.realtimechatapp.data.remote.dto

// Sunucudan gelen JSON yapısına (örneğin Gson veya Moshi ile) birebir uyması gereken sınıftır.
data class MessageDto(
    val id: String,
    val text: String,
    val senderName: String,
    val timestamp: Long
)