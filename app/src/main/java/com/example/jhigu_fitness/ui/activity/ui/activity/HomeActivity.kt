//package com.example.jhigu_fitness.ui.activity.ui.activity
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentTransaction
//import com.example.jhigu_fitness.R
//import com.example.jhigu_fitness.databinding.ActivityHomeBinding
//import com.example.jhigu_fitness.ui.activity.ui.fragment.HomeFragment
//
//class HomeActivity : AppCompatActivity() {
//
//    lateinit var binding: ActivityHomeBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//
//        setContentView(R.layout.activity_home)
//        replaceFragment(HomeFragment())
//
//        binding.btnFirst.setOnClickListener {
//            replaceFragment(FirstFragment())
//
//        }
//        binding.btnSecond.setOnClickListener {
//            replaceFragment(SecondFragment())
//
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager : FragmentManager = supportFragmentManager
//
//        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
//
//        fragmentTransaction.replace(R.id.,fragment)
//        fragmentTransaction.commit()
//    }
//
//}