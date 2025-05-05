package com.example.rowvision.app.data

import com.example.rowvision.app.data.model.Session
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    fun sessions(): StateFlow<List<Session>>
    fun addSession(session: Session)
    fun nextSessionId(): Int
}