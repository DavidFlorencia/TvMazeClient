package com.example.tvmazeclient.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmazeclient.databinding.FragmentSavedBinding
import com.example.tvmazeclient.presentation.adapter.LocalAdapter
import com.example.tvmazeclient.presentation.viewmodel.SavedViewModel
import com.example.tvmazeclient.presentation.viewmodel.SavedViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment() {
    @Inject
    lateinit var factory: SavedViewModelFactory
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: SavedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@SavedFragment
        }
        viewModel = ViewModelProvider(this, factory)[SavedViewModel::class.java]

        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSavedShows().observe(viewLifecycleOwner){ shows ->
            if (shows.isNotEmpty()){
                binding.rvSavedShows.isGone = false
                binding.tvEmpty.isGone = true

                val adapter = LocalAdapter()
                binding.rvSavedShows.adapter = adapter
                adapter.differ.submitList(shows)
                adapter.setOnItemClickListener {
                    nextScreen(it.showId)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvSavedShows.apply {
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun nextScreen(showId: String) {
        findNavController().navigate(
            SavedFragmentDirections.actionSavedFragmentToDetailSavedFragment(showId)
        )
    }
}