package com.provocation.checkmate.presentation.home

data class UserItemList(
    //var profile: String? = null,
    var mateNickName: String? = null,
    val profileImageUrl: Int,
    val studentId: Int,
    val birthYear: Int,
    var mbti: String? = null,
    var desire: String? = null
)
