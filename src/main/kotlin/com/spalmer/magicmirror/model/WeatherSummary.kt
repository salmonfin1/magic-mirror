package com.spalmer.magicmirror.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherSummary(val main: String, val description: String, val icon: String) {
}