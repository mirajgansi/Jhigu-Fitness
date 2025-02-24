package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.FragmentWorkoutPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class WorkoutPage : Fragment() {
    private lateinit var binding: FragmentWorkoutPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupBottomNavigation()
    }

    private fun setupClickListeners() {
        // Workout buttons
        binding.btnCrunches.setOnClickListener { handleWorkoutClick("Kneeling Cable Crunches") }
        binding.btnTwisting.setOnClickListener { handleWorkoutClick("Twisting Crunch") }
        binding.btnObliques.setOnClickListener { handleWorkoutClick("External Obliques") }
        binding.btnLegRaises.setOnClickListener { handleWorkoutClick("Lying Leg Raises") }

        // Start workout button
        binding.btnStartWorkout.setOnClickListener { startWorkout() }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> navigateToHome()
                R.id.nav_workout -> Unit // Current screen
                R.id.nav_settings -> navigateToSettings()
                R.id.nav_profile -> navigateToProfile()
            }
            true
        }
    }

    private fun handleWorkoutClick(workoutName: String) {
        showToast("Selected: $workoutName")
        // Add your workout selection logic here
    }

    private fun startWorkout() {
        showToast("Starting workout session!")
        // Add workout start logic here
    }

    private fun navigateToHome() {
        // Implement home navigation
        showToast("Navigating to Home")
    }

    private fun navigateToSettings() {
        // Implement settings navigation
        showToast("Navigating to Settings")
    }

    private fun navigateToProfile() {
        // Implement profile navigation
        showToast("Navigating to Profile")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkoutPage() // Simplified version
    }
}