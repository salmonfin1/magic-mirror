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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class WeatherService @Autowired constructor(var configuration: WeatherConfiguration) {

    fun getWeather(): List<Weather> {
      FuelManager.instance.basePath = "http://api.openweathermap.org"
      val (_, _, result) = "/data/2.5/forecast?lat=51.4303469&lon=-0.3322893&APPID=${configuration.token}".httpGet().responseString()
      val data = result.get()
      val mapper = jacksonObjectMapper()
      val weatherResponse: WeatherResponse = mapper.readValue(data)
      val tomorrowAtThree = LocalDate.now().plusDays(1).atTime(15,0,0);
      val toEpochSecond = tomorrowAtThree.atZone(ZoneId.of("UTC")).toEpochSecond()
      val tomorrow = weatherResponse.list.first {
        it.dt == toEpochSecond
      }
      return listOf<Weather>(weatherResponse.list.first(), tomorrow)
    }



}
