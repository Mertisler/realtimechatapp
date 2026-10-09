package com.loc.realtimechatapp.data.mapper

import com.loc.realtimechatapp.data.local.entity.MessageEntity
import com.loc.realtimechatapp.data.remote.dto.MessageDto
import com.loc.realtimechatapp.domain.model.Message

/**
 * Sunucudan gelen ham DTO verisini, cihazın veri tabanına kaydetmek üzere Entity'ye çevirir.
 * [currentUserName] parametresi, mesajı gönderen kişi ile cihazı kullanan kişi aynıysa
 * "isFromMe = true" atamasını yaparak arayüzde yeşil baloncuğun sağda çıkmasını sağlar.
 */
fun MessageDto.toEntity(currentUserName: String): MessageEntity {
    return MessageEntity(
        id = this.id,
        text = this.text,
        senderName = this.senderName,
        timestamp = this.timestamp,
        isFromMe = this.senderName == currentUserName
    )
}

/**
 * Veri tabanından okunan Entity verisini (Room objesi),
 * Jetpack Compose arayüzünün doğrudan kullanacağı saf Domain Model'e (Message) çevirir.
 */
fun MessageEntity.toDomain(): Message {
    return Message(
        id = this.id,
        text = this.text,
        senderName = this.senderName,
        timestamp = this.timestamp,
        isFromMe = this.isFromMe
    )
}