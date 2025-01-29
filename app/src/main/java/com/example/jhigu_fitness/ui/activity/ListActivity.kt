package com.example.jhigu_fitness.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.ui.fragment.HomeFragment
import com.example.jhigu_fitness.ui.fragment.WorkoutFragment
import com.example.jhigu_fitness.ui.fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val cardAbs: CardView = findViewById(R.id.cardAbs)
        val cardChest: CardView = findViewById(R.id.cardChest)
        val cardBack: CardView = findViewById(R.id.cardBack)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Set default fragment
        replaceFragment(WorkoutFragment())

        // Set OnClickListener for each CardView
        cardAbs.setOnClickListener {
            navigateToDetails("Abs")
        }

        cardChest.setOnClickListener {
            navigateToDetails("Chest")
        }

        cardBack.setOnClickListener {
            navigateToDetails("Back")
        }

        // Handle Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_workouts -> replaceFragment(WorkoutFragment())
                R.id.nav_settings -> replaceFragment(SettingFragment())
//                R.id.nav_profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun navigateToDetails(category: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }
}
