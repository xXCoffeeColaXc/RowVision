package com.example.rowvision.app.data

import com.example.rowvision.app.data.model.Session

interface SessionRepository {
    fun getSessions(): List<Session>
}