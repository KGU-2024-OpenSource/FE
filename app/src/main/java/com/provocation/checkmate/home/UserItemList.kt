package com.provocation.checkmate.home

data class UserItemList(
    //var profile: String? = null,
    var mateId: Long,
    var mateNickName: String? = null,
    var profileImageUrl: String? = null, // URL로 변경
    var mbti: String? = null,
    val studentId: Int,
    val birthYear: Int,
    var department: String? = null,
    var desire: String? = null
)
