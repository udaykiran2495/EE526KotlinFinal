package com.example.ee526kotlinfinal

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accelerometerBtn = findViewById<Button>(R.id.sensor_accelerometer)

        accelerometerBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "accelerometer")
            startActivity(intent)
        }

        val proximitySensorBtn = findViewById<Button>(R.id.sensor_proximity)

        proximitySensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "proximity")
            startActivity(intent)
        }

        val pressureSensorBtn = findViewById<Button>(R.id.sensor_pressure)

        pressureSensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "pressure")
            startActivity(intent)
        }

        val rotationSensorBtn = findViewById<Button>(R.id.sensor_rotation)

        rotationSensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "rotation")
            startActivity(intent)
        }

        val gravitySensorBtn = findViewById<Button>(R.id.sensor_gravity)

        gravitySensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "gravity")
            startActivity(intent)
        }

        val gyroscopeSensorBtn = findViewById<Button>(R.id.sensor_gyroscope)

        gyroscopeSensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "gyroscope")
            startActivity(intent)
        }

        val temperatureSensorBtn = findViewById<Button>(R.id.sensor_temperature)

        temperatureSensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "temperature")
            startActivity(intent)
        }

        val lightSensorBtn = findViewById<Button>(R.id.sensor_light)

        lightSensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "light")
            startActivity(intent)
        }


    }


}