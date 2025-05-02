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
    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = _sessions

    init {
        viewModelScope.launch {
            _sessions.value = repo.getSessions()
        }
    }
}