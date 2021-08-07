package com.mango.android.domain.entity

data class CharacterEntity(
    var id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String
)