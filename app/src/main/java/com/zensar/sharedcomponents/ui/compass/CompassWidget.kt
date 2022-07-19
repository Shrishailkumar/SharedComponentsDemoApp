package com.zensar.sharedcomponents.ui.compass

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.DialogFragment
import com.android.appcomponents.util.CompassUtility
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.CompassDialogBinding
import com.zensar.sharedcomponents.databinding.CompassFragmentBinding
import kotlin.math.roundToInt

class CompassWidget : DialogFragment() {

    private var mView: View? = null
    private lateinit var dialogBinding: CompassDialogBinding
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
        // either this way we can init dialogBinding
        dialogBinding = CompassDialogBinding.inflate(inflater, container, false)
        dialogBinding.compassView.setBackgroundResource(0)
        dialogBinding.appCompatTextView.setText("")
        dialogBinding.tvAngle.setTextColor(resources.getColor(R.color.white))
        compassUtility.getCompassAngleLiveData().observe(viewLifecycleOwner) {
            dialogBinding.tvAngle.text = it.roundToInt().toString()
            getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val root: View = dialogBinding.root


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

    override fun onDestroyView() {
        super.onDestroyView()
        mView = null
    }


}