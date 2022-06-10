package com.mango.android.rickmortyapp

data class Character(
    var id: Int = 0,
    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null,
    var image: String? = null
)