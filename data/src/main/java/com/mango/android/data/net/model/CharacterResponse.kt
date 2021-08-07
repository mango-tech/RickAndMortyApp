package com.mango.android.data.net.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterResponse(
    @JsonProperty("info")
    val info: CharacterRequestInfo,
    @JsonProperty("results")
    var results: List<CharacterResult>
)
