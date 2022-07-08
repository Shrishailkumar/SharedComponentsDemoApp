package com.zensar.sharedcomponents.ui.uicomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.appcomponents.customui.recyclerview.RecyclerItem
import com.android.appcomponents.customui.recyclerview.setRecyclerViewItems
import com.zensar.sharedcomponents.BR
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentRecyclerviewBinding
import com.zensar.sharedcomponents.ui.uicomponent.entiry.SampleData

class RecyclerviewFragment : Fragment() {

    private var _binding: FragmentRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private val sampleData: List<SampleData> = listOf(
        SampleData(1, "Java", 1995),
        SampleData(2, "Kotlin", 2016),
        SampleData(3, "GoLang", 2009),
        SampleData(4, "JavaScript", 1995),
        SampleData(5, "Python", 1989),
        SampleData(6, "Ruby", 1995),
        SampleData(7, "Dart", 2011),
        SampleData(8, "Swift", 2014)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndSetRecyclerView()
    }

    private fun initAndSetRecyclerView() {
        recyclerView = binding.dynamicRv
        val data:List<RecyclerItem> = loadProLanguage().map {
            it.toRecyclerItem()
        }
        setRecyclerViewItems(recyclerView, data)
    }

    private fun loadProLanguage(): List<SampleData> = sampleData

    private fun SampleData.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.sample_item_layout_hz,
        variableId = BR.sampleData
    )
}