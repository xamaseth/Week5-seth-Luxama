package com.example.week4luxama

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var flashcardDatabase: FlashcardDatabase
    private lateinit var toggleBtn: ImageView

    private lateinit var questionplace: TextView
    private lateinit var option1: TextView
    private lateinit var option2: TextView
    private lateinit var option3: TextView
    private lateinit var allOptions: List<TextView>
    private var correctAnswer: TextView? = null

    private var allCards: List<Flashcard> = emptyList()
    private var currentIndex = 0
    private val history = mutableListOf<Int>() // Tracks viewed cards for back button

    private lateinit var nextBtn: ImageView
    private lateinit var backBtn: ImageView
    private lateinit var deletebt: ImageView
    private lateinit var editbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize database and views
        flashcardDatabase = FlashcardDatabase(this)
        nextBtn = findViewById(R.id.next)
        backBtn = findViewById(R.id.back)
        toggleBtn = findViewById<ImageView>(R.id.imageView)
        val layout1 = findViewById<LinearLayout>(R.id.linear)
        val addbt = findViewById<ImageView>(R.id.imageView2)
        deletebt = findViewById(R.id.deletebt)
        questionplace = findViewById(R.id.textView)
        option1 = findViewById(R.id.textView2)
        option2 = findViewById(R.id.textView3)
        option3 = findViewById(R.id.textView4)
        allOptions = listOf(option1, option2, option3)
        editbtn = findViewById(R.id.editbtn)

        // Toggle visibility of extra layout
        toggleBtn.setOnClickListener {
            if (layout1.isVisible) {
                layout1.visibility = View.INVISIBLE
                toggleBtn.setImageResource(R.drawable.myec_foreground)
            } else {
                layout1.visibility = View.VISIBLE
                toggleBtn.setImageResource(R.drawable.myeo_foreground)
            }
        }

        // Answer checking
        fun checkAnswer(correctView: TextView?, clickedView: TextView) {
            allOptions.forEach { it.isClickable = false }
            if (clickedView == correctView) {
                clickedView.setBackgroundResource(R.drawable.correctb)
            } else {
                clickedView.setBackgroundResource(R.drawable.incorrectb)
                correctView?.setBackgroundResource(R.drawable.correctb)
            }
        }

        option1.setOnClickListener { checkAnswer(correctAnswer, option1) }
        option2.setOnClickListener { checkAnswer(correctAnswer, option2) }
        option3.setOnClickListener { checkAnswer(correctAnswer, option3) }

        // Reset answers
        fun resetAnswers() {
            allOptions.forEach {
                it.setBackgroundResource(R.drawable.textde)
                it.isClickable = true
            }
        }
        questionplace.setOnClickListener { resetAnswers() }

        // Load all cards from DB
        allCards = flashcardDatabase.getAllCards()
        if (allCards.isNotEmpty()) loadRandomCard()

        checkEmpty() //  check once at start

        // Back button
        backBtn.setOnClickListener {
            if (history.size > 1) {
                resetAnswers()
                history.removeAt(history.size - 1) // Remove current card from history
                val previousIndex = history.last()
                currentIndex = previousIndex
                loadCard(currentIndex)
            }
        }

        // Next button -> random card
        nextBtn.setOnClickListener {
            resetAnswers()
            loadRandomCard()
        }

        // Add new card
        addbt.setOnClickListener {
            resetAnswers()
            layout1.visibility = View.INVISIBLE
            toggleBtn.setImageResource(R.drawable.myec_foreground)
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }

        // Delete current card
        deletebt.setOnClickListener {
            deleteCurrentCard()
        }
    }

    override fun onResume() {
        super.onResume()
        allCards = flashcardDatabase.getAllCards()
        checkEmpty()
        // Load a random card if there are any
        if (allCards.isNotEmpty()) {
            if (currentIndex >= allCards.size) currentIndex = allCards.size - 1
            loadCard(currentIndex)
        } else {
            questionplace.text = "No cards yet. Add one!"
            option1.text = ""
            option2.text = ""
            option3.text = ""
            correctAnswer = null
        }
    }

    //  check buttons visibility
    private fun checkEmpty() {
        if (allCards.isEmpty()) {
            nextBtn.isVisible = false
            backBtn.isVisible = false
            deletebt.isVisible = false
            toggleBtn.isVisible = false
        } else {
            nextBtn.isVisible = true
            backBtn.isVisible = true
            deletebt.isVisible = true
            toggleBtn.isVisible = true
        }
    }

    // Load a random card and track history
    private fun loadRandomCard() {
        if (allCards.isNotEmpty()) {
            currentIndex = Random.nextInt(allCards.size)
            history.add(currentIndex)
            loadCard(currentIndex)
        }
    }

    // Delete the current card
    private fun deleteCurrentCard() {
        if (allCards.isNotEmpty()) {
            val cardToDelete = allCards[currentIndex]

            // Delete from database
            flashcardDatabase.deleteCard(cardToDelete.question)

            // Remove from list
            allCards = allCards.toMutableList().apply { removeAt(currentIndex) }

            // Remove from history
            if (history.isNotEmpty()) history.removeAt(history.size - 1)

            // Load next card or show empty message
            if (allCards.isNotEmpty()) {
                if (currentIndex >= allCards.size) currentIndex = allCards.size - 1
                loadRandomCard()
            } else {
                questionplace.text = "No cards left. Add one!"
                option1.text = ""
                option2.text = ""
                option3.text = ""
                correctAnswer = null
            }
        }
        checkEmpty() // ✅ update button visibility after delete
    }

    // Load a card by index and shuffle answers
    private fun loadCard(index: Int) {
        val card = allCards[index]
        questionplace.text = card.question

        val answers = listOf(card.answer, card.wrongAnswer1, card.wrongAnswer2).shuffled()
        option1.text = answers[0]
        option2.text = answers[1]
        option3.text = answers[2]

        correctAnswer = allOptions.first { it.text == card.answer }
    }
}
