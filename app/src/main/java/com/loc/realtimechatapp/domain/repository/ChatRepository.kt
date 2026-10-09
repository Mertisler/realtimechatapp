package com.loc.realtimechatapp.domain.repository

import com.loc.realtimechatapp.core.Resource
import com.loc.realtimechatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    // Veri tabanındaki mesaj listesindeki her değişikliği anlık olarak dinler (Tepkisel akış)
    fun observeMessages(): Flow<List<Message>>

    // Sunucuya yeni bir mesaj iletir. Ağ işlemi olduğu için suspend fonksiyon olarak tanımlanır
    suspend fun sendMessage(text: String): Resource<Unit>

    // WebSocket bağlantısını başlatır
    fun connect()

    // Uygulama arka plana atıldığında veya kapatıldığında soketi güvenle sonlandırır
    fun disconnect()
}