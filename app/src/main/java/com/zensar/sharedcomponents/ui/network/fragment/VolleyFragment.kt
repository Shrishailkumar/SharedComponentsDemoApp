package com.zensar.sharedcomponents.ui.network.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.appcomponents.network.interfaces.VolleyAPIListener
import com.android.appcomponents.util.Utility
import com.zensar.sharedcomponents.ui.network.utils.showSnackBar
import com.zensar.sharedcomponents.ui.network.viewmodel.VolleyViewModel
import com.google.gson.GsonBuilder
import com.zensar.sharedcomponents.databinding.FragmentVolleyBinding
import com.zensar.sharedcomponents.ui.network.adapter.ToDoAdapter
import com.zensar.sharedcomponents.ui.network.model.Todos

class VolleyFragment : Fragment(), VolleyAPIListener {


    private lateinit var viewModel: VolleyViewModel
    private lateinit var volleyFragmentBinding: FragmentVolleyBinding
    private val _binding get() = volleyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        volleyFragmentBinding = FragmentVolleyBinding.inflate(inflater, container, false)

        viewModel =
            ViewModelProvider(
                this,
                VolleyViewModel.VolleyViewModelFactory(this)
            )[VolleyViewModel::class.java]
        viewModel.apiListener = this


        return _binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun callVolleyAPI(viewModel: VolleyViewModel) {
        //Initialize Utility Class from AppComponent Library
        val utility = Utility(this.requireContext())
        //Check Internet Connectivity
        if (utility.isNetworkConnected()) {
            //Get data from server
            viewModel.getDataFromServer()
        } else {
            //Show message when internet is not available
            showSnackBar(
                this.requireActivity(),
                "Internet is not Available",
                "Enable",
                {
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        // Call network API
        callVolleyAPI(viewModel)
    }

    override fun onStarted() {
        _binding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccessResponse(response: String) {
        _binding.progressBar.visibility = View.GONE
        val todos = GsonBuilder().create().fromJson(response, Todos::class.java)

        _binding.rvTodo.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ToDoAdapter(todos)
        }
    }

    override fun onErrorResponse(errorMessage: String) {
        _binding.progressBar.visibility = View.GONE
        makeText(context, errorMessage, LENGTH_SHORT).show()
        Log.e("TAG", "onErrorResponse: $errorMessage")
    }
}