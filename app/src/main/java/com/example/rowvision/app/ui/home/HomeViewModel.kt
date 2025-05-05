package com.example.rowvision.app.ui.home

import androidx.lifecycle.ViewModel
import com.example.rowvision.app.data.SettingsRepository
import com.example.rowvision.app.data.model.WorkoutSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: SettingsRepository
): ViewModel() {
    val settings = repo.settings           // collectAsState() in UI
    fun saveSettings(p:String,i:String,g:String) =
        repo.save(WorkoutSettings(p,i,g))
}