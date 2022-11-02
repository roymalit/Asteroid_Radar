package com.royma.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.royma.asteroidradar.R
import com.royma.asteroidradar.databinding.FragmentDetailBinding

// TODO: Setup Detail viewModel

class AsteroidDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner


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
