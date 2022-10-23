package com.example.ee526kotlinfinal

import android.annotation.SuppressLint
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "SensorActivity"

class SensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorName: String

    private lateinit var resultDisplayCard: TextView
    private lateinit var proximityCard: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)


        sensorName = intent.getStringExtra("sensorName").toString()
        Log.v(TAG, "========================== $sensorName")

        resultDisplayCard = findViewById(R.id.tv_square)
        var sensorNameDisplay = findViewById<TextView>(R.id.sensorName)

        sensorNameDisplay.text = sensorName.uppercase()

        setUpSensorStuff()

    }



    private fun setUpSensorStuff() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager


        val deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)

        for(sensor in deviceSensors) {
            Log.d("All Sensors", sensor.toString())
        }

        //default
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
            "magneticField" -> {
                sensorType = Sensor.TYPE_MAGNETIC_FIELD
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

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")

            val Ax = event.values[0]
            val Ay = event.values[1]
            val Az = event.values[2]

       /*     resultDisplayCard.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }*/

            // Changes the colour of the resultDisplayCard if it's completely flat
     /*       val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.GREEN else Color.RED
            resultDisplayCard.setBackgroundColor(color)*/

            resultDisplayCard.text = "Acceleratation along x-axis: $Ax \n \n" +
                    "Acceleration along y-axis is $Ay \n \n" +
                    "Acceleration along z-axis is $Az "
        }

        if(event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val ms = Math.round(event.values[0])
            val my = Math.round(event.values[1])
            val mz = Math.round(event.values[2])

            resultDisplayCard.text = "Magnetic field along \n" +
                    "x-axis: $ms micro Tesla \n\n" +
                    "y-axis: $my micro Tesla\n\n" +
                    "z-axis: $mz micro Tesla\n\n"
        }

        if(event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            val mbarPressure = event.values[0]
            resultDisplayCard.text = "Pressure $mbarPressure mbar"
        }

        if(event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val scalarComponent = event.values[3]

            resultDisplayCard.text = "Rotation-X: $x \n\n" +
                    " Roation-Y: $y \n\n" +
                    " Rotation-Z: $z \n\n" +
                    " Scalar Component: $scalarComponent"
        }

        if(event?.sensor?.type == Sensor.TYPE_GRAVITY) {
            val x = event.values[0]
            resultDisplayCard.text = "Gravity sensor value:  ${x} m/s^2"
        }

        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            resultDisplayCard.text = "Rate of rotation around" +
                    "x-axis:  $x rad/s \n\n " +
                    "y-axis: $y rad/s \n\n " +
                    "z-axis: $z rad/s "
        }

        if(event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            val x = event.values[0]
            resultDisplayCard.text = "Ambient temp sensor value:  ${x} degree C"
        }

        if(event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val x = event.values[0]
            resultDisplayCard.text = "Light sensor value:  ${x} lx"
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