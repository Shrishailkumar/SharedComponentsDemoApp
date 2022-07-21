package com.zensar.sharedcomponents.ui.geofence

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.appcomponents.views.GeoFenceMapActivity
import com.zensar.sharedcomponents.databinding.FragmentGeoFenceBinding


class GeoFenceFragment : Fragment() {
    var binding: FragmentGeoFenceBinding? = null

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
        return root
    }
}