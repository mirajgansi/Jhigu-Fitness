package com.example.jhigu_fitness.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R

class MotivationActivity : AppCompatActivity() {

        private lateinit var buttonNext: Button
        private lateinit var options: List<Button>
     private lateinit var progressBar: ProgressBar
        private var selectedOption: Button? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_motivation)

            // Initialize views
            buttonNext = findViewById(R.id.buttonNext)
            progressBar = findViewById(R.id.progressBar)
            options = listOf(
                findViewById(R.id.optionFeelConfident),
                findViewById(R.id.optionImproveHealth),
                findViewById(R.id.optionBoostEnergy),
                findViewById(R.id.optionReleaseStress)
            )


            setupProgressBar()
            setupOptions()
            setupNextButton()
        }

    private fun setupProgressBar() {
        progressBar.max = 100
        progressBar.progress = 25 // Initial progress from previous step
    }

    private fun setupOptions() {
        options.forEach { button ->
            button.setOnClickListener {
                selectOption(button)
            }
        }
    }

    private fun setupNextButton() {
        buttonNext.isEnabled = false
        buttonNext.setOnClickListener {
            if (selectedOption != null) {
                animateProgressAndNavigate()
            }
        }
    }

    private fun selectOption(selectedButton: Button) {
        // Reset all options to default color
        options.forEach { button ->
            button.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.black)
            )
        }

        // Set selected option to red
        selectedButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.red)
        )
        selectedOption = selectedButton

        // Enable Next button
        buttonNext.isEnabled = true
    }

    private fun animateProgressAndNavigate() {
        val currentProgress = progressBar.progress
        val targetProgress = 50 // 25% increment

        val progressAnimator = ObjectAnimator.ofInt(
            progressBar,
            "progress",
            currentProgress,
            targetProgress
        ).apply {
            duration = 500
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    navigateToGoalScreen()
                }
            })
        }
        progressAnimator.start()
    }

    private fun navigateToGoalScreen() {
        startActivity(Intent(this, GoalActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}