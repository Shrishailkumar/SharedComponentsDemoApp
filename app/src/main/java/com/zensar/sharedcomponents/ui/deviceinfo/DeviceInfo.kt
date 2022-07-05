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
        mTvRam.text = (mTvRam.text.toString() + deviceInfoViewModel.getRamDetails())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTvBatteryPercentage.text = (mTvBatteryPercentage.text.toString()+deviceInfoViewModel.getBatteryInfo())
        }else{
            mTvBatteryPercentage.text = "Battery info not available for this Android OS."
        }
        mTvInstalledApps.text = (mTvInstalledApps.text.toString()+deviceInfoViewModel.getInstalledApps())
        mTvDeviceData.setText( "Serial num: " + build.serial + "\n\n" +
                "Model: " + build.model + "\n\n" +
                "Id: " + build.id + "\n\n" +
                "Manufacture: " + build.manufacture + "\n\n" +
                "Brand: " + build.brand + "\n\n" +
                "Type: " + build.type + "\n\n" +
                "User: " + build.user + "\n\n" +
                "Sdk:  " + build.sdk + "\n\n" +
                "Board: " + build.board + "\n\n" +
                "Brand name: " + build.brand + "\n\n" +
                "Host: " + build.host + "\n\n" +
                "Fingerprint: "+build.fingerPrint + "\n\n" +
                "Device: " + build.Device +"\n\n" +
                "BootLoader: "+build.bootLoader + "\n\n" +
                "Display: "+build.display + "\n\n" +
                "Hardware: "+build.hardware + "\n\n" +
                "product: "+build.product  )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


