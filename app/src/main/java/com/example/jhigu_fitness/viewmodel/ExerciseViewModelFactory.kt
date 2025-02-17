package com.example.jhigu_fitness.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jhigu_fitness.repository.ExerciseRepository

class ExerciseViewModelFactory(private val repo: ExerciseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
