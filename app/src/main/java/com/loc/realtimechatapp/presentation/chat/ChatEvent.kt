package com.loc.realtimechatapp.presentation.chat

sealed class ChatEvent {
    // Kullanıcı TextField'a yazı yazdığında tetiklenir
    data class EnteredMessage(val value: String) : ChatEvent()

    // Kullanıcı "Gönder" butonuna bastığında tetiklenir
    object SendMessage : ChatEvent()

    // Uygulama açıldığında veya ekrana girildiğinde soketi başlatmak için tetiklenir
    object ConnectSocket : ChatEvent()

    // Uygulama arka plana atıldığında soketi kapatmak için tetiklenir
    object DisconnectSocket : ChatEvent()
}