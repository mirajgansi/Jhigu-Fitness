package com.example.jhigu_fitness.ui.activity

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.databinding.ActivityWorkOutDetailBinding

class WorkOutDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkOutDetailBinding
    private var attemptCount = 0
    private var successfulLoad = false
    private lateinit var possibleUrls: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWorkOutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseId = intent.getStringExtra("EXERCISE_ID")
        val name = intent.getStringExtra("EXERCISE_NAME")
        val sets = intent.getStringExtra("EXERCISE_REPS")?.toIntOrNull() ?: 0
        val desc = intent.getStringExtra("EXERCISE_DESC")
        val image = intent.getStringExtra("EXERCISE_IMAGE") // Cloudinary GIF URL

        if (exerciseId.isNullOrEmpty()) {
            Toast.makeText(this, "Exercise ID is missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Log.d(
            "WorkOutDetailActivity",
            "Exercise ID: $exerciseId, Name: $name, Sets: $sets, Desc: $desc, Image: $image"
        )

        binding.displayExercisename.text = name
        binding.displayExerciseSets.text = "Sets: $sets"

        // Load the exercise video with multiple fallback options
        if (!image.isNullOrEmpty()) {
            loadExerciseVideo(image)
        } else {
            Log.w("CLOUDINARY_VIDEO", "No image URL provided")
            Toast.makeText(this, "No exercise video available", Toast.LENGTH_SHORT).show()
        }

        // Initialize reps and weight
        var currentReps = ""
        var currentWeight = ""

        binding.editReps.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentReps = s.toString()
                binding.displayTempReps.text = "Reps: ${currentReps.ifEmpty { "-" }}"
            }
        })

        binding.editWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentWeight = s.toString()
                binding.displayTempWeight.text = "Weight: ${currentWeight.ifEmpty { "-" }} kg"
            }
        })

        binding.finishButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Workout Complete")
                .setMessage("Good Job!")
                .setPositiveButton("OK") { _, _ ->
                    val resultIntent = Intent().apply {
                        putExtra("EXERCISE_ID", exerciseId)
                        putExtra("EXERCISE_NAME", name)
                        putExtra("EXERCISE_REPS", currentReps.ifEmpty { sets.toString() })
                        putExtra("EXERCISE_WEIGHT", currentWeight)
                        putExtra("EXERCISE_DESC", desc)
                        putExtra("EXERCISE_IMAGE", image)
                    }
                    setResult(RESULT_OK, resultIntent)

                    binding.editReps.text.clear()
                    binding.editWeight.text.clear()
                    binding.displayTempReps.text = "Reps: -"
                    binding.displayTempWeight.text = "Weight: -"

                    binding.videoExercise.stopPlayback()
                    finish()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun loadExerciseVideo(imageUrl: String) {
        // Create a list of possible URL transformations to try
        possibleUrls = listOf(
            // 1. Standard transformation (direct replacement)
            imageUrl.replace("image/upload", "video/upload").replace(".gif", ".mp4"),

            // 2. Transformation with slashes
            imageUrl.replace("/image/upload/", "/video/upload/").replace(".gif", ".mp4"),

            // 3. Try with f_mp4 transformation parameter for direct delivery
            if (imageUrl.contains("/upload/")) {
                imageUrl.replace("/upload/", "/upload/f_mp4/").replace(".gif", ".mp4")
            } else {
                imageUrl.replace("image/upload", "image/upload/f_mp4").replace(".gif", ".mp4")
            },

            // 4. Try just extension change
            imageUrl.replace(".gif", ".mp4"),

            // 5. Try direct gif URL in case the VideoView can handle it
            imageUrl
        )

        // Log all URLs we're going to try
        for ((index, url) in possibleUrls.withIndex()) {
            Log.d("CLOUDINARY_URL", "URL attempt $index: $url")
        }

        // Reset counters
        attemptCount = 0
        successfulLoad = false

        // Start trying URLs
        tryNextUrl()
    }

    private fun tryNextUrl() {
        if (attemptCount >= possibleUrls.size) {
            Log.e("CLOUDINARY_VIDEO_ERROR", "All URL attempts failed")
            Toast.makeText(
                this,
                "Failed to load exercise video after multiple attempts. Please check your internet connection.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val urlToTry = possibleUrls[attemptCount]
        Log.d("CLOUDINARY_VIDEO", "Attempting to load URL #${attemptCount+1}: $urlToTry")

        try {
            // Reset the video view for a clean attempt
            binding.videoExercise.stopPlayback()

            // Set up the video with the current URL
            binding.videoExercise.setVideoURI(Uri.parse(urlToTry))

            binding.videoExercise.setOnPreparedListener { mediaPlayer ->
                Log.d("CLOUDINARY_VIDEO", "✅ Success! Video loaded with URL #${attemptCount+1}: $urlToTry")
                successfulLoad = true
                mediaPlayer.isLooping = true
                mediaPlayer.start()
            }

            binding.videoExercise.setOnErrorListener { mp, what, extra ->
                Log.e("CLOUDINARY_VIDEO_ERROR", "Attempt #${attemptCount+1} failed: what=$what, extra=$extra, URL=$urlToTry")

                // Log detailed error info
                when(what) {
                    MediaPlayer.MEDIA_ERROR_UNKNOWN -> Log.e("VIDEO_ERROR", "Unknown error")
                    MediaPlayer.MEDIA_ERROR_SERVER_DIED -> Log.e("VIDEO_ERROR", "Server died")
                }

                when(extra) {
                    MediaPlayer.MEDIA_ERROR_IO -> Log.e("VIDEO_ERROR", "IO error - Check network connection")
                    MediaPlayer.MEDIA_ERROR_MALFORMED -> Log.e("VIDEO_ERROR", "Malformed media - The MP4 might not exist")
                    MediaPlayer.MEDIA_ERROR_UNSUPPORTED -> Log.e("VIDEO_ERROR", "Unsupported format - Video format not supported")
                    MediaPlayer.MEDIA_ERROR_TIMED_OUT -> Log.e("VIDEO_ERROR", "Timed out - Connection took too long")
                    -1005 -> Log.e("VIDEO_ERROR", "Error -1005 - Typically indicates a network or connection issue")
                }

                // Move to the next URL if this one failed
                attemptCount++
                if (!successfulLoad && attemptCount < possibleUrls.size) {
                    Log.d("CLOUDINARY_VIDEO", "Trying next URL...")
                    tryNextUrl()
                }
                true // Error handled
            }

            // Start loading the video
            binding.videoExercise.requestFocus()

        } catch (e: Exception) {
            Log.e("CLOUDINARY_VIDEO_ERROR", "Exception setting video URI for attempt #${attemptCount+1}", e)
            // Try the next URL on exception
            attemptCount++
            if (attemptCount < possibleUrls.size) {
                tryNextUrl()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause video when activity is paused
        try {
            if (binding.videoExercise.isPlaying) {
                binding.videoExercise.pause()
            }
        } catch (e: Exception) {
            Log.e("VIDEO_ERROR", "Error pausing video", e)
        }
    }

    override fun onResume() {
        super.onResume()
        // Resume video playback when activity is resumed
        try {
            if (successfulLoad && !binding.videoExercise.isPlaying) {
                binding.videoExercise.start()
            }
        } catch (e: Exception) {
            Log.e("VIDEO_ERROR", "Error resuming video", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Properly release video resources
        try {
            binding.videoExercise.stopPlayback()
        } catch (e: Exception) {
            Log.e("VIDEO_ERROR", "Error stopping video playback", e)
        }
    }
}