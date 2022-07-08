package com.zensar.sharedcomponents.ui.uicomponent

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.appcomponents.customui.alertdialog.*
import com.android.appcomponents.customui.progress.UIProgressDialog
import com.android.appcomponents.customui.recyclerview.setRecyclerViewItems
import com.android.appcomponents.customui.snackbar.UISnackBar
import com.android.appcomponents.customui.toast.UIToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentUiComponentBinding
import com.zensar.sharedcomponents.ui.uicomponent.entiry.SampleData

class UIComponentFragment : Fragment() {

    private var _binding: FragmentUiComponentBinding? = null
    private val binding get() = _binding!!

    lateinit var snackBar: UISnackBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUiComponentBinding.inflate(inflater, container, false)
        activity?.run {
            findViewById<FloatingActionButton>(R.id.fab).visibility = View.GONE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSnackBar()
        setClickListener()
    }

    private fun setClickListener() {
        binding.showSb.setOnClickListener {
            snackBar.show()
        }
        binding.showAd.setOnClickListener {
            showAlertDialog()
        }
        binding.showRv.setOnClickListener {
            showRecyclerView()
        }
        binding.showToast.setOnClickListener {
            showToast()
        }
        binding.showProgressDialog.setOnClickListener {
            showProgressDialog()
        }
    }

    private fun showRecyclerView() {
        findNavController().navigate(R.id.action_nav_fragment_component_ui_to_recyclerviewFragment)
    }

    private fun initSnackBar() {
        snackBar = UISnackBar(context as Activity).apply {
            backgroundColorRes(R.color.purple_500)
            cornerRadius(7F)
            padding(10)
            textTypeface(Typeface.DEFAULT_BOLD)
            duration(Snackbar.LENGTH_SHORT)
            textColorRes(R.color.white)
            message("Test UISnackBar")
        }
    }

    private fun showAlertDialog() {
        UIAlertDialog.build(context as Activity)
            .title("Alert Dialog Title")
            .body("Alert Dialog Body")
            .position(UIAlertDialog.POSITIONS.CENTER)
            /*.onNegative("Cancel") {
                Log.d("TAG", "Negative Clicked ")
            }*/
            .icon(R.drawable.custom_icon_tick)
            .onPositive("OK"){
                Log.d("TAG", "Positive Clicked")
            }

        /*UIAlertDialog.build(context as Activity)
            .title(
                "Alert Dialog Title",
                titleColor = ContextCompat.getColor(context as Activity, android.R.color.white)
            )
            .body(
                "Alert Dialog Body",
                color = ContextCompat.getColor(context as Activity, android.R.color.white)
            )
            .background(R.drawable.custom_rounded_green)
            .onPositive(
                "Ok",
                buttonBackgroundColor = R.drawable.custom_rounded_white,
                textColor = ContextCompat.getColor(context as Activity, android.R.color.black)
            ) {
                Log.d("TAG", "positive ")
            }
            .onNegative(
                "Cancel",
                buttonBackgroundColor = R.drawable.custom_rounded_white,
                textColor = ContextCompat.getColor(context as Activity, android.R.color.black)
            ) {
                Log.d("TAG", "negative ")
            }*/
    }

    private fun showProgressDialog() {
        UIProgressDialog(context as Activity).apply {
            isCancellable = true
            progressBarColor = R.color.purple_500
            progressMessageText = "Loading.."
            canShowMessage = true
            tcProgressMessage = R.color.white
            tsProgressMessage = 20F
            showProgressBar()
        }
    }

    private fun showToast() {
        /*UIToast.showToast(context as Activity, "Toast",
            Toast.LENGTH_LONG).show()*/
        UIToast.showToast(
            context as Activity, "Test Toast",
            Toast.LENGTH_LONG, R.drawable.custom_icon_tick, R.color.white, R.color.black
        ).show()

        /*UIToast.showToastPosition(context as Activity, "Center Toast",
            Toast.LENGTH_LONG, Gravity.CENTER).show()*/

       /* UIToast.showToast(
            context as Activity, "Test Toast",
            Toast.LENGTH_LONG, R.drawable.custom_icon_tick, R.color.white, R.color.teal_700,
            40f
        ).show()*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UIProgressDialog(context as Activity).hideProgressBar()
        _binding = null
    }
}