package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.me.g4dpz.satellite.*
import java.util.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
@RestController
class PredictionController(private val tracker: Tracker) {

    @GetMapping("/predict")
    fun predict(
        @RequestParam(defaultValue = "60") minutes: Int
    ): PredictionResponse {
        val lines = tracker.tle.trim().lines()
        val tleArray = lines.toTypedArray()
        val tleData = TLE(tleArray)
        val satellite = SatelliteFactory.createSatellite(tleData)

        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, minutes)
        val futureDate = cal.time


        val groundStation = GroundStationPosition(0.0, 0.0, 0.0) // должна быть наземная станция от которой считаем, это заглушка для демонстрации работы
        val pos: SatPos = satellite.getPosition(groundStation, futureDate)
        return PredictionResponse(
            latitude = Math.toDegrees(pos.latitude),
            longitude = Math.toDegrees(pos.longitude),
            altitudeKm = pos.range,
            predictedAt = Clock.System.now().toString(),
            offsetMinutes = minutes
        )
    }
}


data class PredictionResponse(
    val latitude: Double,
    val longitude: Double,
    val altitudeKm: Double,
    val predictedAt: String,
    val offsetMinutes: Int
)