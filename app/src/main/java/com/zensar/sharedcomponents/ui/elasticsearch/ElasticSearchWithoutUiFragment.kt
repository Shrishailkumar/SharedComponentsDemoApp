package com.zensar.sharedcomponents.ui.elasticsearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.appcomponents.network.interfaces.APIListener
import com.android.appcomponents.network.interfaces.NetworkAPI
import com.android.appcomponents.util.Utility
import com.android.appcomponents.viewmodel.*
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentElasticsearchWithoutUiBinding
import okhttp3.ResponseBody

class ElasticSearchWithoutUiFragment : Fragment(), APIListener {

    private var _binding: FragmentElasticsearchWithoutUiBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    var elasticSearchViewModel: ElasticSearchViewModel? = null
    private lateinit var utilityLocOb: Utility
    var queryHashMap: HashMap<String, String>? = HashMap<String, String>()
    var endPoint: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        utilityLocOb = Utility(activity)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentElasticsearchWithoutUiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        configureViewModel()
        callElasticSearch()

        return root
    }

    private fun callElasticSearch() {
        if (!utilityLocOb.isNetworkConnected()) {
            Toast.makeText(activity, getString(R.string.conccetivity_lost_text), Toast.LENGTH_LONG).show()
        } else {
            elasticSearchViewModel?.getDataFromServer(endPoint, queryHashMap)
        }
    }

    private fun configureViewModel() {
        var baseURL: String? = "https://api.github.com/search/"
        endPoint = "repositories"
        queryHashMap?.put("q","searchTerm")

        val networkAPIViewModel: NetworkAPIViewModel = ViewModelProvider(
            this, NetworkAPIViewModelFactory(baseURL!!, activity?.baseContext!!)
        ).get(NetworkAPIViewModel::class.java)

        val retrofitInstance = networkAPIViewModel.getNetworkClient().create(NetworkAPI::class.java)

        elasticSearchViewModel =
            ViewModelProvider(this, ElasticSearchViewModelFactory(retrofitInstance)).get(
                ElasticSearchViewModel::class.java
            )

        elasticSearchViewModel?.apiListener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onErrorResponse(errorMessage: String) {
        binding.tvResult.text = errorMessage
    }

    override fun onStarted() {
        binding.tvResult.text = "Service started"
    }

    override fun onSuccessResponse(responseBody: ResponseBody) {
        binding.tvResult.text = responseBody.string()
    }
}