package com.example.rowvision.app.data.model

import java.time.LocalDateTime

data class Session(
    val id: Int,
    val type: String,                      // e.g. "Endurance", "Interval"
    val dateTime: LocalDateTime,
    val durationSeconds: Int,              // total seconds
    val strokesPerMinute: Int?,            // e.g. 22
    val strokesCount: Int?,                // e.g. 200 (for interval)
    val distanceMeters: Int,               // e.g. 4500
    val paceSecondsPer500m: Int            // e.g. 125  (2:05)
)