package com.example.tvmazeclient.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmazeclient.R
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModel
import com.example.tvmazeclient.databinding.FragmentLandingBinding
import com.example.tvmazeclient.presentation.MainActivity
import com.example.tvmazeclient.presentation.adapter.QueryAdapter
import com.example.tvmazeclient.presentation.adapter.ScheduleAdapter
import com.google.android.material.snackbar.Snackbar

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
         * servicio web de programación del día, valida la respuesta
         * y maneja la forma en que se mostrara el resultado
         */
        viewModel.currentShows.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()

                    /**
                     * se filtra respuesta dado que incluía elementos repetidos
                     */
                    val filtered: List<ScheduleResponse.Show>? =
                        response.data?.distinctBy{
                            it.showInfo.id
                        }

                    val adapter = ScheduleAdapter()
                    binding.rvShows.adapter = adapter
                    adapter.differ.submitList(filtered)
                }
                is Resource.Error -> {
                    showErrorScreen()
                    response.message?.let {
                        Snackbar
                            .make(
                                binding.root,
                            "An error occurred : $it",
                                Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                    binding.ivProgressBar.isGone = false
                }
            }
        }

        /**
         * observador que se detona al finalizar el consumo del
         * servicio web de busqueda, valida la respuesta
         * y maneja la forma en que se mostrara el resultado
         */
        viewModel.queryShows.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    val adapter = QueryAdapter()
                    binding.rvShows.adapter = adapter
                    adapter.differ.submitList(response.data)
                }
                is Resource.Error -> {
                    showErrorScreen()
                    response.message?.let {
                        Snackbar
                            .make(
                                binding.root,
                                "An error occurred : $it",
                                Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                    binding.ivProgressBar.isGone = false
                }
            }
        }

        initRecyclerView()
    }

    private fun showProgressBar() {
        binding.ivProgressBar.isGone = false
        binding.rvShows.isGone = true
    }

    private fun hideProgressBar() {
        binding.ivProgressBar.isGone = true
        binding.rvShows.isGone = false
    }
    private fun showErrorScreen() {
        binding.ivProgressBar.isGone = false
        binding.ivProgressBar.setImageResource(R.drawable.ic_connection_error)
    }

    /**
     * se inicializa recycler view
     */
    private fun initRecyclerView() {
        binding.rvShows.apply {
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        activity?.title = viewModel.getStringDate()
        super.onResume()
    }

    /**
     * función utilizada para filtrar lista de objetos a tráves de
     * alguna de sus propiedades
     */
    private inline fun <T, K> Iterable<T>.distinctBy(selector: (T) -> K): List<T> {
        val set = HashSet<K>()
        val list = ArrayList<T>()
        for (e in this) {
            val key = selector(e)
            if (set.add(key))
                list.add(e)
        }
        return list
    }
}