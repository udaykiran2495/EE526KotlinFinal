package com.example.ee526kotlinfinal

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "MyActivity"

val accelerometerMap = mapOf(
    "name" to "accelerometer",
    "defaultSensorType" to Sensor.TYPE_ACCELEROMETER,
)

val proximityMap = mapOf(
    "name" to "proximity",
    "defaultSensorType" to Sensor.TYPE_PROXIMITY
)

val sensorObjectMap = mapOf<String, Any?>(
    "accelerometer" to accelerometerMap,
    "proximity" to proximityMap
)


class SensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorName: String

    private lateinit var accelerometerCard: TextView
    private lateinit var proximityCard: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)


        sensorName = intent.getStringExtra("sensorName").toString()
        Log.v(TAG, "========================== " + sensorName)

        accelerometerCard = findViewById(R.id.tv_square)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        var sensorType = Sensor.TYPE_ACCELEROMETER

        when (sensorName) {
            "accelerometer" -> {
                sensorType = Sensor.TYPE_ACCELEROMETER
            }
            "pressure" -> {
                sensorType = Sensor.TYPE_PRESSURE
            }
            "rotation" -> {
                sensorType = Sensor.TYPE_ROTATION_VECTOR
            }
            "gravity" -> {
                sensorType = Sensor.TYPE_GRAVITY
            }
            "gyroscope" -> {
                sensorType = Sensor.TYPE_GYROSCOPE
            }
            "temperature" -> {
                sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
            }
            "light" -> {
                sensorType = Sensor.TYPE_LIGHT
            }
            "proximity" -> {
                sensorType = Sensor.TYPE_PROXIMITY
            } else -> {
                Log.v(TAG, "Error")
            }
        }

        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(sensorType)?.also { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")

            // Sides = Tilting phone left(10) and right(-10)
            val sides = event.values[0]

            // Up/Down = Tilting phone up(10), flat (0), upside-down(-10)
            val upDown = event.values[1]

            accelerometerCard.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            // Changes the colour of the accelerometerCard if it's completely flat
            val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.GREEN else Color.RED
            accelerometerCard.setBackgroundColor(color)

            accelerometerCard.text = "up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}"
        }

        if(event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distance = event.values[0]
            accelerometerCard.text = "Distance ${distance}"
        }

        if(event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            val mbarPressure = event.values[0]
            accelerometerCard.text = "Pressure ${mbarPressure} mmhg"
        }

        if(event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val x = event.values[0]
            val y = event.values[1]
            val z= event.values[2]
            accelerometerCard.text = "Rotationx: ${x} \n RoationY: ${y} \n RotationZ: ${z}"
        }

        if(event?.sensor?.type == Sensor.TYPE_GRAVITY) {
            val x = event.values[0]
            accelerometerCard.text = "Gravity sensor value:  ${x} m/s^2"
        }

        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            accelerometerCard.text = "Gravity sensor value:  ${x} m/s^2"
        }

        if(event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            val x = event.values[0]
            accelerometerCard.text = "Ambient temp sensor value:  ${x} degree C"
        }

        if(event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val x = event.values[0]
            accelerometerCard.text = "Light sensor value:  ${x} lx"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}