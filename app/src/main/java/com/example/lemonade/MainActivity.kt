package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    private val SELECT = "select"
    private val SQUEEZE = "squeeze"
    private val DRINK = "drink"
    private val RESTART = "restart"
    private val FINAL = "final"
    private var lemonadeState = "select"
    private var lemonSize = -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        btn.setOnClickListener {
            clickLemonImage()
        }
        lemonImage!!.setOnClickListener {
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            showSnackbar()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }


    private fun clickLemonImage() {
        when(lemonadeState){
            SELECT -> {
                lemonadeState = SQUEEZE
                lemonSize = lemonTree.pick()
                squeezeCount = 0
            }
            SQUEEZE -> {
                squeezeCount++
                lemonSize--
                if(lemonSize == 0){
                    lemonadeState = DRINK
                    lemonSize = -1
                }
            }
            DRINK -> {
                lemonadeState = RESTART
            }
            RESTART -> {
                lemonadeState = FINAL
            }
            FINAL -> {
                lemonadeState = SELECT
            }

        }
        setViewElements()
    }

    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        when(lemonadeState){
            SELECT -> {
                textAction.text = resources.getString(R.string.lemon_select)
                lemonImage?.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE -> {
                textAction.text = resources.getString(R.string.lemon_squeeze)
                lemonImage?.setImageResource(R.drawable.lemon_squeeze)
            }
            DRINK -> {
                textAction.text = resources.getString(R.string.lemon_drink)
                lemonImage?.setImageResource(R.drawable.lemon_drink)
            }
            RESTART -> {
                textAction.text = resources.getString(R.string.lemon_empty_glass)
                lemonImage?.setImageResource(R.drawable.lemon_restart)
            }
            FINAL -> {
                textAction.text = resources.getString(R.string.lemon_final)
                lemonImage?.setImageResource(R.drawable.lemon_final)
            }
        }

    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}