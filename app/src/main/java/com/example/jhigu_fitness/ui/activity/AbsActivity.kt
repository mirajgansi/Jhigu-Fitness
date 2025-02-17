package com.example.jhigu_fitness.ui.activity.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.R

class AbsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abs) // Ensure you have an XML layout for this activity
    }
}
