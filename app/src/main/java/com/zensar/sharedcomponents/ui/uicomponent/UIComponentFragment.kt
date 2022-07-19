package com.zensar.sharedcomponents.ui.uicomponent

import android.app.Activity
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.appcomponents.customui.alertdialog.*
import com.android.appcomponents.customui.datepicker.UICalendarListener
import com.android.appcomponents.customui.datepicker.UICalendarView
import com.android.appcomponents.customui.progress.UIProgressBar
import com.android.appcomponents.customui.snackbar.UISnackBar
import com.android.appcomponents.customui.timepicker.UITimePicker
import com.android.appcomponents.customui.toast.UIToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentUiComponentBinding
import java.text.SimpleDateFormat
import java.util.*

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
        binding.showCalendar.setOnClickListener {
            showCalendar()
        }
        binding.showTime.setOnClickListener {
            showTime()
        }
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

    private fun showProgressDialog() {
        UIProgressBar.showProgressBar(
            context as Activity, "Loading...",
            ContextCompat.getColor(context as Activity, R.color.purple_200)
        )
    }

    private fun showAlertDialog() {
        /*UIAlertDialog.build(context as Activity)
            .title("Alert Dialog Title")
            .body("Alert Dialog Body")
            .position(UIAlertDialog.POSITIONS.CENTER)
            .onNegative("Cancel") {
                Log.d("TAG", "Negative Clicked ")
            }
            .icon(R.drawable.custom_icon_tick)
            .onPositive("OK"){
                Log.d("TAG", "Positive Clicked")
            }*/
        UIAlertDialog.build(context as Activity)
            .title("Alert Dialog Title")
            .body("Alert Dialog Body")
            .position(UIAlertDialog.POSITIONS.CENTER)
            /*.onNegative("Cancel") {
                Log.d("TAG", "Negative Clicked ")
            }*/
            .icon(R.drawable.custom_icon_tick)
            .onPositive("OK") {
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

    private fun showRecyclerView() {
        findNavController().navigate(R.id.action_nav_fragment_component_ui_to_recyclerviewFragment)
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

    private fun showCalendar() {
        val dialog = Dialog(activity as Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.ui_calendar)

        val currentCalendar = Calendar.getInstance(Locale.getDefault())
        val calendarView = dialog.findViewById<UICalendarView>(R.id.calendarView)

        calendarView.apply {
            //Show monday as first date of week
            initDayOfWeek = Calendar.MONDAY

            //Show/hide overflow days of a month
            isOverflowDateVisible = false

            calendarBackgroundColor =
                (ContextCompat.getColor(activity as Activity, R.color.white))

            calendarHeaderBackgroundColor =
                (ContextCompat.getColor(activity as Activity, android.R.color.holo_purple))
            calendarTitleTextColor = (ContextCompat.getColor(activity as Activity, R.color.black))
            weekLayoutBackgroundColor =
                (ContextCompat.getColor(activity as Activity, R.color.white))
            dayOfWeekTextColor = (ContextCompat.getColor(activity as Activity, R.color.black))
            dayOfMonthTextColor = (ContextCompat.getColor(activity as Activity, R.color.black))
            disabledDayBackgroundColor =
                (ContextCompat.getColor(activity as Activity, R.color.disabled_grey_light))
            disabledDayTextColor =
                (ContextCompat.getColor(activity as Activity, R.color.day_disabled_text_color))
            selectedDayBackground =
                (ContextCompat.getColor(activity as Activity, android.R.color.holo_green_dark))
            selectedDayTextColor = (ContextCompat.getColor(activity as Activity, R.color.white))
            currentDayOfMonth =
                (ContextCompat.getColor(activity as Activity, R.color.sky_blue))

            //call refreshCalendar to update calendar the view
            refreshCalendar(currentCalendar)
        }.setUICalendarListener(object : UICalendarListener {

            override fun onDateSelected(date: Date) {
                val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                UIToast.showToast(activity as Activity, df.format(date), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onLongClick(date: Date) {
                val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                UIToast.showToast(activity as Activity, df.format(date), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onMonthChanged(date: Date) {
                val df = SimpleDateFormat("MM-yyyy", Locale.ENGLISH)
                UIToast.showToast(activity as Activity, df.format(date), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        dialog.show()
    }

    private fun showTime() {
        val materialTimePicker = UITimePicker().materialTimeBuilder(
            R.style.Theme_DemoAppsForSharedComponent_TimePicker
        ).build()

        materialTimePicker.show(parentFragmentManager, "UIComponentFragment")
        materialTimePicker.addOnPositiveButtonClickListener {
            val pickedHour: Int = materialTimePicker.hour
            val pickedMinute: Int = materialTimePicker.minute

            UIToast.showToast(
                context as Activity, "Selected $pickedHour:$pickedMinute",
                Toast.LENGTH_LONG, R.drawable.custom_icon_tick, R.color.sky_blue, R.color.white
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UIProgressBar.hideProgressBar()
        _binding = null
    }
}