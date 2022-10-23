package com.example.ee526kotlinfinal

import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.Preview.SurfaceProvider
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.ee526kotlinfinal.databinding.ActivityPhotoHangerBinding
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import kotlin.math.atan
import kotlin.math.sqrt

class PhotoHangerActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityPhotoHangerBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private lateinit var surfaceProvider: SurfaceProvider

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorText: TextView

    private lateinit var lineViewLeft: ImageView
    private lateinit var lineViewRight: ImageView

    private lateinit var rotate: RotateAnimation

    private var currAngle :Float = 1.5708f//90 degrees in radians

    val degreeConvertConst = 57.2958f

    private val cameraProviderResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){ permissionGranted->
        if(permissionGranted){
            // cut and paste the previous startCamera() call here.
            startCamera()
        }else {
            Snackbar.make(binding.root,"The camera permission is required", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoHangerBinding.inflate(layoutInflater)
        setContentView(binding.root)

  /*      sensorText = findViewById(R.id.textView3)*/
        surfaceProvider = binding.preview.surfaceProvider
        cameraProviderResult.launch(Manifest.permission.CAMERA)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        lineViewRight = findViewById(R.id.straight_line)
        lineViewLeft = findViewById(R.id.straight_line2)

        startCamera()
        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        //sensor stuff
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {sensor ->
            sensorManager.registerListener(this,
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    private fun getAngleOfInclination(ax: Float, ay: Float, az: Float ): Float {
        val s1 = ay*ay + az*az
        val s2 = sqrt(s1)
        val s3 = ax/s2
        return atan(s3) // radians
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")
            val ax = event.values[0]
            val ay = event.values[1]
            val az = event.values[2]

            var newAngle = getAngleOfInclination(ax, ay, az)

            val currAngleDegrees = Math.round(currAngle*180/Math.PI)
            val newAngleDegress = Math.round(newAngle*180/Math.PI)

            Log.d("ANGLESDEGREES currAngleDegrees ", "=========== $currAngleDegrees")
            Log.d("ANGLESDEGREES newAngleDegress ", "=========== $newAngleDegress")

            val rotate = RotateAnimation(
                -currAngleDegrees.toFloat(),-newAngleDegress.toFloat(),
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f
            )
            currAngle = newAngle
            rotate.duration = 4000
            lineViewLeft.animation = rotate
            lineViewRight.animation = rotate
/*            sensorText.text = "left/right ${ax.toInt()} \n angle : $newAngle"*/
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    private fun startCamera(){
        // listening for data from the camera
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            // connecting a preview use case to the preview in the xml file.
            val preview = Preview.Builder().build().also{
                it.setSurfaceProvider(surfaceProvider)
            }
            try{
                // clear all the previous use cases first.
                cameraProvider.unbindAll()
                // binding the lifecycle of the camera to the lifecycle of the application.
                cameraProvider.bindToLifecycle(this,cameraSelector,preview)
            } catch (e: Exception) {
                Log.d(TAG, "Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(this))
    }

    companion object {
        val TAG = "PhotoHangerActivity"
    }
}