package com.zensar.sharedcomponents.ui.QRAndBarCodeScanner

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.appcomponents.model.DeviceInfo
import com.android.appcomponents.viewmodel.ScannerViewModel
import com.android.appcomponents.views.ScannerActivity
import com.zensar.sharedcomponents.databinding.FragmentScannerreadBinding


class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerreadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //the Component viewModel accessed here
    private lateinit var scannerViewModel: ScannerViewModel
    private lateinit var resultTV: TextView


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentScannerreadBinding.inflate(inflater, container, false)
        val root: View = binding.root
        resultTV = binding.showResult


        scannerViewModel = ViewModelProvider(this).get(ScannerViewModel::class.java)
        activity?.let {
            scannerViewModel.getDeviceData()
                .observe(it, Observer<String?> {

                })
        }

        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    data?.getStringExtra("result").also { resultTV.text = it }

                }
            }

        binding.openScanner.setOnClickListener {
            val intent = Intent(activity, ScannerActivity::class.java)
            resultLauncher.launch(intent)
        }
        //
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}


