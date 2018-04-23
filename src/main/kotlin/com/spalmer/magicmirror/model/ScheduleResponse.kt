package com.spalmer.magicmirror.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ScheduleResponse(@JsonProperty("trainServices") val schedules: List<Schedule>) {


}