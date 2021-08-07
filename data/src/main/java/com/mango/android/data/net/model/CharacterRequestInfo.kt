package com.mango.android.data.net.model

data class CharacterRequestInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
