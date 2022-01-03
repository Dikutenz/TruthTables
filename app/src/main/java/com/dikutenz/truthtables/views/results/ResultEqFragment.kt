package com.dikutenz.truthtables.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.entities.TwoBooleanFunction
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.adapters.TableAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class ResultEqFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var s1RecyclerView: RecyclerView
    private lateinit var s2RecyclerView: RecyclerView
    private lateinit var resTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_eq, container, false)
        initUI(view)
        loadData()
        return view
    }

    private fun loadData() {
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) { booleanFunction ->
            if (booleanFunction.isNotEmpty() && mainViewModel.enterFinished) {
                mainViewModel.secondBooleanFunction.observe(viewLifecycleOwner) { secondBooleanFunction ->
                    if (secondBooleanFunction.isNotEmpty() && mainViewModel.enterFinished) {
                        val twoBF = TwoBooleanFunction(booleanFunction, secondBooleanFunction)
                        val firstValues = twoBF.firstTruthTable()
                        s1RecyclerView.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        s1RecyclerView.adapter = activity?.let { TableAdapter(firstValues, it) }

                        val secondValues = twoBF.secondTruthTable()
                        s2RecyclerView.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        s2RecyclerView.adapter = activity?.let { TableAdapter(secondValues, it) }

                        var eq = true
                        for (i in 0 until firstValues[0].values.size)
                            if (firstValues[firstValues.size - 1].values[i] != secondValues[0].values[i])
                                eq = false
                        resTextView.text =
                            if (eq) "Функции эквивалентны" else "Функции не эквивалентны"
                    }
                }
            }
        }
    }

    private fun initUI(view: View) {
        s1RecyclerView = view.findViewById(R.id.s1_recycler_view)
        s2RecyclerView = view.findViewById(R.id.s2_recycler_view)
        resTextView = view.findViewById(R.id.res_text_view)
    }

}
