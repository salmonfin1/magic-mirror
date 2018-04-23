package com.spalmer.magicmirror.controller
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.spalmer.magicmirror.model.Schedule
import com.spalmer.magicmirror.model.ScheduleResponse
import com.spalmer.magicmirror.configuration.TokenConfiguration
import com.spalmer.magicmirror.model.Weather
import com.spalmer.magicmirror.service.TrainScheduleService
import com.spalmer.magicmirror.service.WeatherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TrainScheduleController @Autowired constructor(var trainScheduleService: TrainScheduleService,
                                                     var weatherService: WeatherService) {

    @GetMapping("/schedules")
    fun getSchedule(): List<Schedule> {
        return trainScheduleService.getTrainSchedule()
    }

    @GetMapping("/weather")
    fun getWeather(): List<Weather> {
        return weatherService.getWeather()
    }
}