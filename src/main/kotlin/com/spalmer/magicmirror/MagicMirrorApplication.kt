package com.spalmer.magicmirror

import com.github.kittinunf.fuel.httpGet
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MagicMirrorApplication

fun main(args: Array<String>) {
    runApplication<MagicMirrorApplication>(*args)
}
