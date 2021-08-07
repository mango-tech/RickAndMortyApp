package com.mango.android.data.net.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterResult(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("status")
    val status: String,
    @JsonProperty("species")
    val species: String,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("gender")
    val gender: String,
    @JsonProperty("image")
    val image: String
)
