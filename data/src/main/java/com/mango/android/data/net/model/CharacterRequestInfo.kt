package com.mango.android.data.net.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterRequestInfo(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("pages")
    val pages: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("prev")
    val prev: String?
)
