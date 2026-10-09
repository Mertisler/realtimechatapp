package com.loc.realtimechatapp.core

object Constants {
    // Emulator üzerinden lokal sunucuya (örneğin Node.js/Ktor) bağlanmak için varsayılan IP
    const val WEBSOCKET_URL = "ws://10.0.2.2:8080/chat"

    // Room veri tabanı dosyasının adı
    const val DATABASE_NAME = "chat_database"
}