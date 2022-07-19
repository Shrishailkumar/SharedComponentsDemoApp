package com.zensar.sharedcomponents.ui.compass

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.appcomponents.util.CompassUtility
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.CompassFragmentBinding
import kotlin.math.roundToInt

class CompassDemoFragment : Fragment() {
    private lateinit var binding: CompassFragmentBinding
    private lateinit var compassUtility: CompassUtility
    private lateinit var mContext : Context;

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        compassUtility = CompassUtility.getInstance(mContext)
        binding = CompassFragmentBinding.inflate(inflater, container, false)
        binding.compassView.setBackgroundResource(0)
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