package com.zensar.sharedcomponents.ui.compass

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.appcomponents.util.CompassUtility
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.BasicCompassFragmentBinding
import com.zensar.sharedcomponents.databinding.CompassFragmentBinding
import kotlin.math.roundToInt

class CompassBasicView : Fragment() {
    private lateinit var binding: BasicCompassFragmentBinding
    private lateinit var compassUtility: CompassUtility

    override fun onAttach(context: Context) {
        super.onAttach(context)
        compassUtility = CompassUtility.getInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BasicCompassFragmentBinding.inflate(inflater, container, false)
        compassUtility.getCompassAngleLiveData().observe(viewLifecycleOwner) {
            binding.tvAngle.text = it.roundToInt().toString()
        }
        val root: View = binding.root


        return root
    }

    override fun onResume() {
        super.onResume()
        compassUtility.startListening()
    }

    override fun onPause() {
        super.onPause()
        compassUtility.stopListening()
    }
}