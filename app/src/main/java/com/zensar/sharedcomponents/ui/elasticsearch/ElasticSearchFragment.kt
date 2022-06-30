package com.zensar.sharedcomponents.ui.elasticsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zensar.sharedcomponents.databinding.FragmentElasticsearchServicesBinding

class ElasticSearchFragment : Fragment() {

    private var _binding: FragmentElasticsearchServicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
 //add your view model here
        _binding = FragmentElasticsearchServicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}