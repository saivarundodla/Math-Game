package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textScore: TextView
    lateinit var textLife: TextView
    lateinit var textTime: TextView

    lateinit var textQuestion: TextView
    lateinit var editTextAnswer: EditText
    lateinit var buttonOk: Button
    lateinit var buttonNext: Button

    private var correctAns = 0
    private var userScore = 0
    private var userLife = 3

    private lateinit var timer: CountDownTimer
    private val startTimerInMillis: Long = 60000
    var timeLeftInMillis: Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar!!.title = "Addition"

        textScore = findViewById(R.id.textview_score_value)
        textLife = findViewById(R.id.textview_life_value)
        textTime = findViewById(R.id.textview_time_value)

        textQuestion = findViewById(R.id.textview_question)
        editTextAnswer = findViewById(R.id.editext_answer)
        buttonOk = findViewById(R.id.button_ok)
        buttonNext = findViewById(R.id.button_next)

        var operation = intent.getStringExtra("operation")!!

        gameContinue(operation)

        buttonOk.setOnClickListener {
            val input = editTextAnswer.text.toString()
            Log.d("Button_Ok", input)
            if (input.isEmpty() || input.isBlank()) {
                Snackbar.make(buttonOk, "Input cannot be empty or blank", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                pauseTimer()
                if (input.toInt() == correctAns) {
                    Log.d("Button_Ok", "Correct Ans")
                    textQuestion.text = "Congratulations, your answer is correct"
                    textScore.text = "${userScore + 10}";
                } else {
                    if (userLife <= 0) {
                        intentToResultActivity()
                    } else {
                        Log.d("Button_Ok", "Wrong Ans")
                        userLife--;
                        textQuestion.text = "Sorry, your answer is wrong"
                        textLife.text = userLife.toString()
                    }
                }
            }
        }

        buttonNext.setOnClickListener {
            val input = editTextAnswer.text.toString()
            if (input.isEmpty() || input.isBlank()) {
                Snackbar.make(buttonNext, "Please provide your answer", Snackbar.LENGTH_LONG).show()
            } else {
                if (userLife == 0) {
                    Snackbar.make(buttonNext, "Game Over", Snackbar.LENGTH_LONG).show()
                    intentToResultActivity()
                } else {
                    pauseTimer()
                    resetTimer()
                    gameContinue(operation)
                    editTextAnswer.setText("")
                }
            }
        }
    }

    private fun intentToResultActivity() {
        val intent = Intent(this@GameActivity, ResultActivity::class.java)
        intent.putExtra("score", userScore)
        startActivity(intent)
        finish()
    }

    private fun generateNumber(): Int {
        return Random.nextInt(0, 1000)
    }

    private fun gameContinue(operation: String) {
        var num1 = generateNumber()
        var num2 = generateNumber()
        if (num1 == 0) {
            num1 = generateNumber()
        }
        if (num2 == 0) {
            num2 = generateNumber()
        }

        correctAns = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            else -> {
                num1 / num2
            }
        }
        textQuestion.text = "$num1 $operation $num2"
        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Sorry, your time is up"
            }

        }.start()

    }

    private fun updateText() {
        val remainingTime: Int = (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }
}