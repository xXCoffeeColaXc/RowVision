package com.example.rowvision.app.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    // Hard-coded for demo
    private val _userName = MutableStateFlow("Minta Márta")
    val userName: StateFlow<String> = _userName

    // Existing settings state you already implemented...
    val workoutPlans  = listOf("Endurance", "Interval", "Strength")
    val intervals     = listOf("30s/30s", "1min/1min", "2min/1min")
    val goals         = listOf("500m", "1km", "5km")

    private val _selectedPlan = MutableStateFlow(workoutPlans.first())
    val selectedPlan: StateFlow<String> = _selectedPlan
    private val _selectedInterval = MutableStateFlow(intervals.first())
    val selectedInterval: StateFlow<String> = _selectedInterval
    private val _selectedGoal = MutableStateFlow(goals.first())
    val selectedGoal: StateFlow<String> = _selectedGoal
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    // Your updated updaters
    fun onPlanChosen(plan: String)      { _selectedPlan.value = plan }
    fun onIntervalChosen(interval: String) { _selectedInterval.value = interval }
    fun onGoalChosen(goal: String)      { _selectedGoal.value = goal }
    fun onNotificationsToggled(enabled: Boolean) { _notificationsEnabled.value = enabled }
}
