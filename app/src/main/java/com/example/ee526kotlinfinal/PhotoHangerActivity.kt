package com.example.ee526kotlinfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.ee526kotlinfinal.databinding.ActivityPhotoHangerBinding
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture

class PhotoHangerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoHangerBinding

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector

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

        cameraProviderResult.launch(android.Manifest.permission.CAMERA)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        startCamera()
    }

    private fun startCamera(){
        // listening for data from the camera
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // connecting a preview use case to the preview in the xml file.
            val preview = Preview.Builder().build().also{
                it.setSurfaceProvider(binding.preview.surfaceProvider)
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