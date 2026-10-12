package com.loc.realtimechatapp.domain.usecase

import com.loc.realtimechatapp.core.Resource
import com.loc.realtimechatapp.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(text: String): Resource<Unit> {
        // İş kuralı: Mesaj boş veya sadece boşluktan oluşamaz
        if (text.isBlank()) {
            return Resource.Error("Mesaj boş olamaz.")
        }

        // Kuraldan geçtiyse Repository üzerinden ağa gönder
        return repository.sendMessage(text.trim())
    }
}