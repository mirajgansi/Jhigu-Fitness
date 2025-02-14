package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityWorkOutDetailBinding
import com.example.jhigu_fitness.repository.ProductRepositoryImp
import com.example.jhigu_fitness.viewmodel.ProductViewModel

class WorkOutDetailActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var timerText: TextView
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 60000 // 60 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out_detail)

        startButton = findViewById(R.id.startButton)
        progressBar = findViewById(R.id.progressBar2)
        timerText = findViewById(R.id.timerText)

        startButton.setOnClickListener {
            startTimer()
        }
    }

    private fun startTimer() {
        // Cancel previous timer if running
        countDownTimer?.cancel()

        // Initialize timer
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerUI()
            }

            override fun onFinish() {
                // Timer finished actions
                timerText.text = "Done!"
                progressBar.progress = 0
            }
        }.start()
    }

    private fun updateTimerUI() {
        val seconds = timeLeftInMillis / 1000
        timerText.text = seconds.toString()
        progressBar.progress = seconds.toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Prevent memory leaks
    }
}