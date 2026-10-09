package com.loc.realtimechatapp.data.remote

import com.google.gson.Gson
import com.loc.realtimechatapp.core.Constants
import com.loc.realtimechatapp.data.remote.dto.MessageDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.*
import okio.ByteString

class ChatWebSocketClient(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    private var webSocket: WebSocket? = null

    // Gelen mesajları dinleyicilere (Repository) iletmek için kullanılan akış
    private val _messageFlow = MutableSharedFlow<MessageDto>()
    val messageFlow: SharedFlow<MessageDto> = _messageFlow.asSharedFlow()

    fun connect(username: String) {
        val request = Request.Builder()
            .url("${Constants.WEBSOCKET_URL}?username=$username")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                // Sunucudan gelen JSON metnini MessageDto nesnesine çevirip akışa emit ediyoruz
                val messageDto = gson.fromJson(text, MessageDto::class.java)
                _messageFlow.tryEmit(messageDto)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                t.printStackTrace()
            }
        })
    }

    fun sendMessage(text: String) {
        webSocket?.send(text)
    }

    fun disconnect() {
        webSocket?.close(1000, "Bağlantı kullanıcı tarafından sonlandırıldı")
        webSocket = null
    }
}