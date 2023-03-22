package com.royma.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.royma.asteroidradar.R
import com.royma.asteroidradar.databinding.FragmentMainBinding
import com.royma.asteroidradar.repository.AsteroidDatabase

class AsteroidRadarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val viewModelFactory = AsteroidRadarViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val asteroidRadarViewModel =
            ViewModelProvider(this, viewModelFactory)[AsteroidRadarViewModel::class.java]

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
        asteroidRadarViewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
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

        // TODO: Save asteroids in offline repository - only from today's date onwards

        /*
            TODO: Use a Worker to download and save/cache today's asteroid data in background
             once a day when the device is charging and wifi is enabled
         */

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
                return true
            }
        }, viewLifecycleOwner)
    }

}
