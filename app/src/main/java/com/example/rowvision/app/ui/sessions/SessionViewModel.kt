package com.example.rowvision.app.ui.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rowvision.app.data.SessionRepository
import com.example.rowvision.app.data.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionsViewModel @Inject constructor(
    private val repo: SessionRepository
): ViewModel() {
    /** Exposed as StateFlow so composables can collectAsState() */
    val sessions = repo.sessions()

    fun addSession(session: Session) = repo.addSession(session)
    fun nextId(): Int = repo.nextSessionId()
}