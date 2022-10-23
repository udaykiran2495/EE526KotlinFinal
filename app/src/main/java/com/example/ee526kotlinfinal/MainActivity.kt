package com.example.ee526kotlinfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val photoHangerAssistantBtn = findViewById<Button>(R.id.photo_hanger_asst)

        photoHangerAssistantBtn.setOnClickListener {
            val intent = Intent(this, PhotoHangerActivity::class.java)
            startActivity(intent)
        }

        val accelerometerBtn = findViewById<Button>(R.id.sensor_accelerometer)

        accelerometerBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "accelerometer")
            startActivity(intent)
        }

        val magneticSensorBtn = findViewById<Button>(R.id.sensor_magnetic_field)

        magneticSensorBtn.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sensorName", "magneticField")
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