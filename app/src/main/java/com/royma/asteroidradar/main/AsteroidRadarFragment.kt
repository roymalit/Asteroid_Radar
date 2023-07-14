package com.royma.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.royma.asteroidradar.R
import com.royma.asteroidradar.databinding.FragmentMainBinding
import com.royma.asteroidradar.repository.AsteroidDatabase

class AsteroidRadarFragment : Fragment() {

    // Get a reference to the ViewModel associated with this fragment.
    private val asteroidRadarViewModel by lazy {
        val application = requireNotNull(this.activity).application
        // Create an instance of the ViewModel Factory.
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val viewModelFactory = AsteroidRadarViewModelFactory(dataSource, application)
        ViewModelProvider(this, viewModelFactory)[AsteroidRadarViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner


        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.asteroidRadarViewModel = asteroidRadarViewModel

       // Inform RecyclerView about adapter
        val adapter = AsteroidAdapter(AsteroidListener {
            asteroid -> asteroidRadarViewModel.onAsteroidClicked(asteroid)
        })

        // Attaches the adapter to the Recycler
        binding.asteroidRecycler.adapter = adapter

        // Listens for changes in the database of Asteroids
//        asteroidRadarViewModel.asteroids.observe(viewLifecycleOwner) {
//            it?.let {
//                adapter.submitList(it)
//            }
//        }
        asteroidRadarViewModel.asteroidCollection.observe(viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        // Navigates to the Asteroid Detail fragment
        asteroidRadarViewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner){ asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    AsteroidRadarFragmentDirections.actionShowDetail(asteroid))
                asteroidRadarViewModel.onAsteroidDetailNavigated()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Correct way to implement menus as onCreateOptionsMenu is deprecated
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner (automatically
        // handles provider removal) and an optional Lifecycle.State to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                asteroidRadarViewModel.updateFilter(
                    when (menuItem.itemId){
                        R.id.view_today_menu -> AsteroidFilter.TODAY
                        else -> AsteroidFilter.WEEK
                    }
                )
                return true
            }
        }, viewLifecycleOwner)
    }

}
