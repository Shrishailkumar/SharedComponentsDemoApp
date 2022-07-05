package com.zensar.sharedcomponents.ui.elasticsearch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.appcomponents.views.ElasticSearchActivity
import com.zensar.sharedcomponents.databinding.FragmentElasticsearchServicesBinding

class ElasticSearchFragment : Fragment() {

    private var _binding: FragmentElasticsearchServicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentElasticsearchServicesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnPerformElasticSearch?.setOnClickListener {
            callElasticSearch()
        }

        return root
    }

    private fun callElasticSearch() {
        var queryHashMap: HashMap<String, String>? = HashMap<String, String>()
        val intent = Intent(activity, ElasticSearchActivity::class.java)
        intent.putExtra("baseUrl","https://api.github.com/search/")
        intent.putExtra("endPoint","repositories")
        queryHashMap?.put("q","searchTerm")
        intent.putExtra("map",queryHashMap)
        activity?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}