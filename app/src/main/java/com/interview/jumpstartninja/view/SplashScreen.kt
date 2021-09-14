package com.interview.jumpstartninja.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.interview.jumpstartninja.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    var activitySplashBinding:ActivitySplashScreenBinding?=null
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding= ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(activitySplashBinding!!.root)

        activityScope.launch {
            delay(3000)
            var intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()
    }
}