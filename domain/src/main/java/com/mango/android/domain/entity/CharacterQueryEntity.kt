package com.mango.android.domain.entity

data class CharacterQueryEntity(
    val count: Int,
    val pages: Int,
    val data: List<CharacterEntity>
)
