package com.spalmer.magicmirror.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.spalmer.magicmirror.configuration.TokenConfiguration
import com.spalmer.magicmirror.model.Schedule
import com.spalmer.magicmirror.model.ScheduleResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TrainScheduleService @Autowired constructor(var configuration: TokenConfiguration) {

    fun getTrainSchedule(): List<Schedule> {
        FuelManager.instance.basePath = "https://huxley.apphb.com"
        val (_, _, result) = "/departures/TED?accessToken=${configuration.token}".httpGet().responseString()
        val data = result.get()
        val mapper = jacksonObjectMapper()
        val scheduleResponse: ScheduleResponse = mapper.readValue(data)
        return scheduleResponse.schedules
    }
}