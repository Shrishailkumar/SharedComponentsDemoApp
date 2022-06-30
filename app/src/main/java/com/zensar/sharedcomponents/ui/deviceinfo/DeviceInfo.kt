package com.zensar.sharedcomponents.ui.deviceinfo

import android.content.Context
import android.os.Build
import android.os.Bundle
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
    lateinit var mTvDeviceData : TextView
    lateinit var mTvRam : TextView
    lateinit var mTvBatteryPercentage: TextView
    lateinit var mTvInstalledApps: TextView
    //the Component viewModel accessed here
    private lateinit var deviceInfoViewModel: DeviceInfoViewModel
    //the Component Class Utiltiy accessed here
   private lateinit var utilityLocOb:Utility
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
            Toast.makeText(activity, getString(R.string.conccetivity_lost_text), Toast.LENGTH_LONG).show()
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
        //deviceInfoViewModel.
        /*mTvRam.setTextColor(mTvRam.text+deviceInfoViewModel.())
        mTvBatteryPercentage.setTextColor(mTvBatteryPercentage.text+deviceInfoViewModel.getBateryPercentage())
        mTvInstalledApps.setTextColor(mTvInstalledApps.text+deviceInfoViewModel.installedApps())*/
        mTvDeviceData.setText( "SERIAL: " + build.serial + "\n" +
                "MODEL: " + build.model + "\n" +
                "ID: " + build.id + "\n" +
                "Manufacture: " + build.manufacture + "\n" +
                "Brand: " + build.brand + "\n" +
                "Type: " + build.type + "\n" +
                "User: " + build.user + "\n" +
                "SDK:  " + build.sdk + "\n" +
                "BOARD: " + build.board + "\n" +
                "BRAND: " + build.brand + "\n" +
                "HOST: " + build.host + "\n" +
                "FINGERPRINT: "+build.fingerPrint + "\n" +
                "Device: " + build.Device +"\n" +
                "BootLoader: "+build.bootLoader + "\n" +
                "Display: "+build.display + "\n" +
                "Hardware: "+build.hardware + "\n" +
                "product: "+build.product  )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


