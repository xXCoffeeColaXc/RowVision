package com.example.rowvision.app.data

import com.example.rowvision.app.data.model.WorkoutSettings
import javax.inject.Inject          // <-- use javax (works the same)
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class SettingsRepository @Inject constructor() {
    private val _settings = MutableStateFlow(
        WorkoutSettings(plan = "Endurance", interval = "30s/30s", goal = "500m")
    )

    val settings: StateFlow<WorkoutSettings> = _settings

    fun save(new: WorkoutSettings) { _settings.value = new }
}