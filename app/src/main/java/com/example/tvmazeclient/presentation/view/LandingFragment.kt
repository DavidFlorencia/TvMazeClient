package com.example.tvmazeclient.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModel
import com.example.tvmazeclient.databinding.FragmentLandingBinding
import com.example.tvmazeclient.presentation.MainActivity

class LandingFragment : Fragment() {
    private lateinit var binding: FragmentLandingBinding
    private lateinit var viewModel: LandingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@LandingFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        /**
         * observador que se detona al iniciar este fragment
         * da inicio al proceso de consumo de la programación
         * del día
         */
        viewModel.dateIso8601.observe(viewLifecycleOwner) { date ->
            viewModel.getShowsSchedule(date)
        }

        /**
         * observador que se detona al finalizar el consumo del
         * servicio web de programación del día, muestra la respuesta
         * del consumo en texto plano
         */
        viewModel.currentShows.observe(viewLifecycleOwner){ value ->
            binding.txtTest.text = value.data?.size.toString()
        }
    }

    override fun onResume() {
        activity?.title = viewModel.getStringDate()
        super.onResume()
    }

}