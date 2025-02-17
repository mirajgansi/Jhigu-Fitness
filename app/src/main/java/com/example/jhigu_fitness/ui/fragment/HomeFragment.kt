package com.example.jhigu_fitness.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
=======
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the Abs Training TextView
        val absTextView: TextView = view.findViewById(R.id.absTextView)

        // Set a click listener on the Abs Training TextView
        absTextView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.Framecontent, AbsFragment()) // Navigate to AbsFragment
                .addToBackStack(null) // Allows back navigation
                .commit()
        }

        return view
>>>>>>> main
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}