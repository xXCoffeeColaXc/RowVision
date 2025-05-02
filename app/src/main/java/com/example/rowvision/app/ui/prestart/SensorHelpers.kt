package com.example.rowvision.app.ui.prestart

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.lang.Math.toDegrees

/**
 * Emits the current device pitch (forward/backward tilt) in degrees.
 */
@Composable
fun rememberTiltAngle(): State<Float> {
    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val rotVecSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    val angleState = remember { mutableStateOf(0f) }

    DisposableEffect(rotVecSensor) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(e: SensorEvent) {
                val rm = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rm, e.values)
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rm, orientation)
                // orientation[1] is pitch in radians
                angleState.value = toDegrees(orientation[1].toDouble()).toFloat()
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, rotVecSensor, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }
    return angleState
}
