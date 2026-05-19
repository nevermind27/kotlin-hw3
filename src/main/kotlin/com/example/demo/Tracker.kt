package com.example.demo

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.concurrent.TimeUnit

// Обновляем раз в 2 часа, на случай коррекции направления, CeleTrack также обновляет раз в 2-3 часа
// При запросе пользователя используем последнее сохраненное значение
@EnableScheduling
@Service
class Tracker {
    final var tle = ""
        private set
    private val restClient = RestClient.create()

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
    fun fetchCurrentPosition() {
        tle = restClient.get()
            .uri("https://celestrak.org/NORAD/elements/gp.php?CATNR=25544&FORMAT=TLE")
            .retrieve()
            .body(String::class.java)!!
    }

}