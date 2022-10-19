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

        val sensorMap: Any? = sensorObjectMap[sensorName]

        val _defaultSensorType: Int  = sensorMap.getValue("defaultSensorType")

        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(_defaultSensorType)?.also { sensor ->
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

            accelerometerCard.text = "Distance ${distance.toFloat()}"
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