package com.provocation.checkmate.model

data class IAmYouAreInfo(
    val myMBTI: String,
    val myStudentId: Long,
    val myBirthYear: Int,
    val mySmokingStatus: String,
    val mySnoringStatus: String,
    val mySleepSensitivity: String,
    val myDepartment: String,
    val myDesiredCloseness: String,
    val yourSmokingStatus: String,
    val yourSnoringStatus: String,
    val yourSleepSensitivity: String,
    val yourDepartment: String
) {
    constructor() : this(
        "",
        -1,
        0,
        "",
        ",",
        ",",
        ",",
        ",",
        ",",
        ",",
        ",",
        ",",
    )
}