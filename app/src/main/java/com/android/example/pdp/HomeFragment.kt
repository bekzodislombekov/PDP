package com.android.example.pdp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.example.pdp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.cardCourse.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", 1)
            findNavController().navigate(R.id.courseFragment, bundle)
        }

        binding.cardGroup.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", 2)
            findNavController().navigate(R.id.courseFragment, bundle)
        }

        binding.cardMentor.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", 3)
            findNavController().navigate(R.id.courseFragment, bundle)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "PDP"
        setHasOptionsMenu(false)
    }

}