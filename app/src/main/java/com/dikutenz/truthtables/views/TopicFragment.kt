package com.dikutenz.truthtables.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.enums.Topic
import com.dikutenz.truthtables.model.enums.Topic.*
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.adapters.TopicAdapter
import com.dikutenz.truthtables.views.calculators.CalculatorEqTwoFunctionsFragment
import org.koin.android.viewmodel.ext.android.viewModel

class TopicFragment : Fragment(), TopicAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topic, container, false)
        initUI(view)
        return view
    }

    override fun onItemClicked(topic: Topic) {
        mainViewModel.topic = topic
        val fragment = if (topic == EQUIVALENCE_FUNCTION) {
            CalculatorEqTwoFunctionsFragment()
        } else {
            mainViewModel.isSDNF = (topic == FIND_SDNF)
            mainViewModel.isSKNF = (topic == FIND_SKNF)
            SettingCreateTruthTableFragment()
        }
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    private fun initUI(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TopicAdapter(this, Topic.values().toList())
    }
}
