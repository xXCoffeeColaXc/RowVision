package com.example.rowvision.app.data

import com.example.rowvision.app.data.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(): SessionRepository {
    // start with your three demo sessions
    private val _sessions = MutableStateFlow(
        listOf(
            Session(3, "Endurance", LocalDateTime.of(2025,4,6,7,32), 32*60+45, 22, null, 4500, 5000, 2*60+5),
            Session(2, "Interval",  LocalDateTime.of(2025,4,5,17,17), 25*60,     24, 200,   0, 500,  2*60+10),
            Session(1, "Endurance", LocalDateTime.of(2025,4,3,12,12), 45*60+10, 21, null, 500, 1000, 2*60+8)
        )
    )
    override fun sessions(): StateFlow<List<Session>> = _sessions

    override fun addSession(session: Session) {
        // prepend new session
        _sessions.value = listOf(session) + _sessions.value
    }

    override fun nextSessionId(): Int =
        (_sessions.value.maxOfOrNull { it.id } ?: 0) + 1
}