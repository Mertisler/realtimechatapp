package com.loc.realtimechatapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// tableName: SQLite tarafında oluşacak tablonun fiziksel ismidir.
@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val text: String,
    val senderName: String,
    val timestamp: Long,
    val isFromMe: Boolean // Mesajın kimden olduğunu veri tabanında tutarak arayüzün yükünü hafifletiriz.
)