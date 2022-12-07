package com.example.assessment_2_app_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_launch_screen.*
import java.util.*
import kotlin.concurrent.schedule

class LaunchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_launch_screen)

        //first bubble
        bubble_1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bubble))

        //delayed second bubble
        Timer().schedule(200)
        {
            bubble_2.startAnimation(AnimationUtils.loadAnimation(this@LaunchScreen, R.anim.bubble))
        }

        //delayed third bubble
        Timer().schedule(500)
        {
            bubble_3.startAnimation(AnimationUtils.loadAnimation(this@LaunchScreen, R.anim.bubble))
        }

        //2 seconds before going to calculator screen
        Timer().schedule(2000)
        {
            var intent = Intent(this@LaunchScreen, MainActivity::class.java)
            intent.putExtra("popup", true)
            startActivity(intent)
        }
    }
}