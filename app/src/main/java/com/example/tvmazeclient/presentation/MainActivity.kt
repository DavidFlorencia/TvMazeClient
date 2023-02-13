package com.example.tvmazeclient.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tvmazeclient.R
import com.example.tvmazeclient.databinding.ActivityMainBinding
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModel
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: LandingViewModelFactory
    lateinit var viewModel: LandingViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this,factory)[LandingViewModel::class.java]

        if(resources.getBoolean(R.bool.portrait_only)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        setUpBottomMenu()

        setContentView(binding.root)
    }

    private fun setUpBottomMenu() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvNews.setupWithNavController(
            navController
        )
    }

    /**
     * se inicializa y configura searchview en action bar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView
                as? SearchView

        searchView?.queryHint = "Buscar programa"

        /**
         * se implementa listener que se detona al presionar en buscar
         */
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.bnvNews.selectedItemId = R.id.landingFragment
                viewModel.getShowsByQuery(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        /**
         * se implementa listener que se detona al cerrar caja de busqueda
         */
        menu.findItem(R.id.action_search).setOnActionExpandListener(
            object: MenuItem.OnActionExpandListener{
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                    binding.bnvNews.selectedItemId = R.id.landingFragment
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    viewModel.apply {
                        getShowsSchedule(dateIso8601.value.toString())
                    }
                    return true
                }

            }
        )

        return super.onCreateOptionsMenu(menu)
    }
}