package com.example.jhigu_fitness.ui.activity.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.ui.activity.GoalActivity


class GenderActivity : AppCompatActivity() {

    private var selectedCard: CardView? = null
    private var currentProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        setupCardViews()
        setupButtonListeners()
    }

    private fun setupCardViews() {
        val cardViews = listOf<CardView>(
            findViewById(R.id.optionMale),  // Make sure these IDs match your XML
            findViewById(R.id.optionFemale),
            findViewById(R.id.optionOther),
            findViewById(R.id.optionNot)
        )

        cardViews.forEach { cardView ->
            cardView.setOnClickListener {
                // Clear previous selection
                selectedCard?.setCardBackgroundColor(
                    ContextCompat.getColor(this, android.R.color.darker_gray)
                )

                // Set new selection
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
                selectedCard = cardView
            }
        }
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            // Animate progress bar
            animateProgressBar(currentProgress, currentProgress + 25) {
                // Navigate after animation completes
                startActivity(Intent(this, GoalActivity::class.java))
            }
            currentProgress += 25
        }

        findViewById<View>(R.id.backArrow).setOnClickListener {
            finish()
        }
    }

    private fun animateProgressBar(start: Int, end: Int, onEnd: () -> Unit) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val animator = ObjectAnimator.ofInt(progressBar, "progress", start, end)
        animator.duration = 500
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                onEnd()
            }
        })
        animator.start()
    }
}