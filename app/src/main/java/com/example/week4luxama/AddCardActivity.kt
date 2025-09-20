package com.example.week4luxama

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddCardActivity : AppCompatActivity() {
    private lateinit var db: FlashcardDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_card)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FlashcardDatabase(this)

        val backHomeBtn = findViewById<ImageView>(R.id.backHomeBtn)
        val savebutton = findViewById<ImageView>(R.id.savebutton)

        val inputQuestion = findViewById<EditText>(R.id.question)
        val answer1 = findViewById<EditText>(R.id.answer1)
        val answer2 = findViewById<EditText>(R.id.answer2)
        val answer3 = findViewById<EditText>(R.id.answer3)
        val answerSelector = findViewById<Spinner>(R.id.answerSelector)

        fun closeThis() {
            finish()
        }

        fun onSubmit() {
            if (inputQuestion.text.toString().isEmpty() ||
                answer1.text.toString().isEmpty() ||
                answer2.text.toString().isEmpty() ||
                answer3.text.toString().isEmpty()
            ) {
                val errorFields = listOf(inputQuestion, answer1, answer2, answer3)
                errorFields.forEach { it.hint = "Sa paka vid" } // "Cannot be empty"
            } else {
                // Determine which answer is correct based on the spinner
                val selected = answerSelector.selectedItem.toString()
                val correctAnswer: String
                val wrong1: String
                val wrong2: String

                when (selected) {
                    "Answer 1" -> {
                        correctAnswer = answer1.text.toString()
                        wrong1 = answer2.text.toString()
                        wrong2 = answer3.text.toString()
                    }
                    "Answer 2" -> {
                        correctAnswer = answer2.text.toString()
                        wrong1 = answer1.text.toString()
                        wrong2 = answer3.text.toString()
                    }
                    else -> { // "Answer 3"
                        correctAnswer = answer3.text.toString()
                        wrong1 = answer1.text.toString()
                        wrong2 = answer2.text.toString()
                    }
                }

                // Save flashcard to DB
                val flashcard = Flashcard(
                    question = inputQuestion.text.toString(),
                    answer = correctAnswer,
                    wrongAnswer1 = wrong1,
                    wrongAnswer2 = wrong2
                )
                db.insertCard(flashcard)

                closeThis()
            }
        }

        savebutton.setOnClickListener { onSubmit() }
        backHomeBtn.setOnClickListener { closeThis() }
    }
}
