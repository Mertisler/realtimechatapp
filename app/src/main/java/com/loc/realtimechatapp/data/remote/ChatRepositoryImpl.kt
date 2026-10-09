package com.loc.realtimechatapp.data.repository

import com.loc.realtimechatapp.core.Resource
import com.loc.realtimechatapp.data.local.dao.MessageDao
import com.loc.realtimechatapp.data.mapper.toDomain
import com.loc.realtimechatapp.data.mapper.toEntity
import com.loc.realtimechatapp.data.remote.ChatWebSocketClient
import com.loc.realtimechatapp.domain.model.Message
import com.loc.realtimechatapp.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatRepositoryImpl(
    private val messageDao: MessageDao,
    private val webSocketClient: ChatWebSocketClient,
    private val currentUserName: String // Oturum açan kullanıcının adı (Örn: DataStore'dan gelir)
) : ChatRepository {

    init {
        // Repository başlatıldığında, WebSocket'ten gelen mesajları dinlemeye başla
        // ve gelen her DTO nesnesini Entity'e çevirip anında Room veri tabanına kaydet.
        CoroutineScope(Dispatchers.IO).launch {
            webSocketClient.messageFlow.collect { messageDto ->
                messageDao.insertMessage(messageDto.toEntity(currentUserName))
            }
        }
    }

    // Arayüze sunulan metod: Veri tabanındaki Entity listesini Domain modeline (Message) dönüştür.
    override fun observeMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(text: String): Resource<Unit> {
        return try {
            webSocketClient.sendMessage(text)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Bilinmeyen bir ağ hatası oluştu.")
        }
    }

    override fun connect() {
        webSocketClient.connect(currentUserName)
    }

    override fun disconnect() {
        webSocketClient.disconnect()
    }
}