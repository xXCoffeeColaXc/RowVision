package com.example.rowvision.app.ui.video

import androidx.lifecycle.ViewModel
import com.example.rowvision.app.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repo: SettingsRepository
): ViewModel() {
    val settings = repo.settings           // same flow, shared everywhere
}