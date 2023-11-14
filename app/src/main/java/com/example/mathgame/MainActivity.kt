package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var additionBtn: Button
    lateinit var subtractionBtn: Button
    lateinit var multiplicationBtn: Button
    lateinit var divisionBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        additionBtn = findViewById(R.id.buttonaddition)
        subtractionBtn = findViewById(R.id.buttonsubtraction)
        multiplicationBtn = findViewById(R.id.buttonmultiplication)
        divisionBtn = findViewById(R.id.buttondivision)

        additionBtn.setOnClickListener {
            startIntentGameActivity("+")
        }

        subtractionBtn.setOnClickListener {
            startIntentGameActivity("-")
        }

        multiplicationBtn.setOnClickListener {
            startIntentGameActivity("*")
        }

        divisionBtn.setOnClickListener {
            startIntentGameActivity("/")
        }
    }

    private fun startIntentGameActivity(operation: String) {
        val intent = Intent(this@MainActivity, GameActivity::class.java)
        intent.putExtra("operation", operation)
        startActivity(intent)
    }
}