package com.provocation.checkmate.presentation.home

data class UserItemList(
    //var profile: String? = null,
    var mateNickName: String? = null,
    var profileImageUrl: String? = null, // URL로 변경
    val studentId: Int,
    val birthYear: Int,
    var mbti: String? = null,
    var desire: String? = null
)
