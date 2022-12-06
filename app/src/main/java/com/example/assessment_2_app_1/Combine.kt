package com.example.assessment_2_app_1

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_combine.*
import kotlinx.android.synthetic.main.activity_combine.textView
import java.util.*
import kotlin.concurrent.schedule

class Combine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combine)

        //make output text and return button invisible
        textView.visibility = View.INVISIBLE
        button.visibility = View.INVISIBLE

        //gets and sets drawables for both elements
        var first : Drawable? = findDrawable(intent.getStringExtra("first"))
        var second : Drawable? = findDrawable(intent.getStringExtra("second"))
        imageView.setImageDrawable(first)
        imageView2.setImageDrawable(second)

        //gets and sets result of combination
        var result = intent.getStringExtra("result")
        textView.text = result

        //animation to shake both elements
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        imageView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))

        //timer to wait 750 ms before crashing the elements against each other
        Timer().schedule(750) {
            imageView.startAnimation(AnimationUtils.loadAnimation(this@Combine, R.anim.crash_right))
            imageView2.startAnimation(AnimationUtils.loadAnimation(this@Combine, R.anim.crash_left))

        }

        //timer to wait 100 ms after the first timer
        //to make the elements disappear and for the return button and output to appear
        Timer().schedule(850)
        {
            imageView.clearAnimation()
            imageView2.clearAnimation()

            //to run on thread
            this@Combine.runOnUiThread {
                imageView.visibility = View.INVISIBLE
                imageView2.visibility = View.INVISIBLE

                textView.visibility = View.VISIBLE
                button.visibility = View.VISIBLE
            }

            textView.startAnimation(AnimationUtils.loadAnimation(this@Combine, R.anim.scale))
        }

        //returns back to calculator
        button.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    //gets drawable from string
    fun findDrawable(e : String?) : Drawable?
    {
        if (e == "Anemo") return ResourcesCompat.getDrawable(resources, R.drawable.element_anemo, null)
        if (e == "Geo") return ResourcesCompat.getDrawable(resources, R.drawable.element_geo, null)
        if (e == "Dendro") return ResourcesCompat.getDrawable(resources, R.drawable.element_dendro, null)
        if (e == "Pyro") return ResourcesCompat.getDrawable(resources, R.drawable.element_pyro, null)
        if (e == "Cryo") return ResourcesCompat.getDrawable(resources, R.drawable.element_cryo, null)
        if (e == "Hydro") return ResourcesCompat.getDrawable(resources, R.drawable.element_hydro, null)
        return ResourcesCompat.getDrawable(resources, R.drawable.element_electro, null)
    }
}