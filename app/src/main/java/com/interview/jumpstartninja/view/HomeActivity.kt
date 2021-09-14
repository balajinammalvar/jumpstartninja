package com.interview.jumpstartninja.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.interview.jumpstartninja.databinding.ActivityMainBinding
import com.interview.jumpstartninja.utilis.ImageUtils
import com.interview.jumpstartninja.utilis.Status.*
import online.interview.teamtweaks.viewmodel.user.user.PostmageViewModel


class HomeActivity : AppCompatActivity() {

    var activityHomeBind: ActivityMainBinding? = null;
    private val cameraRequest = 1888
    lateinit var mPostmageViewModel: PostmageViewModel
    lateinit var imageByteArray:ByteArray;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPostmageViewModel = ViewModelProvider(this).get(PostmageViewModel::class.java)
        activityHomeBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityHomeBind!!.root)
        activityHomeBind!!.btnSelect.setOnClickListener {
            if (cameraPermission()) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                } else {
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                }
//                when {
//                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O -> {
//                        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT)  // Tested on API 24 Android version 7.0(Samsung S6)
//                    }
//                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
//                        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT) // Tested on API 27 Android version 8.0(Nexus 6P)
//                        cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
//                    }
//                    else -> cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1)  // Tested API 21 Android version 5.0.1(Samsung S4)
//                }
                activityLauncher.launch(cameraIntent)
            }
        }
        activityHomeBind!!.btnSend.setOnClickListener {
            postImage(imageByteArray)
        }
    }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            println(result)
            val photo: Bitmap = result?.data?.extras?.get("data") as Bitmap
            activityHomeBind!!.ivImage.setImageBitmap(photo)
            imageByteArray = ImageUtils.getBitmapAsByteArray(photo)
            postImage(imageByteArray)
            activityHomeBind!!.btnSend.setVisibility(VISIBLE)
            activityHomeBind!!.btnSelect.setVisibility(INVISIBLE)
        }

    private fun cameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )
            return false
        } else {
            return true
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun postImage(image: ByteArray) {
        mPostmageViewModel.postImage(image)?.observe(this, Observer {
            when (it.status) {
                LOADING -> {
                    Toast.makeText(applicationContext, "Loading", Toast.LENGTH_SHORT).show()
                }
                ERROR -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
                SUCCESS -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}