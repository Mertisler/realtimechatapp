package com.loc.realtimechatapp.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.realtimechatapp.core.Resource
import com.loc.realtimechatapp.domain.usecase.ConnectSocketUseCase
import com.loc.realtimechatapp.domain.usecase.DisconnectSocketUseCase
import com.loc.realtimechatapp.domain.usecase.GetMessagesUseCase
import com.loc.realtimechatapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val connectSocketUseCase: ConnectSocketUseCase,
    private val disconnectSocketUseCase: DisconnectSocketUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    init {
        // ViewModel oluştuğunda direkt veri tabanını dinlemeye başla
        getAllMessages()
    }

    // Arayüzden gelen eylemlerin yönlendirildiği tek nokta
    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.EnteredMessage -> {
                _state.update { it.copy(messageText = event.value) }
            }
            is ChatEvent.SendMessage -> {
                sendMessage()
            }
            is ChatEvent.ConnectSocket -> {
                connectSocketUseCase()
            }
            is ChatEvent.DisconnectSocket -> {
                disconnectSocketUseCase()
            }
        }
    }

    private fun getAllMessages() {
        // Veri tabanını (Room) kesintisiz dinler. Yeni mesaj eklendiğinde liste otomatik güncellenir.
        getMessagesUseCase().onEach { messages ->
            _state.update { it.copy(messages = messages) }
        }.launchIn(viewModelScope)
    }

    private fun sendMessage() {
        val currentText = _state.value.messageText
        if (currentText.isBlank()) return

        // 1. Durum: Mesaj gönderiliyor, metin kutusunu temizle ve yüklemeyi başlat
        _state.update { it.copy(messageText = "", isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            // UseCase çalıştırılarak ağ isteği yapılır
            val result = sendMessageUseCase(currentText)

            when (result) {
                is Resource.Success -> {
                    // 2. Durum: Başarılı. Sadece yükleme ikonunu kapat.
                    // Not: Mesaj listeye burada eklenmez! Socket'ten gelip Room'a yazılınca
                    // getAllMessages() içindeki akış (Flow) otomatik olarak listeyi güncelleyecektir.
                    _state.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    // 3. Durum: Hata oldu, hata mesajını State'e yaz.
                    _state.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Beklenmeyen bir hata oluştu"
                    ) }
                }
                else -> Unit
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // ViewModel bellekten silinirken soketi temizle
        disconnectSocketUseCase()
    }
}