package com.provocation.checkmate.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Chat(
    val text: String,
    val senderId: Long,
    val date: String
) {
    constructor(text: String, senderId: Long) : this(
        text,
        senderId,
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    )
}