package com.spalmer.magicmirror.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherMain(val main: Double,
                       @JsonProperty("temp_min") val tempMin: Double,
                       @JsonProperty("temp_max") val tempMax: Double) {
}
