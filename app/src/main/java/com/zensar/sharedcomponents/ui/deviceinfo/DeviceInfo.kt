package com.zensar.sharedcomponents.ui.deviceinfo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.appcomponents.model.DeviceInfo
import com.android.appcomponents.util.DeviceInfoUtility
import com.android.appcomponents.util.Utility
import com.android.appcomponents.viewmodel.DeviceInfoViewModel
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentDeviceinfoBinding

class DeviceInfo : Fragment() {

    private var _binding: FragmentDeviceinfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var mTvDeviceData: TextView
    lateinit var mTvRam: TextView
    lateinit var mTvBatteryPercentage: TextView
    lateinit var mTvInstalledApps: TextView

    //the Component viewModel accessed here
    private lateinit var deviceInfoViewModel: DeviceInfoViewModel

    //the Component Class Utiltiy accessed here
    private lateinit var utilityLocOb: Utility
    override fun onAttach(context: Context) {
        super.onAttach(context)
        utilityLocOb = Utility(activity)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeviceinfoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //
        mTvDeviceData = binding.idDeviceInfo
        mTvRam = binding.tvRam
        mTvBatteryPercentage = binding.tvBattery
        mTvInstalledApps = binding.tvInstalledApps
        //utilityLocOb.isNetworkConnected()
        if (!utilityLocOb.isNetworkConnected()) {
            Toast.makeText(activity, getString(R.string.conccetivity_lost_text), Toast.LENGTH_LONG)
                .show()
        } else {
            deviceInfoViewModel = ViewModelProvider(this).get(DeviceInfoViewModel::class.java)
            activity?.let {
                deviceInfoViewModel.getDeviceData()
                    .observe(it, Observer<DeviceInfo?> {
                        updateUI(it)
                    })
            }
        }
        //
        return root
    }

    private fun updateUI(build: DeviceInfo) {
        mTvRam.text =
            (Html.fromHtml("<b>" + mTvRam.text.toString() + "</b>" + deviceInfoViewModel.getRamDetails()))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTvBatteryPercentage.text =
                (Html.fromHtml("<b>" + mTvBatteryPercentage.text.toString() + "</b>" + deviceInfoViewModel.getBatteryInfo()))
        } else {
            mTvBatteryPercentage.text = "Battery info not available for this Android OS."
        }
        mTvInstalledApps.text =
            (Html.fromHtml("<b>" + mTvInstalledApps.text.toString() + "</b>" + deviceInfoViewModel.getInstalledApps()))
        mTvDeviceData.setText(
            Html.fromHtml(
                "<b>" + "Model: " + "</b>" + build.model + "<br><br>" +
                        "<b>" + "Id: " + "</b>" + build.id + "<br><br>" +
                        "<b>" + "Manufacture: " + "</b>" + build.manufacture + "<br><br>" +
                        "<b>" + "Serial num: " + "</b>" + build.serial + "<br><br>" +
                        "<b>" + "Brand: " + "</b>" + build.brand + "<br><br>" +
                        "<b>" + "Type: " + "</b>" + build.type + "<br><br>" +
                        "<b>" + "User: " + "</b>" + build.user + "<br><br>" +
                        "<b>" + "Sdk:  " + "</b>" + build.sdk + "<br><br>" +
                        "<b>" + "Board: " + "</b>" + build.board + "<br><br>" +
                        "<b>" + "Brand name: " + "</b>" + build.brand + "<br><br>" +
                        "<b>" + "Host: " + "</b>" + build.host + "<br><br>" +
                        "<b>" + "Fingerprint: " + "</b>" + build.fingerPrint + "<br><br>" +
                        "<b>" + "Device: " + "</b>" + build.Device + "<br><br>" +
                        "<b>" + "BootLoader: " + "</b>" + build.bootLoader + "<br><br>" +
                        "<b>" + "Display: " + "</b>" + build.display + "<br><br>" +
                        "<b>" + "Hardware: " + "</b>" + build.hardware + "<br><br>" +
                        "<b>" + "product: " + "</b>" + build.product
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


