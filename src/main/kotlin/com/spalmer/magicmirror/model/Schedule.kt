package com.spalmer.magicmirror.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Schedule(@JsonProperty("std") val departureTime :String,
                    @JsonProperty("etd") val status: String,
                    val platform: Int) {



}