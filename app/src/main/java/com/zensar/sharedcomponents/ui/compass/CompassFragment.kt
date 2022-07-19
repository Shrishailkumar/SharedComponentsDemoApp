package com.zensar.sharedcomponents.ui.compass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.CompassViewTypeBinding

class CompassFragment: Fragment() {

    private var _binding: CompassViewTypeBinding? = null
    private val binding get() = _binding!!
    val widgetCompass = CompassWidget()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CompassViewTypeBinding.inflate(inflater, container, false)
        activity?.run {
            findViewById<FloatingActionButton>(R.id.fab).visibility = View.GONE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.compassView1.setOnClickListener {
            showCompassBasicView()
        }
        binding.compassView2.setOnClickListener {
            showCompassView()
        }
        binding.compassView3.setOnClickListener {
            showCompassWidget()
        }
    }

    private fun showCompassView() {
        findNavController().navigate(R.id.action_nav_fragment_component_ui_to_compassview1)
    }

    private fun showCompassBasicView() {
        findNavController().navigate(R.id.action_nav_fragment_component_ui_to_compassview2)
    }

    private fun showCompassWidget()
    {
        widgetCompass.show(parentFragmentManager, "Compass Widget")
    }
}