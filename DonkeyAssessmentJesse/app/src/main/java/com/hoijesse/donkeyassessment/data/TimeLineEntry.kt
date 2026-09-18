package com.hoijesse.donkeyassessment.data

data class TimeLineEntry (
    val id: Int,
    val inGroup: String,
    val text: String,
    val image: String? = null,
    val senderName: String
)