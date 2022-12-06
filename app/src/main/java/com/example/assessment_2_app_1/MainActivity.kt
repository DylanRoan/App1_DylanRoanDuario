package com.example.assessment_2_app_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //declare variables here so they're accessible from other functions
    lateinit var elements : Array<ImageView>
    var maxPair = 2
    lateinit var pair : Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize the declared variables
        elements = arrayOf(anemo, geo, pyro, hydro, cryo, electro, dendro)
        pair = arrayOf()

        //create onClickListener(s) for the different elements/images
        for (e in elements)
        {
            e.setOnClickListener {
                arrayPairHandler(e) //handles the two empty boxes and the 'pair' array
            }
        }

        //listener for the combine button
        button.setOnClickListener {

            //checks if the pair array size is two
            if (pair.size >= maxPair)
            {
                //for the background dimming animation section
                var intent = Intent(this, Combine::class.java)

                intent.putExtra("first", pair[0].contentDescription.toString())
                intent.putExtra("second", pair[1].contentDescription.toString())

                intent.putExtra("result", elementCombiner())

                startActivity(intent)
            }
        }

        //posts popup view only after the launch screen
        main_layout.post { if (intent.getBooleanExtra("popup", false)) popupView()}

    }


    //handles the 'pair' array
    private fun arrayPairHandler(e: ImageView) {
        //Section to check if array has space and to add items
        var text = e.contentDescription.toString()
        if (e in pair)
        {
            val arr = pair.toMutableList()
            arr.remove(e)
            pair = arr.toTypedArray()
            text += " removed!"
        }
        else
        {
            //for when the array is empty
            if (pair.isEmpty())
            {
                pair = pair.plus(e)
                text += " added!"
            }
            else if (pair.size < maxPair)
            {
                pair = pair.plus(e)
                text += " added!"
            }
            else
            {
                text = "Can't add any more elements!"
            }
        }
        //Debugging
        //text = "(Anemo, Geo)? " + pair.inside(arrayOf(anemo, geo))
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        //For the boxes
        first.removeAllViews()
        second.removeAllViews()
        if (pair.isNotEmpty())
        {
            val imageView = ImageView(this)

            imageView.layoutParams = LinearLayout.LayoutParams(
                pair[0].width,
                pair[0].height)
                .apply {
                    gravity = Gravity.CENTER
                    weight = 1f
            }
            imageView.setImageDrawable(pair[0].drawable)

            first.addView(imageView)
        }
        if (pair.size > 1)
        {
            val imageView = ImageView(this)

            imageView.layoutParams = LinearLayout.LayoutParams(
                pair[1].width,
                pair[1].height)
                .apply {
                    gravity = Gravity.CENTER
                    weight = 1f
                }
            imageView.setImageDrawable(pair[1].drawable)

            second.addView(imageView)
        }
    }

    //function to handle all the reactions
    fun elementCombiner(): String
    {
        //Main Reactions
        if (pair.inside(arrayOf(pyro, hydro))) return "Steam"
        if (pair.inside(arrayOf(pyro, cryo))) return "Water"
        if (pair.inside(arrayOf(pyro, electro))) return "Overload"
        if (pair.inside(arrayOf(cryo, electro))) return "Superconduct"
        if (pair.inside(arrayOf(hydro, electro))) return "Electrocute"
        if (pair.inside(arrayOf(hydro, cryo))) return "Ice"

        //Anemo reactions
        if (pair.inside(arrayOf(anemo, pyro))) return "Pyronado"
        if (pair.inside(arrayOf(anemo, hydro))) return "Hurricane"
        if (pair.inside(arrayOf(anemo, cryo))) return "Freezenado"
        if (pair.inside(arrayOf(anemo, electro))) return "Thunderstorm"
        if (pair.inside(arrayOf(anemo, geo))) return "Sandstorm"
        if (pair.inside(arrayOf(anemo, dendro))) return "Dandelion"

        //Geo reactions
        if (pair.inside(arrayOf(geo, pyro))) return "Magma"
        if (pair.inside(arrayOf(geo, hydro))) return "Mud"
        if (pair.inside(arrayOf(geo, cryo))) return "Glacier"
        if (pair.inside(arrayOf(geo, electro))) return "Metal"
        if (pair.inside(arrayOf(geo, dendro))) return "Plant"

        //Dendro reactions
        if (pair.inside(arrayOf(dendro, pyro))) return "Burning"
        if (pair.inside(arrayOf(dendro, hydro))) return "Growing"
        if (pair.inside(arrayOf(dendro, cryo))) return "Winter"
        if (pair.inside(arrayOf(dendro, electro))) return "Photosynthesis"

        return "None"
    }

    //Function for the popup view
    fun popupView()
    {
        //inflate popup window
        var inflater : LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var popup : View = inflater.inflate(R.layout.popup_window, null)

        //create window
        var len : Int = LinearLayout.LayoutParams.WRAP_CONTENT
        var window = PopupWindow(popup, len, len, true)

        //show popup
        window.showAtLocation(calculator, Gravity.CENTER, 0, 0)

        //listener for when the popup is touched
        popup.setOnClickListener {
            window.dismiss()

        }


    }

    //function to check if two arrays of the same length are the same, ignoring order
    fun Array<*>.inside(other: Array<*>) = this.all { it in other } and (this.size == other.size)


}