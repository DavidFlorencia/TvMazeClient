package com.example.tvmazeclient.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tvmazeclient.viewmodel.LandingViewModel
import com.example.tvmazeclient.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {
    private lateinit var binding: FragmentLandingBinding
    private val viewModel: LandingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@LandingFragment
        }

        return binding.root
    }

    override fun onResume() {
        activity?.title = viewModel.getStringDate()
        super.onResume()
    }

}