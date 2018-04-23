package com.spalmer.magicmirror.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(@JsonProperty("dt") val dt: Long, val main: WeatherMain, val weather: List<WeatherSummary>) {
}