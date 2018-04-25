package com.spalmer.magicmirror.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.spalmer.magicmirror.configuration.WeatherConfiguration
import com.spalmer.magicmirror.model.Weather
import com.spalmer.magicmirror.model.WeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Component
class WeatherService @Autowired constructor(var configuration: WeatherConfiguration) {

    fun getWeather(): List<Weather> {
        FuelManager.instance.basePath = "http://api.openweathermap.org"
        val (_, _, result) = "/data/2.5/forecast?lat=51.4303469&lon=-0.3322893&APPID=${configuration.token}".httpGet().responseString()
        val data = result.get()
        val mapper = jacksonObjectMapper()
        val weatherResponse: WeatherResponse = mapper.readValue(data)
        val tomorrow = LocalDateTime.now().plusDays(1)
        return weatherResponse.list.filter {
            LocalDateTime.ofInstant(Instant.ofEpochSecond(it.dt), TimeZone.getDefault().toZoneId()).isAfter(tomorrow)

        }
    }



}