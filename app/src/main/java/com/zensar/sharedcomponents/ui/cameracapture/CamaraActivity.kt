package com.zensar.sharedcomponents.ui.cameracapture

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.cameracapturecomponent.CameraComponent
import com.android.cameracapturecomponent.interfaces.OnImageCaptureCallback
import com.android.cameracapturecomponent.util.CameraMode
import com.android.cameracapturecomponent.util.CameraSource
import com.bumptech.glide.Glide
import com.zensar.sharedcomponents.R

class CamaraActivity : AppCompatActivity() {

    private lateinit var showImage :ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)
        showImage = findViewById(R.id.show_image)
        photoCapture(CameraSource.INTENT)
    }

    private fun photoCapture(cameraSource: CameraSource) {
        val cameraComponent = CameraComponent()

        val fragment = cameraComponent.initCameraPreview(
            CameraMode.PHOTO,
            cameraSource = cameraSource
        )
        cameraComponent.enabledPreview(true)
        cameraComponent.setImageCaptureCallback(object : OnImageCaptureCallback {
            override fun onError(message: String) {
                Toast.makeText(
                    this@CamaraActivity,
                    "Photo capture failed: $message",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onImageCaptured(capturedImageUri: Uri) {
                Toast.makeText(
                    this@CamaraActivity,
                    "Photo captured: $capturedImageUri",
                    Toast.LENGTH_SHORT
                ).show()

                Glide.with(this@CamaraActivity).load(capturedImageUri).into(showImage)
            }
        })
        addFragment(fragment)
    }
    private fun addFragment(fragment: Fragment?) {
        fragment?.let {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fContainer, fragment)
            ft.addToBackStack(fragment::class.java.name)
            ft.commit()
        }
    }
}