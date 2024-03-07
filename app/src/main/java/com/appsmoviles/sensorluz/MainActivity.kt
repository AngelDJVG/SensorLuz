package com.appsmoviles.sensorluz

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null
    private var textView: TextView? = null
    private var layout: View? = null
    private var imageView: ImageView? = null
    private var catImage: Int = R.drawable.cat_dancing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById<TextView>(R.id.textView)
        layout = findViewById<View>(R.id.layout)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        imageView = findViewById<ImageView>(R.id.imageView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            if (event.values[0] > 20) {
                textView!!.text = "Hay luz en el entorno"
                layout!!.setBackgroundColor(Color.WHITE)
                imageView!!.setImageResource(catImage)
            } else {
                textView!!.text = "No hay mucha luz en el entorno"
                layout!!.setBackgroundColor(Color.BLACK)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}