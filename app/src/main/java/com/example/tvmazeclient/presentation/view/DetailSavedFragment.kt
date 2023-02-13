package com.example.tvmazeclient.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tvmazeclient.presentation.viewmodel.DetailSavedViewModel
import com.example.tvmazeclient.R
import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.databinding.FragmentDetailBinding
import com.example.tvmazeclient.databinding.FragmentSavedBinding
import com.example.tvmazeclient.presentation.MainActivity
import com.example.tvmazeclient.presentation.adapter.LocalAdapter
import com.example.tvmazeclient.presentation.adapter.PersonAdapter
import com.example.tvmazeclient.presentation.viewmodel.DetailSavedViewModelFactory
import com.example.tvmazeclient.presentation.viewmodel.SavedViewModel
import com.example.tvmazeclient.presentation.viewmodel.SavedViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailSavedFragment : Fragment() {

    @Inject
    lateinit var factory: DetailSavedViewModelFactory
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailSavedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@DetailSavedFragment
        }
        viewModel = ViewModelProvider(this, factory)[DetailSavedViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()
        initRecyclerView()
    }

    private fun attachObservers() {
        val args = DetailSavedFragmentArgs.fromBundle(requireArguments())

        binding.btnSave.isGone = true
/*        binding.btnSave?.setOnClickListener {
            viewModel.deleteShow(args.showId)
        }

        viewModel.deleted.observe(viewLifecycleOwner){ saved ->
            if (saved){
                findNavController().navigateUp()
                Snackbar
                    .make(
                        binding.root,
                        "Show deleted",
                        Snackbar.LENGTH_LONG)
                    .show()
            }
        }*/
        viewModel.getShowById(args.showId).observe(viewLifecycleOwner){ show ->

            binding.ivPortrait.let {
                Glide.with(it.context)
                    .load(show.image)
                    .into(it)
            }
            binding.tvName.text = show.name
            binding.tvNetworkName.text = show.network
            binding.tvRating.text = "rating: ${show.rating}"
            if (show.resume!=null){
                binding.tvResume.text = Html.fromHtml(show.resume,
                    Html.FROM_HTML_MODE_COMPACT)
            }

            binding.tvGenders.text = show.genres

            binding.tvSchedule.text = Html.fromHtml(
                "<p><b>${getString(R.string.detail_schedule)}</b>${show.time} | ${show.days}</p>",
                Html.FROM_HTML_MODE_COMPACT)

            binding.btnGoSite.setOnClickListener {
                val url = show.site
                if (url?.isNotEmpty() == true){
                    val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browse)
                }else{
                    Snackbar
                        .make(
                            binding.root,
                            "No data",
                            Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        }
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
        (activity as MainActivity?)?.binding?.bnvNews?.apply {
            val slideDown: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            this.isGone = true
            this.startAnimation(slideDown)
        }
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    /**
     * mostrar action bar al salir de este fragment
     */
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        (activity as MainActivity?)?.binding?.bnvNews?.isGone = false
    }
}