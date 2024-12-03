package com.provocation.checkmate.chatlist

import java.time.LocalDateTime

data class ChatItemList(
    val roomId: Long,
    val senderId: Long,
    val receiverName: String,
    val receiverProfileImageUrl: String,
    val lastMessage: String

)
