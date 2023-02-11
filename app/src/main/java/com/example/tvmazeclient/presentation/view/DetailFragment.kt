package com.example.tvmazeclient.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tvmazeclient.R
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.databinding.FragmentDetailBinding
import com.example.tvmazeclient.presentation.adapter.PersonAdapter
import com.example.tvmazeclient.presentation.viewmodel.DetailViewModel
import com.example.tvmazeclient.presentation.viewmodel.DetailViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment() {
    @Inject
    lateinit var factory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@DetailFragment
        }
        initialSetup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()
        initRecyclerView()
    }

    private fun initialSetup() {
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getShowById(args.showId.toString())
        viewModel.getCastById(args.showId.toString())
    }

    private fun attachObservers() {
        /**
         * observador que se detona al finalizar el consumo del
         * servicio web busqueda de show por id, valida la respuesta
         * y maneja la forma en que se mostrara el resultado
         */
        viewModel.infoShow.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { show ->
                        binding.ivPortrait.let {
                            Glide.with(it.context)
                                .load(show.image?.medium)
                                .into(it)
                        }
                        binding.tvName.text = show.name
                        binding.tvNetworkName.text = show.network?.name
                        binding.tvRating.text = "rating: ${show.rating.average ?: "-"}"
                        if (show.summary!=null){
                            binding.tvResume.text = Html.fromHtml(show.summary,
                                Html.FROM_HTML_MODE_COMPACT)
                        }

                        binding.tvGenders.text = show.genres
                            .joinToString(", ")
                        val days = show.schedule.days.joinToString(", ")
                        binding.tvSchedule.text = "${show.schedule.time} | $days"

                        show.officialSite
                        show.network?.officialSite

                        /**
                         * validar que exista la información de la página a la que
                         * se va a navegar
                         */
                        binding.btnGoSite.setOnClickListener {
                            val url =
                                show.officialSite
                                    ?: (show.network?.officialSite ?: "")
                            if (url.isNotEmpty()){
                                val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(browse)
                            }else{
                                Snackbar
                                    .make(
                                        binding.root,
                                        "No data",
                                        Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                    hideProgressBar()
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
                }
            }
        }

        /**
         * observador que se detona al finalizar el consumo del
         * servicio web busqueda de show por id, valida la respuesta
         * y maneja la forma en que se mostrara el resultado
         */
        viewModel.cast.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    val adapter = PersonAdapter()
                    binding.rvCast.adapter = adapter
                    adapter.differ.submitList(response.data)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun showErrorScreen() {
        binding.statusContainer.isGone = false
        binding.ivProgressBar.setImageResource(R.drawable.ic_connection_error)
    }

    private fun showProgressBar() {
        binding.statusContainer.isGone = false
    }

    private fun hideProgressBar() {
        binding.statusContainer.isGone = true
    }

    private fun initRecyclerView() {
        binding.rvCast.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    /**
     * ocultar action bar al iniciar este fragment
     */
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    /**
     * mostrar action bar al salir de este fragment
     */
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}