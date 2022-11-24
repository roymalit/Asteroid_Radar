package com.royma.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.royma.asteroidradar.R
import com.royma.asteroidradar.databinding.FragmentDetailBinding
import com.royma.asteroidradar.repository.AsteroidDatabase

// TODO: Setup Detail viewModel

class AsteroidDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val asteroid = AsteroidDetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid
        val viewModelFactory = AsteroidDetailViewModelFactory(asteroid, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val asteroidDetailViewModel =
            ViewModelProvider(this, viewModelFactory)[AsteroidDetailViewModel::class.java]

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.detailViewModel = asteroidDetailViewModel

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        // TODO: Display previously selected asteroid data correctly

        // TODO: Display correct 'hazardous' image

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
