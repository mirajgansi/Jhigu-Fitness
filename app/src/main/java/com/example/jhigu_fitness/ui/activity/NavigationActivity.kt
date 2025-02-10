
package com.example.jhigu_fitness.ui.activity.ui.activity

//import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityNavigationBinding
import com.example.jhigu_fitness.ui.fragment.HomeFragment
import com.example.jhigu_fitness.ui.fragment.SettingFragment
import com.example.jhigu_fitness.ui.fragment.WorkoutDashboardFragment
import com.example.jhigu_fitness.ui.fragment.WorkoutFragment


class NavigationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNavigationBinding
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(WorkoutDashboardFragment())

        binding.buttomNavigation.setOnItemSelectedListener {menu->
            when(menu.itemId){
                R.id.nav_home ->replaceFragment(WorkoutDashboardFragment())
                R.id.nav_settings -> replaceFragment(SettingFragment())
                R.id.nav_workouts -> replaceFragment(HomeFragment())
                R.id.nav_profile -> replaceFragment(HomeFragment())

                else -> {}
            }
            true
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }


}
