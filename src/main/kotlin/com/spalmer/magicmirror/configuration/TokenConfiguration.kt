package com.spalmer.magicmirror.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "darwin")
class TokenConfiguration {
    lateinit var token: String
}