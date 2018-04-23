package com.spalmer.magicmirror.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "weather")
class WeatherConfiguration {
    lateinit var token: String
}