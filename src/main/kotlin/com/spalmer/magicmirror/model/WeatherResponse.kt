package com.spalmer.magicmirror.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherResponse(val list:  List<Weather> ) {

}