package com.zensar.sharedcomponents.ui.geofence

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.appcomponents.geofence.GeofenceBroadcastReceiverWithoutMap
import com.android.appcomponents.geofence.IGeofenceReceiver
import com.android.appcomponents.util.GeofenceUtility
import com.android.appcomponents.viewmodel.DeviceInfoViewModel
import com.android.appcomponents.viewmodel.GeofenceViewModel
import com.android.appcomponents.views.GeoFenceMapActivity
import com.zensar.sharedcomponents.databinding.FragmentGeoFenceBinding


class GeoFenceFragment : Fragment(), IGeofenceReceiver {
    var binding: FragmentGeoFenceBinding? = null
    private lateinit var geofenceViewModel: GeofenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGeoFenceBinding.inflate(
            inflater,
            container, false
        )
        val root: View = binding!!.root

        binding!!.geoFenceMapBtn.setOnClickListener {
            val intent = Intent(activity, GeoFenceMapActivity::class.java)
            intent.putExtra("latitude","")
            intent.putExtra("longitude","")
           intent.putExtra("GEOFENCE_RADIUS","")
           intent.putExtra("GEO_DURATION","")
            activity?.startActivity(intent)
        }

        geofenceViewModel = ViewModelProvider(this).get(GeofenceViewModel::class.java)
        binding!!.geoFenceCallbackBtn.setOnClickListener {
            geofenceViewModel.addGeofence(18.4516,73.9149,100f)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GeofenceBroadcastReceiverWithoutMap.registerGeofenceCallback(this)
    }

    override fun onGeofenceEnterDwell(message: String) {
        Toast.makeText(context, "Geofence callback: $message", Toast.LENGTH_LONG).show()
    }

    override fun onGeofenceExit(message: String) {
        Toast.makeText(context, "Geofence callback: $message", Toast.LENGTH_LONG).show()
    }

    override fun onGeofenceError(message: String) {
        Toast.makeText(context, "Geofence callback: $message", Toast.LENGTH_LONG).show()

        //For Removing Geofencing
        geofenceViewModel.removeGeofence()
    }
}