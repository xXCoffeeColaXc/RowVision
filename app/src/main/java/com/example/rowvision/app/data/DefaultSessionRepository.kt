package com.example.rowvision.app.data

import com.example.rowvision.app.data.model.Session
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(): SessionRepository {
    override fun getSessions() = listOf(
        Session(
            id                = 1,
            type              = "Endurance",
            dateTime          = LocalDateTime.of(2025,4,6,7,32),
            durationSeconds   = 32*60 + 45,
            strokesPerMinute  = 22,
            strokesCount      = null,
            distanceMeters    = 4500,
            paceSecondsPer500m= 2*60 + 5
        ),
        Session(
            id                = 2,
            type              = "Interval",
            dateTime          = LocalDateTime.of(2025,4,5,17,17),
            durationSeconds   = 25*60,
            strokesPerMinute  = 24,
            strokesCount      = 200,
            distanceMeters    = 0,      // optional
            paceSecondsPer500m= 2*60 + 10
        ),
        Session(
            id                = 3,
            type              = "Endurance",
            dateTime          = LocalDateTime.of(2025,4,3,12,12),
            durationSeconds   = 45*60 + 10,
            strokesPerMinute  = 21,
            strokesCount      = null,
            distanceMeters    = 9500,
            paceSecondsPer500m= 2*60 + 8
        )
    )
}