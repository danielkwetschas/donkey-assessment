package com.example.donkeyassessment.data

data class TimeLineItem(
    val id: Int,
    val groupName: String,
    val content: String,
    val imgURL: String? = null
)