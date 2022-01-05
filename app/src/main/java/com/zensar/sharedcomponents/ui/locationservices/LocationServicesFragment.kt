package com.zensar.sharedcomponents.ui.locationservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zensar.sharedcomponents.databinding.FragmentLocationservicesBinding

class LocationServicesFragment : Fragment() {

    private var _binding: FragmentLocationservicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
 //add your view model here
        _binding = FragmentLocationservicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}