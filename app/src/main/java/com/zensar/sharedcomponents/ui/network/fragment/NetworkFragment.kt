package com.zensar.sharedcomponents.ui.network.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentNetworkBinding

class NetworkFragment : Fragment() {

    private lateinit var binding: FragmentNetworkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNetworkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btRetrofit.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_network_retrofit)
        }

        binding.btVolley.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_network_volley)
        }
    }

}