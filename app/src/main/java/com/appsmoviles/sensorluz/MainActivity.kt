package com.appsmoviles.sensorluz

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var layout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        layout = findViewById(R.id.layout)
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener del sensor cuando la actividad está en primer plano
        lightSensor?.also { sensor ->
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar el listener del sensor cuando la actividad está en segundo plano
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        // Manejar el cambio en los valores del sensor de luz
        event?.let {
            val lightValue = event.values[0]

            if (lightValue <= 10) {
                imageView.visibility = View.INVISIBLE
                textView.text = "Poca luz"
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            } else {
                imageView.visibility = View.VISIBLE
                textView.text = "Suficiente luz"
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            }
        }
    }
}