package com.dikutenz.truthtables.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.adapters.TableAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class ResultTruthTableFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var resultLayout: LinearLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_truth_table, container, false)
        initUI(view)
        loadData()
        return view
    }

    private fun loadData() {
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && mainViewModel.enterFinished) {
                resultLayout.visibility = View.VISIBLE
                createTable()
            } else {
                resultLayout.visibility = View.GONE
            }

        }
    }

    private fun createTable() {
        val booleanFunction = mainViewModel.booleanFunction.value!!
        val bf = BooleanFunction(value = booleanFunction, inputType = mainViewModel.inputType.toString())
        val values = bf.getTruthTable(true)
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = activity?.let { TableAdapter(values, it) }
    }

    private fun initUI(view: View) {
        resultLayout = view.findViewById(R.id.result_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
    }
}
