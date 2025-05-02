package com.example.rowvision.app.ui.analysis

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor() : ViewModel() {
    // Total “clean strokes” – you can compute or fetch this
    val cleanStrokes: Int = 20

    // Session duration string
    val duration: String = "32 min 17 sec"

    // Phase percentages (newest at top)
    val segments = listOf(
        "Extended lean back"  to 40f,
        "Early knee bend"     to 26f,
        "Arms arm bend"       to 24f,
        "Optimal catch"       to 10f,
        "Optimal release"     to  0f,
        "Perfect stroke"      to  0f
    )
}