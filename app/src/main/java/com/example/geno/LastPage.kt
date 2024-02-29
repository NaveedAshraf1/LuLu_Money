package com.example.geno

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class LastPage : AppCompatActivity() {
    var appInForeground = false
    private val handler = Handler()
    lateinit var time : TextView
    var startTime = 0L

//    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last)
        time = findViewById(R.id.time)
//        Toast.makeText(this, android.os.Build.MODEL, Toast.LENGTH_SHORT).show()

        val waitingTime = .5 * 60 * 60 * 1000L  // 24 hours in milliseconds

        findViewById<ImageView>(R.id.imageView4).setOnClickListener {
            Toast.makeText(this@LastPage, "Please wait ...", Toast.LENGTH_SHORT).show()
        }
        var   sharedPreferences = getSharedPreferences("TimerPrefs", Context.MODE_PRIVATE)

        if (sharedPreferences.contains("startTime")) {
            startTime = sharedPreferences.getLong("startTime", 0)
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - startTime
            if (elapsedTime < waitingTime) {
                startCountdownTimer(waitingTime.toLong() - elapsedTime)
            } else {
                time.text = "00:00"
            }
        } else {
            startTime = System.currentTimeMillis()
            sharedPreferences.edit().putLong("startTime", startTime).apply()
            startCountdownTimer(waitingTime.toLong())
        }
    }

    override fun onPause() {
        super.onPause()
        appInForeground = false
        handler.postDelayed({
            if (!appInForeground) {
//                hideApp()
            }
        }, 60 * 1000) // 1 minute
    }

    override fun onResume() {
        super.onResume()
        appInForeground = true
        handler.removeCallbacksAndMessages(null)
    }

//    private fun hideApp() {
//        if (android.os.Build.MODEL != "CPH2343"){
//            val packageManager = packageManager
//            val componentName = ComponentName(this@LastPage, SplashScreen::class.java)
//            packageManager.setComponentEnabledSetting(
//                componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP
//            )
//        }
//    }

    private fun startCountdownTimer(millisInFuture: Long) {
        object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / (60 * 1000) % 60
                val hours = millisUntilFinished / (60 * 60 * 1000)

                val timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                time.text = timeText
            }

            override fun onFinish() {
                time.text = "00:00:00"
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
//        countDownTimer.cancel()
    }

    companion object{
        val owner = "trueMoney"  // imran for blocked 1/28/2024
    }
}