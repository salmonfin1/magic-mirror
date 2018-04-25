package com.spalmer.magicmirror

import com.spalmer.magicmirror.model.Schedule
import com.spalmer.magicmirror.service.TrainScheduleService
import com.spalmer.magicmirror.service.WeatherService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(val simpMessagingTemplate: SimpMessagingTemplate,
                var trainScheduleService: TrainScheduleService,
                var weatherService: WeatherService) {

  @Scheduled(fixedRate = 10000)
  fun updateWeather() {
//    val trainSchedule = trainScheduleService.getTrainSchedule();

    val schedule = Schedule(Math.random().toString(),Math.random().toString(), 1);
    val trainSchedule = listOf<Schedule>(schedule)
    simpMessagingTemplate.convertAndSend("/magicmirror", trainSchedule)
  }
}
