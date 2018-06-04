package com.spalmer.magicmirror

import com.google.api.client.util.DateTime
import com.spalmer.magicmirror.model.*
import com.spalmer.magicmirror.service.CalendarService
import com.spalmer.magicmirror.service.TrainScheduleService
import com.spalmer.magicmirror.service.WeatherService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class Scheduler(val simpMessagingTemplate: SimpMessagingTemplate,
                var trainScheduleService: TrainScheduleService,
                var weatherService: WeatherService,
                var calendarService: CalendarService) {

  @Scheduled(initialDelay = 10000, fixedRate = 10800000)
  fun getWeather() {

    val weather = weatherService.getWeather()
    simpMessagingTemplate.convertAndSend(
      "/weather",
      weather
    )
  }

  @Scheduled(initialDelay = 10000, fixedRate = 600000)
  fun getTrains() {
    val trainSchedule = trainScheduleService.getTrainSchedule()
    simpMessagingTemplate.convertAndSend(
      "/trains",
      trainSchedule
    )
  }

  @Scheduled(initialDelay = 10000, fixedRate = 21600000)
  fun getCalendar() {
    val calendars = calendarService.getCalendars()
    simpMessagingTemplate.convertAndSend(
      "/calendar",
      calendars
    )
  }
}
